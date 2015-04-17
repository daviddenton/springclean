package springclean.core.generate;

import org.daisychain.source.Instance;
import org.daisychain.source.body.AssignableStatement;
import springclean.core.domain.IdentifiedBean;

import java.util.Set;

public class IdentifiedBeanConstructionStrategy implements ConstructionStrategy {
    private final ConstructionStrategy innerConstructionStrategy;
    private final Instance instance;

    public IdentifiedBeanConstructionStrategy(IdentifiedBean bean, ConstructionStrategy innerConstructionStrategy) {
        this.innerConstructionStrategy = innerConstructionStrategy;
        instance = new Instance(bean.id().value, bean.declaredBeanClass());
    }

    public AssignableStatement asStatement() {
        return instance.reference.assignTo(innerConstructionStrategy.asStatement());
    }

    public Set<Instance> dependencies() {
        return innerConstructionStrategy.dependencies();
    }
}
