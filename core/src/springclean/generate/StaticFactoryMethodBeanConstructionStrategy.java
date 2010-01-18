package springclean.generate;

import static com.google.common.collect.Lists.newArrayList;
import org.daisychain.source.AClass;
import static org.daisychain.source.HasImports.ImportExtractor.extractImportsFrom;
import org.daisychain.source.Instance;
import org.daisychain.source.body.AssignableStatement;
import org.daisychain.source.util.IndentingStringWriter;
import springclean.domain.Bean;
import springclean.domain.ConstructorArg;
import springclean.domain.Property;
import static springclean.generate.ContextElement.DependencyExtractor.allDependenciesOf;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class StaticFactoryMethodBeanConstructionStrategy implements ConstructionStrategy {
    private final Bean bean;

    public StaticFactoryMethodBeanConstructionStrategy(Bean bean) {
        this.bean = bean;
    }

    public AssignableStatement asStatement() {
        final List<AssignableStatement> argumentStatements = newArrayList();
        int count=0;
        for (ConstructorArg constructorArg : bean.constructorArgs()) {
            argumentStatements.add(constructorArg.referencedObject().asContextElement(bean.factoryMethod().parameters().get(count++).instance.aClass).asStatement());
        }

        return new AssignableStatement() {
            public Set<AClass> getImports() {
                return extractImportsFrom(argumentStatements);
            }

            public void appendSource(IndentingStringWriter writer) throws IOException {
                bean.clazz().call(bean.factoryMethod().name(), argumentStatements).appendSource(writer);
                if (bean.hasInitMethod()) {
                    new Instance("bean", bean.clazz()).call(bean.initMethod().name()).appendSource(writer);
                }
            }
        };
    }

    public Set<Instance> dependencies() {
        final List<ContextElement> injectedDependencies = newArrayList();
        for (Property property : bean.setterDependencies()) {
            injectedDependencies.add(property.referencedObject().asContextElement(bean.setter(property).parameters().get(0).instance.aClass));
        }
        return allDependenciesOf(injectedDependencies);
    }
}
