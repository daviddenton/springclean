package springclean.core.xml;

import static com.google.common.collect.Lists.newArrayList;
import nu.xom.Element;
import org.daisychain.source.*;
import org.daisychain.util.SimpleFunctor;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import springclean.core.domain.*;
import static springclean.core.domain.SpringId.springId;
import springclean.core.exception.Defect;
import springclean.core.generate.*;
import static springclean.core.xml.XomUtils.loop;

import java.util.Collections;
import java.util.List;

public class XmlBean extends AbstractElementWrapper implements Bean {
    private final ApplicationContext applicationContext;

    public XmlBean(Element beanNode, ApplicationContext applicationContext) {
        super(beanNode, applicationContext);
        this.applicationContext = applicationContext;
    }

    protected XmlIdentifiedBean parent() {
        if (!hasParent()) throw new Defect("No parent for " + this);
        return (XmlIdentifiedBean) applicationContext.findBean(SpringId.springId(attributeValue("parent")));
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

    public AClass<ExistingMethod> clazz() {
        return new ExistingClass(beanClass());
    }

    public boolean hasInitMethod() {
        return hasAttribute("init-method") || clazz().implementsInterface(InitializingBean.class);
    }

    public boolean hasDestroyMethod() {
        boolean localResult = hasAttribute("destroy-method") || clazz().implementsInterface(DisposableBean.class);
        return localResult || (hasParent() && parent().hasDestroyMethod());
    }

    protected boolean hasFactoryMethod() {
        boolean localResult = hasAttribute("factory-method");
        return localResult || (hasParent() && parent().hasFactoryMethod());
    }

    public Method initMethod() {
        MethodFinder<ExistingMethod> methodFinder = new MethodFinder<ExistingMethod>(clazz());
        if (hasAttribute("init-method")) return methodFinder.method(attributeValue("init-method"), 0);
        if (clazz().implementsInterface(InitializingBean.class)) return methodFinder.method("afterPropertiesSet", 0);
        if (hasParent()) return parent().initMethod();
        throw new Defect("No init method defined on " + this);
    }

    public Method destroyMethod() {
        if (!hasDestroyMethod()) throw new Defect("No destroy method on " + this);
        String methodName = hasAttribute("destroy-method") ? attributeValue("destroy-method") : "destroy";
        return new MethodFinder<ExistingMethod>(clazz()).method(methodName, 0);
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
        ConstructorArgs inheritedConstructorArgs = new ConstructorArgs(hasParent() ? parent().constructorArgs() : Collections.EMPTY_LIST);
        return new ConstructorArgs(dependencies).mergeIn(inheritedConstructorArgs).constructorArgs();
    }


    public List<Property> setterDependencies() {
        final List<Property> dependencies = newArrayList();
        loop(element.getChildElements("property"), new SimpleFunctor<Element>() {
            public void execute(Element target) {
                dependencies.add(new XmlProperty(target, applicationContext));
            }
        });
        return dependencies;
    }

    private AClass<ExistingMethod> factoryClass() {
        if (hasAttribute("class")) return clazz();
        if (hasFactoryBean()) return factoryBean().clazz();
        throw new XomProcessingException("Can't determine factory class for " + this);
    }

    private SimpleReference factoryBean() {
        return new SimpleReference(springId(attributeValue("factory-bean")), applicationContext);
    }

    protected Class<?> beanClass() {

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
        boolean localResult = hasAttribute("factory-bean");
        return localResult || (hasParent() && parent().hasFactoryMethod());
    }

    public Constructor constructor() {
        for (Constructor constructionMethod : clazz().constructors()) {
            if (constructionMethod.parameters().size() == constructorArgs().size()) {
                return constructionMethod;
            }
        }
        throw new Defect("Not sure which construction method to use for " + this);
    }

    public Method setter(Property property) {
        for (Method method : clazz().methods()) {
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
        if (clazz().implementsInterface(FactoryBean.class))
            return new SpringFactoryBeanConstructionStrategy(clazz(), new StandardBeanConstructionStrategy(this));
        if (clazz().implementsInterface(InitializingBean.class)) new StandardBeanConstructionStrategy(this);
        return new StandardBeanConstructionStrategy(this);
    }

}
