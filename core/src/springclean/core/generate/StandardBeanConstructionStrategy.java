package springclean.core.generate;

import static com.google.common.collect.Lists.newArrayList;
import org.daisychain.source.AClass;
import static org.daisychain.source.HasImports.ImportExtractor.extractImportsFrom;
import org.daisychain.source.Instance;
import org.daisychain.source.body.AssignableStatement;
import org.daisychain.source.util.DynamicContent;
import org.daisychain.source.util.IndentingStringWriter;
import static org.daisychain.source.util.ListAppender.generateSource;
import springclean.core.domain.Bean;
import springclean.core.domain.ConstructorArg;
import springclean.core.domain.Property;
import static springclean.core.generate.ContextElement.DependencyExtractor.allDependenciesOf;

import java.io.IOException;
import java.util.Collections;
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
        final List<ContextElement> injectedDependencies = newArrayList();
        for (Property property : bean.setterDependencies()) {
            injectedDependencies.add(property.referencedObject().asContextElement(bean.setter(property).parameters().get(0).instance.aClass));
        }
        return injectedDependencies;
    }

    private static List<ContextElement> constructorInjectedDependencies(final Bean bean) {
        final List<ContextElement> injectedDependencies = newArrayList();
        int count = 0;
        for (ConstructorArg constructorArg : bean.constructorArgs()) {
            injectedDependencies.add(constructorArg.referencedObject().asContextElement(bean.constructor().parameters().get(count++).instance.aClass));
        }
        return injectedDependencies;
    }

    public AssignableStatement asStatement() {
        final List<AssignableStatement> postConstructionStatements = newArrayList();
        for (Property property : bean.setterDependencies()) {
            AssignableStatement assignableStatement = property.referencedObject().asContextElement(bean.setter(property).parameters().get(0).instance.aClass).asStatement();
            postConstructionStatements.add(bean.setter(property).call(Collections.singletonList(assignableStatement)));
        }

        if (bean.hasInitMethod()) {
            postConstructionStatements.add(bean.initMethod().call(Collections.EMPTY_LIST));
        }

        final List<AssignableStatement> constructorStatements = newArrayList();

        int count = 0;
        for (ConstructorArg constructorArg : bean.constructorArgs()) {
            constructorStatements.add(constructorArg.referencedObject().asContextElement(bean.constructor().parameters().get(count++).instance.aClass).asStatement());
        }

        return new AssignableStatement() {
            public Set<AClass> getImports() {
                return extractImportsFrom(postConstructionStatements, constructorStatements);
            }

            public void appendSource(IndentingStringWriter writer) throws IOException {
                bean.clazz().instantiate(constructorStatements).appendSource(writer);
                org.daisychain.source.util.ListAppender.loop(postConstructionStatements)
                        .withPrefix(new InitializerBlockStart())
                        .andForEach(generateSource()).seperatedBy(";\n")
                        .withSuffix(new InitializerBlockEnd())
                        .to(writer);
            }
        };
    }

    public Set<Instance> dependencies() {
        return allDependenciesOf(constructorInjectedDependencies, propertyInjectedDependencies);
    }

    private static class InitializerBlockEnd implements DynamicContent {
        public void append(IndentingStringWriter writer) throws IOException {
            writer.append(";").exdent().newLine().append("}}");
        }
    }

    private static class InitializerBlockStart implements DynamicContent {
        public void append(IndentingStringWriter writer) throws IOException {
            writer.indent().append(" {{").newLine();
        }
    }

}
