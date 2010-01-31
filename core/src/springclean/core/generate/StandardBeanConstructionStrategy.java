package springclean.core.generate;

import com.google.common.base.Function;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;
import org.daisychain.source.Instance;
import org.daisychain.source.body.AssignableStatement;
import springclean.core.domain.Bean;
import springclean.core.domain.Property;
import static springclean.core.generate.ContextElement.DependencyExtractor.allDependenciesOf;

import java.util.List;
import java.util.Set;

public class StandardBeanConstructionStrategy implements ConstructionStrategy {
    private final Bean bean;
    private final List<ContextElement> constructorInjectedDependencies;
    private final List<ContextElement> propertyInjectedDependencies;

    public StandardBeanConstructionStrategy(Bean bean) {
        this.bean = bean;
        this.constructorInjectedDependencies = constructorInjectedDependencies(bean);
        this.propertyInjectedDependencies = propertyInjectedDependencies(bean);
    }

    private static List<ContextElement> propertyInjectedDependencies(final Bean bean) {
        return transform(bean.setterDependencies(), new Function<Property, ContextElement>() {
            public ContextElement apply(Property property) {
                return property.referencedObject().asContextElement(bean.setter(property).parameters().get(0).instance.aClass);
            }
        });
    }

    private static List<ContextElement> constructorInjectedDependencies(final Bean bean) {
        final List<ContextElement> injectedDependencies = newArrayList();
        for (int i = 0; i < bean.constructorArgs().size(); i++) {
            injectedDependencies.add(bean.constructorArgs().get(i).referencedObject().asContextElement(bean.constructor().parameters().get(i).instance.aClass));
        }
        return injectedDependencies;
    }

    public AssignableStatement asStatement() {
        return new NestedStatement(new ConstructorInvocation(bean), new SetterInjection(bean));
    }

    public Set<Instance> dependencies() {
        return allDependenciesOf(constructorInjectedDependencies, propertyInjectedDependencies);
    }
}
