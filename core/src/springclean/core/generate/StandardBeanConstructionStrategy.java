package springclean.core.generate;

import org.daisychain.source.Instance;
import org.daisychain.source.body.AssignableStatement;
import springclean.core.domain.Bean;
import static springclean.core.domain.ConstructorArg.Util.constructorInjectedDependencies;
import static springclean.core.domain.Property.Util.propertyInjectedDependencies;
import static springclean.core.generate.ConstructionStrategy.Util.allDependenciesOf;

import java.util.Set;

public class StandardBeanConstructionStrategy implements ConstructionStrategy {
    private final Bean bean;

    public StandardBeanConstructionStrategy(Bean bean) {
        this.bean = bean;
    }

    public AssignableStatement asStatement() {
        return new NestedStatement(new ConstructorInvocation(bean), new SetterInjection(bean));
    }

    public Set<Instance> dependencies() {
        return allDependenciesOf(constructorInjectedDependencies(bean), propertyInjectedDependencies(bean));
    }
}
