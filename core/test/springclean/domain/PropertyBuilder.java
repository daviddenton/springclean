package springclean.domain;

import springclean.core.domain.Property;
import springclean.core.domain.PropertyName;
import static springclean.core.domain.PropertyName.propertyName;
import springclean.core.domain.SpringManagedObject;
import static springclean.domain.BeanBuilder.aBean;

public class PropertyBuilder {

    private PropertyName propertyName = propertyName("propertyName");
    private SpringManagedObject springManagedObject = aBean().build();

    private PropertyBuilder() {
    }

    public static PropertyBuilder aProperty() {
        return new PropertyBuilder();
    }

    public PropertyBuilder withPropertyName(PropertyName propertyName) {
        this.propertyName = propertyName;
        return this;
    }

    public PropertyBuilder withSpringManagedObject(SpringManagedObject springManagedObject) {
        this.springManagedObject = springManagedObject;
        return this;
    }

    public Property build() {
        return new Property() {
            public PropertyName name() {
                return propertyName;
            }

            public SpringManagedObject referencedObject() {
                return springManagedObject;
            }
        };
    }
}
