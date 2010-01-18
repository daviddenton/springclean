package springclean.generate;

import org.daisychain.source.Instance;
import org.daisychain.source.body.AssignableStatement;
import springclean.domain.IdentifiedBean;
import static springclean.generate.ContextElement.DependencyExtractor.allDependenciesOf;

import static java.util.Arrays.asList;
import java.util.Set;

public class IdentifiedBeanConstructionStrategy implements ConstructionStrategy {
    private final IdentifiedBean bean;
    private final ConstructionStrategy innerConstructionStrategy;

    public IdentifiedBeanConstructionStrategy(IdentifiedBean bean, ConstructionStrategy innerConstructionStrategy) {
        this.bean = bean;
        this.innerConstructionStrategy = innerConstructionStrategy;
    }

    public AssignableStatement asStatement() {
        return new Instance(bean.id().value, bean.clazz()).reference.assignTo(innerConstructionStrategy.asStatement());
    }

    public Set<Instance> dependencies() {
        return allDependenciesOf(asList(innerConstructionStrategy));
    }
}
