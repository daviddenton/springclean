package springclean.core.domain;

import org.daisychain.source.AClass;
import static org.daisychain.source.ExistingClass.existingClass;
import static springclean.core.domain.SpringId.springId;
import springclean.core.generate.ConstructionStrategy;
import springclean.core.generate.ConstructionStrategyBuilder;

public class ReferenceBuilder {
    private SpringId springId = springId("springId");
    private ConstructionStrategy constructionStrategy = ConstructionStrategyBuilder.aConstructionStrategy().build();
    private AClass aClass = existingClass(String.class);

    private ReferenceBuilder() {
    }

    public static ReferenceBuilder aReference() {
        return new ReferenceBuilder();
    }

    public ReferenceBuilder withIdentity(SpringId springId) {
        this.springId = springId;
        return this;
    }

    public ReferenceBuilder withClass(AClass aClass) {
        this.aClass = aClass;
        return this;
    }

    public ReferenceBuilder whichProducesConstructionStrategy(ConstructionStrategy constructionStrategy) {
        this.constructionStrategy = constructionStrategy;
        return this;
    }

    public Reference build() {
        return new Reference() {
            public SpringId id() {
                return springId;
            }

            public ConstructionStrategy asConstructionStrategy(AClass aClass) {
                return constructionStrategy;
            }

            public AClass clazz() {
                return aClass;
            }
        };
    }
}