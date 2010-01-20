package springclean.domain;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import org.daisychain.source.AClass;
import org.daisychain.source.Constructor;
import static org.daisychain.source.ConstructorBuilder.aConstructor;
import org.daisychain.source.ExistingClass;
import org.daisychain.source.Method;
import static org.daisychain.source.MethodBuilder.aMethod;
import springclean.exception.Defect;
import springclean.generate.ContextElement;
import static springclean.generate.ContextElementBuilder.aContextElement;

import java.util.List;
import java.util.Map;

public class BeanBuilder {

    AClass aClass = new ExistingClass(String.class);
    List<ConstructorArg> constructorArgs = newArrayList();
    Map<Property, Method> properties = newHashMap();
    Constructor constructor = aConstructor().build();
    Method initMethod;
    Method destroyMethod;
    Method factoryMethod;
    ContextElement contextElement = aContextElement().build();
    Reference factoryBean;

    BeanBuilder() {
    }

    public static BeanBuilder aBean() {
        return new BeanBuilder();
    }

    public BeanBuilder withClass(AClass aClass) {
        this.aClass = aClass;
        return this;
    }

    public BeanBuilder withFactoryMethod(Method method) {
        this.factoryMethod = method;
        return this;
    }

    public BeanBuilder withFactoryBean(Reference reference) {
        this.factoryBean = reference;
        return this;
    }

    public BeanBuilder withInitMethod(Method method) {
        initMethod = method;
        return this;
    }

    public BeanBuilder withDestroyMethod(Method method) {
        destroyMethod = method;
        return this;
    }

    public BeanBuilder withConstructorArg(ConstructorArg constructorArg) {
        this.constructorArgs.add(constructorArg);
        return this;
    }

    public BeanBuilder withProperty(Property property) {
        return withProperty(property, aMethod().withName(property.name().setterName()).build());
    }

    public BeanBuilder withProperty(Property property, Method setterMethod) {
        this.properties.put(property, setterMethod);
        return this;
    }

    public BeanBuilder withConstructor(Constructor constructor) {
        this.constructor = constructor;
        return this;
    }

    public BeanBuilder whichProducesContextElement(ContextElement contextElement) {
        this.contextElement = contextElement;
        return this;
    }

    public Bean build() {
        return new Bean() {
            public AClass clazz() {
                return aClass;
            }

            public List<Property> setterDependencies() {
                return newArrayList(properties.keySet());
            }

            public List<ConstructorArg> constructorArgs() {
                return constructorArgs;
            }

            public Method constructor() {
                return constructor;
            }

            public boolean hasInitMethod() {
                return initMethod != null;
            }

            public boolean hasDestroyMethod() {
                return destroyMethod != null;
            }

            public Method initMethod() {
                if(!hasInitMethod()) throw new Defect("No init method defined");
                return initMethod;
            }

            public Method destroyMethod() {
                if(!hasDestroyMethod()) throw new Defect("No destroy method defined");
                return destroyMethod;
            }

            private boolean hasFactoryMethod() {
                return factoryMethod != null;
            }

            public Method factoryMethod() {
                if(!hasFactoryMethod()) throw new Defect("No factory method defined");
                return factoryMethod;
            }

            public Method setter(Property property) {
                if (!properties.containsKey(property))
                    throw new Defect("Not sure which setter metbod to use for " + property.name());

                return properties.get(property);
            }

            public ContextElement asContextElement(AClass aClass) {
                return contextElement;
            }
        };
    }
}