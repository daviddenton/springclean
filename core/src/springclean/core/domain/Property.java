package springclean.core.domain;

import com.google.common.base.Function;
import static com.google.common.collect.Lists.transform;
import springclean.core.generate.ContextElement;

import java.util.List;

public interface Property extends InjectedDependency {
    PropertyName name();

    public static class Util {
        public static List<ContextElement> propertyInjectedDependencies(final Bean bean) {
            return transform(bean.setterDependencies(), new Function<Property, ContextElement>() {
                public ContextElement apply(Property property) {
                    return property.referencedObject().asContextElement(bean.setter(property).parameters().get(0).instance.aClass);
                }
            });
        }
    }

}
