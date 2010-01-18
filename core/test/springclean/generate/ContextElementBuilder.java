package springclean.generate;

import static com.google.common.collect.Sets.newHashSet;
import static org.daisychain.source.AssignableStatementBuilder.anAssignableStatement;
import org.daisychain.source.Instance;
import org.daisychain.source.body.AssignableStatement;

import java.util.Set;

public class ContextElementBuilder {
    private Set<Instance> dependencies = newHashSet();
    private AssignableStatement statement = anAssignableStatement().build();
    
    private ContextElementBuilder() {
    }

    public static ContextElementBuilder aContextElement() {
        return new ContextElementBuilder();
    }

    public ContextElementBuilder withDependency(Instance dependency) {
        this.dependencies.add(dependency);
        return this;
    }

    public ContextElementBuilder whichProducesStatement(AssignableStatement statement) {
        this.statement = statement;
        return this;
    }

    public ContextElement build() {
        return new ContextElement() {
 
            public Set<Instance> dependencies() {
                return dependencies;
            }

            public AssignableStatement asStatement() {
                return statement;
            }
        };
    }
}