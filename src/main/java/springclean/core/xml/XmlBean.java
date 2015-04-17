package springclean.core.xml;

import nu.xom.Element;
import org.daisychain.source.*;
import org.objenesis.ObjenesisStd;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import springclean.core.domain.ApplicationContext;
import springclean.core.domain.Bean;
import springclean.core.domain.ConstructorArg;
import springclean.core.domain.Property;
import springclean.core.exception.Defect;
import springclean.core.generate.*;
import springclean.core.util.SimpleFunctor;

import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.daisychain.source.ExistingClass.existingClass;
import static springclean.core.domain.SpringId.springId;
import static springclean.core.xml.XomUtils.loop;

public class XmlBean extends AbstractElementWrapper implements Bean {
    private final ApplicationContext applicationContext;

    public XmlBean(Element beanNode, ApplicationContext applicationContext) {
        super(beanNode, applicationContext);
        this.applicationContext = applicationContext;
    }

    protected XmlIdentifiedBean parent() {
        if (!hasParent()) throw new Defect("No parent for " + this);
        return (XmlIdentifiedBean) applicationContext.findBean(springId(attributeValue("parent")));
    }

    protected boolean hasAttribute(String name) {
        boolean localResult = super.hasAttribute(name);
        return localResult || (hasParent() && parent().hasAttribute(name));
    }

    protected String attributeValue(String name) {
        if (super.hasAttribute(name)) return super.attributeValue(name);
        return parent().attributeValue(name);
    }

    private boolean hasParent() {
        return super.hasAttribute("parent");
    }

    public AClass<ExistingMethod> declaredBeanClass() {
        return existingClass(declaredJavaClass());
    }

    public AClass<ExistingMethod> constructedBeanClass() {
        return declaredBeanClass().implementsInterface(FactoryBean.class) ? factoryBeanClass() : declaredBeanClass();
    }

    private ExistingClass factoryBeanClass() {
        try {
            FactoryBean factoryBean = (FactoryBean) new ObjenesisStd().getInstantiatorOf(declaredJavaClass()).newInstance();
            return existingClass(factoryBean.getObjectType());
        } catch (Exception e) {
            return existingClass(declaredJavaClass());
        }
    }

    public boolean hasInitMethod() {
        return hasAttribute("init-method") || declaredBeanClass().implementsInterface(InitializingBean.class);
    }

    public boolean hasDestroyMethod() {
        boolean localResult = hasAttribute("destroy-method") || declaredBeanClass().implementsInterface(DisposableBean.class);
        return localResult || (hasParent() && parent().hasDestroyMethod());
    }

    protected boolean hasFactoryMethod() {
        boolean localResult = hasAttribute("factory-method");
        return localResult || (hasParent() && parent().hasFactoryMethod());
    }

    public Method initMethod() {
        MethodFinder<ExistingMethod> methodFinder = new MethodFinder<ExistingMethod>(declaredBeanClass());
        if (hasAttribute("init-method")) return methodFinder.method(attributeValue("init-method"), 0);
        if (declaredBeanClass().implementsInterface(InitializingBean.class))
            return methodFinder.method("afterPropertiesSet", 0);
        if (hasParent()) return parent().initMethod();
        throw new Defect("No init method defined on " + this);
    }

    public Method destroyMethod() {
        if (!hasDestroyMethod()) throw new Defect("No destroy method on " + this);
        String methodName = hasAttribute("destroy-method") ? attributeValue("destroy-method") : "destroy";
        return new MethodFinder<ExistingMethod>(declaredBeanClass()).method(methodName, 0);
    }

    public Method factoryMethod() {
        return new MethodFinder<ExistingMethod>(factoryClass()).method(attributeValue("factory-method"), constructorArgs().size());
    }


    public List<ConstructorArg> constructorArgs() {
        final List<ConstructorArg> dependencies = newArrayList();
        loop(element.query("constructor-arg"), new SimpleFunctor<Element>() {
            public void execute(Element target) {
                dependencies.add(new XmlConstructorArg(target, applicationContext));
            }
        });
        try {
            ConstructorArgs inheritedConstructorArgs = new ConstructorArgs(hasParent() ? parent().constructorArgs() : Collections.EMPTY_LIST);
            return new ConstructorArgs(dependencies).mergeIn(inheritedConstructorArgs).constructorArgs();
        } catch (ConstructorArgs.IllegalConstructorArgs illegalConstructorArgs) {
            throw new Defect("Illegal constructor arg indexing in " + this);
        }
    }


    public List<Property> setterDependencies() {
        final List<Property> dependencies = newArrayList();
        loop(element.getChildElements("property"), new SimpleFunctor<Element>() {
            public void execute(Element target) {
                dependencies.add(new XmlProperty(target, applicationContext));
            }
        });
        InjectedProperties inheritedInjectedProperties = new InjectedProperties(hasParent() ? parent().setterDependencies() : Collections.EMPTY_LIST);
        return new InjectedProperties(dependencies).mergeIn(inheritedInjectedProperties).properties();
    }

    private AClass<ExistingMethod> factoryClass() {
        if (hasAttribute("class")) return declaredBeanClass();
        if (hasFactoryBean()) return factoryBean().clazz();
        throw new XomProcessingException("Can't determine factory class for " + this);
    }

    private InlineXmlReference factoryBean() {
        return new InlineXmlReference(springId(attributeValue("factory-bean")), applicationContext);
    }


    protected Class<?> declaredJavaClass() {

        String className;
        if (hasAttribute("class")) {
            className = attributeValue("class");
        } else if (hasFactoryBean()) {
            className = factoryClass().method(attributeValue("factory-method"), constructorArgs().size()).returnType().name();
        } else {
            throw new XomProcessingException("Can't work out class for " + this);
        }

        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new XomProcessingException("can't find existing class " + className);
        }
    }

    private boolean hasFactoryBean() {
        return hasAttribute("factory-bean") || (hasParent() && parent().hasFactoryMethod());
    }

    public Constructor constructor() {
        for (Constructor constructionMethod : declaredBeanClass().constructors()) {
            if (constructionMethod.parameters().size() == constructorArgs().size()) {
                return constructionMethod;
            }
        }
        throw new Defect("Not sure which construction method to use for " + this);
    }

    public Method setter(Property property) {
        for (Method method : declaredBeanClass().methods()) {
            if (property.name().setterName().equals(method.name())) return method;
        }
        throw new Defect("Not sure which setter method to use for " + property.name());
    }

    public boolean isAbstract() {
        return attributeValueEquals("abstract", "true") || attributeValueEquals("scope", "prototype");
    }

    public ConstructionStrategy asConstructionStrategy(AClass aClass) {
        return constructionStrategy();
    }

    protected ConstructionStrategy constructionStrategy() {
        if (hasFactoryBean() && hasFactoryMethod())
            return new CustomFactoryBeanConstructionStrategy(factoryBean(), this);
        if (hasFactoryMethod()) return new StaticFactoryMethodBeanConstructionStrategy(this);
        if (declaredBeanClass().implementsInterface(FactoryBean.class))
            return new SpringFactoryBeanConstructionStrategy(constructedBeanClass(), new StandardBeanConstructionStrategy(this));
        if (declaredBeanClass().implementsInterface(InitializingBean.class)) new StandardBeanConstructionStrategy(this);
        return new StandardBeanConstructionStrategy(this);
    }

}
