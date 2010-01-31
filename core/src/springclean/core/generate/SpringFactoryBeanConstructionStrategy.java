package springclean.core.generate;

import org.daisychain.source.AClass;
import org.daisychain.source.Instance;
import org.daisychain.source.body.AssignableStatement;

import java.util.Set;

public class SpringFactoryBeanConstructionStrategy implements ConstructionStrategy {
    private final AClass castClass;
    private final ConstructionStrategy innerConstructionStrategy;

    public SpringFactoryBeanConstructionStrategy(AClass castClass, ConstructionStrategy innerConstructionStrategy) {
        this.castClass = castClass;
        this.innerConstructionStrategy = innerConstructionStrategy;
    }

    public AssignableStatement asStatement() {
        return new SpringFactoryBeanInvocation(castClass, innerConstructionStrategy.asStatement());
    }

    public Set<Instance> dependencies() {
        return innerConstructionStrategy.dependencies();
    }

}