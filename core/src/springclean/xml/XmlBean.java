package springclean.xml;

import static com.google.common.collect.Lists.newArrayList;
import nu.xom.Element;
import org.daisychain.source.*;
import org.daisychain.util.SimpleFunctor;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import springclean.domain.ApplicationContext;
import springclean.domain.Bean;
import springclean.domain.ConstructorArg;
import springclean.domain.Property;
import static springclean.domain.SpringId.springId;
import springclean.exception.Defect;
import springclean.generate.*;
import static springclean.xml.XomUtils.loop;

import java.util.List;

public class XmlBean extends AbstractElementWrapper implements Bean {
    private final ApplicationContext applicationContext;

    public XmlBean(Element beanNode, ApplicationContext applicationContext) {
        super(beanNode, applicationContext);
        this.applicationContext = applicationContext;
    }

    public AClass<ExistingMethod> clazz() {
        return new ExistingClass(beanClass());
    }

    public boolean hasInitMethod() {
        return hasAttribute("init-method");
    }

    public boolean hasDestroyMethod() {
        return hasAttribute("destroy-method") || clazz().implementsInterface(DisposableBean.class);
    }

    private boolean hasFactoryMethod() {
        return hasAttribute("factory-method");
    }

    public Method initMethod() {
        return new MethodFinder<ExistingMethod>(clazz()).method(attributeValue("init-method"), 0);
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
        return dependencies;
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
            className = element.getAttributeValue("class");
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
        return hasAttribute("factory-bean");
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

    public ContextElement asContextElement(AClass aClass) {
        return new BeanContextElement(this, constructionStrategy());
    }

    protected ConstructionStrategy constructionStrategy() {
        if (hasFactoryBean() && hasFactoryMethod())
            return new CustomFactoryBeanConstructionStrategy(factoryBean(), this);
        if (hasFactoryMethod()) return new StaticFactoryMethodBeanConstructionStrategy(this);
        if (clazz().implementsInterface(FactoryBean.class))
            return new SpringFactoryBeanConstructionStrategy(clazz(), new StandardBeanConstructionStrategy(this));
        if (clazz().implementsInterface(InitializingBean.class))
            return new CustomFactoryBeanConstructionStrategy(factoryBean(), this);
        return new StandardBeanConstructionStrategy(this);
    }

}
