package springclean.domain;

import springclean.core.domain.ConstructorArg;
import springclean.core.domain.SpringManagedObject;
import static springclean.domain.BeanBuilder.aBean;

public class ConstructorArgBuilder {
    private SpringManagedObject springManagedObject = aBean().build();

    private ConstructorArgBuilder() {
    }

    public static ConstructorArgBuilder aConstructorArg() {
        return new ConstructorArgBuilder();
    }

    public ConstructorArgBuilder withSpringManagedObject(SpringManagedObject springManagedObject) {
        this.springManagedObject = springManagedObject;
        return this;
    }

    public ConstructorArg build() {
        return new ConstructorArg() {
            public SpringManagedObject referencedObject() {
                return springManagedObject;
            }
        };
    }
}
