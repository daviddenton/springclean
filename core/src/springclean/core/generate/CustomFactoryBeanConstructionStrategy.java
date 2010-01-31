package springclean.core.generate;

import static com.google.common.collect.Lists.newArrayList;
import org.daisychain.source.Instance;
import org.daisychain.source.body.AssignableStatement;
import springclean.core.domain.Bean;
import springclean.core.domain.Property;
import springclean.core.domain.Reference;
import static springclean.core.generate.ContextElement.DependencyExtractor.allDependenciesOf;

import java.util.List;
import java.util.Set;

public class CustomFactoryBeanConstructionStrategy implements ConstructionStrategy {
    private final Reference reference;
    private final Bean bean;

    public CustomFactoryBeanConstructionStrategy(Reference reference, Bean bean) {
        this.reference = reference;
        this.bean = bean;
    }

    public AssignableStatement asStatement() {
        return new FactoryMethodInvocation(bean, CustomFactoryBeanConstructionStrategy.this.reference);

    }

    public Set<Instance> dependencies() {
        final List<ContextElement> injectedDependencies = newArrayList();
        for (Property property : bean.setterDependencies()) {
            injectedDependencies.add(property.referencedObject().asContextElement(bean.setter(property).parameters().get(0).instance.aClass));
        }
        return allDependenciesOf(injectedDependencies);
    }

}
