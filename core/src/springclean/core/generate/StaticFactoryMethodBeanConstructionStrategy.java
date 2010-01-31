package springclean.core.generate;

import com.google.common.base.Function;
import static com.google.common.collect.Lists.transform;
import org.daisychain.source.Instance;
import org.daisychain.source.body.AssignableStatement;
import springclean.core.domain.Bean;
import springclean.core.domain.Property;
import static springclean.core.generate.ContextElement.DependencyExtractor.allDependenciesOf;

import java.util.Set;

public class StaticFactoryMethodBeanConstructionStrategy implements ConstructionStrategy {
    private final Bean bean;

    public StaticFactoryMethodBeanConstructionStrategy(Bean bean) {
        this.bean = bean;
    }

    public AssignableStatement asStatement() {
        return new StaticFactoryMethod(bean);
    }

    public Set<Instance> dependencies() {
        return allDependenciesOf(transform(bean.setterDependencies(), new Function<Property, ConstructionStrategy>() {
            public ConstructionStrategy apply(Property property) {
                return property.referencedObject().asConstructionStrategy(bean.setter(property).parameters().get(0).instance.aClass);
            }
        }));
    }

}
