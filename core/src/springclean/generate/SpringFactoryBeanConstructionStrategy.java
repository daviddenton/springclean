package springclean.generate;

import org.daisychain.source.Instance;
import org.daisychain.source.body.AssignableStatement;

import java.util.Set;

public class SpringFactoryBeanConstructionStrategy implements ConstructionStrategy {
    private final ConstructionStrategy innerConstructionStrategy;

    public SpringFactoryBeanConstructionStrategy(ConstructionStrategy innerConstructionStrategy) {
        this.innerConstructionStrategy = innerConstructionStrategy;
    }

    public AssignableStatement asStatement() {
        return innerConstructionStrategy.asStatement();
    }

    public Set<Instance> dependencies() {
        return innerConstructionStrategy.dependencies();
    }
}