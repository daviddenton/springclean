package springclean.core.generate;

import static com.google.common.collect.Sets.newHashSet;
import static org.daisychain.source.AssignableStatementBuilder.anAssignableStatement;
import org.daisychain.source.Instance;
import org.daisychain.source.body.AssignableStatement;

import java.util.Set;

public class ConstructionStrategyBuilder {
    private Set<Instance> dependencies = newHashSet();
    private AssignableStatement statement = anAssignableStatement().build();

    private ConstructionStrategyBuilder() {
    }

    public static ConstructionStrategyBuilder aConstructionStrategy() {
        return new ConstructionStrategyBuilder();
    }

    public ConstructionStrategyBuilder withDependency(Instance dependency) {
        this.dependencies.add(dependency);
        return this;
    }

    public ConstructionStrategyBuilder whichProducesStatement(AssignableStatement statement) {
        this.statement = statement;
        return this;
    }

    public ConstructionStrategy build() {
        return new ConstructionStrategy() {

            public Set<Instance> dependencies() {
                return dependencies;
            }

            public AssignableStatement asStatement() {
                return statement;
            }
        };
    }
}