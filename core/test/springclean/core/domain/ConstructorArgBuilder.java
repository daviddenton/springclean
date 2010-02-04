package springclean.core.domain;

import static springclean.core.domain.BeanBuilder.aBean;
import springclean.core.exception.Defect;

public class ConstructorArgBuilder {
    private SpringManagedObject springManagedObject = aBean().build();
    private Integer index;

    private ConstructorArgBuilder() {
    }

    public static ConstructorArgBuilder aConstructorArg() {
        return new ConstructorArgBuilder();
    }

    public ConstructorArgBuilder withSpringManagedObject(SpringManagedObject springManagedObject) {
        this.springManagedObject = springManagedObject;
        return this;
    }

    public ConstructorArgBuilder withIndex(int index) {
        this.index = index;
        return this;
    }

    public ConstructorArg build() {
        return new ConstructorArg() {
            public SpringManagedObject referencedObject() {
                return springManagedObject;
            }

            public boolean isIndexed() {
                return index != null;
            }

            public int index() {
                if (!isIndexed()) throw new Defect("no index");
                return index;
            }
        };
    }
}
