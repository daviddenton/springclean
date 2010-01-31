package springclean.core.domain;

import com.google.common.base.Function;
import static com.google.common.collect.Lists.transform;
import springclean.core.generate.ConstructionStrategy;

import java.util.List;

public interface Property extends InjectedDependency {
    PropertyName name();

    public static class Util {
        public static List<ConstructionStrategy> propertyInjectedDependencies(final Bean bean) {
            return transform(bean.setterDependencies(), new Function<Property, ConstructionStrategy>() {
                public ConstructionStrategy apply(Property property) {
                    return property.referencedObject().asConstructionStrategy(bean.setter(property).parameters().get(0).instance.aClass);
                }
            });
        }
    }

}
