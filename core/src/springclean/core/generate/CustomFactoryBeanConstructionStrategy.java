package springclean.core.generate;

import org.daisychain.source.Instance;
import org.daisychain.source.body.AssignableStatement;
import springclean.core.domain.Bean;
import static springclean.core.domain.Property.Util.propertyInjectedDependencies;
import springclean.core.domain.Reference;
import static springclean.core.generate.ContextElement.DependencyExtractor.allDependenciesOf;

import java.util.Set;

public class CustomFactoryBeanConstructionStrategy implements ConstructionStrategy {
    private final Reference reference;
    private final Bean bean;

    public CustomFactoryBeanConstructionStrategy(Reference reference, Bean bean) {
        this.reference = reference;
        this.bean = bean;
    }

    public AssignableStatement asStatement() {
        return new FactoryMethodInvocation(bean, reference);

    }

    public Set<Instance> dependencies() {
        return allDependenciesOf(propertyInjectedDependencies(bean));
    }

}
