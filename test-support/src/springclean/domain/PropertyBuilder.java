package springclean.domain;

import static springclean.domain.BeanBuilder.aBean;
import static springclean.domain.PropertyName.propertyName;

public class PropertyBuilder {

    private PropertyName propertyName = propertyName("propertyName");
    private SpringManagedObject springManagedObject = aBean().build();

    private PropertyBuilder() {}

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
