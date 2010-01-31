package springclean.core.generate;

import org.daisychain.source.Instance;
import org.daisychain.source.body.AssignableStatement;
import springclean.core.domain.Bean;
import static springclean.core.domain.Property.Util.propertyInjectedDependencies;
import static springclean.core.generate.ContextElement.DependencyExtractor.allDependenciesOf;

import java.util.Set;

public class StaticFactoryMethodBeanConstructionStrategy implements ConstructionStrategy {
    private final Bean bean;

    public StaticFactoryMethodBeanConstructionStrategy(Bean bean) {
        this.bean = bean;
    }

    public AssignableStatement asStatement() {
        return new StaticFactoryMethodInvocation(bean);
    }

    public Set<Instance> dependencies() {
        return allDependenciesOf(propertyInjectedDependencies(bean));
    }

}
