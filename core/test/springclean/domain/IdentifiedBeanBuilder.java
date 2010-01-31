package springclean.domain;

import static com.google.common.collect.Lists.newArrayList;
import org.daisychain.source.AClass;
import org.daisychain.source.Method;
import springclean.core.domain.ConstructorArg;
import springclean.core.domain.IdentifiedBean;
import springclean.core.domain.Property;
import springclean.core.domain.SpringId;
import static springclean.core.domain.SpringId.springId;
import springclean.core.exception.Defect;
import springclean.core.generate.ConstructionStrategy;

import java.util.List;

public class IdentifiedBeanBuilder extends BeanBuilder {
    private SpringId springId = springId("springId");

    private IdentifiedBeanBuilder() {
    }

    public static IdentifiedBeanBuilder anIdentifiedBean() {
        return new IdentifiedBeanBuilder();
    }

    public IdentifiedBeanBuilder withIdentity(SpringId springId) {
        this.springId = springId;
        return this;
    }

    public IdentifiedBean build() {
        return new IdentifiedBean() {

            public AClass clazz() {
                return aClass;
            }

            public List<Property> setterDependencies() {
                return newArrayList(properties.keySet());
            }

            public boolean isAbstract() {
                return false;
            }

            public List<ConstructorArg> constructorArgs() {
                return constructorArgs;
            }

            public boolean hasInitMethod() {
                return initMethod != null;
            }

            public boolean hasDestroyMethod() {
                return destroyMethod != null;
            }

            public Method initMethod() {
                if (!hasInitMethod()) throw new Defect();
                return initMethod;
            }

            public Method destroyMethod() {
                if (!hasDestroyMethod()) throw new Defect();
                return destroyMethod;
            }

            private boolean hasFactoryMethod() {
                return factoryMethod != null;
            }

            public Method factoryMethod() {
                if (!hasFactoryMethod()) throw new Defect("No factory method defined");
                return factoryMethod;
            }

            public Method constructor() {
                return constructor;
            }

            public Method setter(Property property) {
                if (!properties.containsKey(property))
                    throw new Defect("Not sure which setter metbod to use for " + property.name());

                return properties.get(property);
            }

            public ConstructionStrategy asConstructionStrategy(AClass aClass) {
                return constructionStrategy;
            }

            public SpringId id() {
                return springId;
            }
        };
    }
}