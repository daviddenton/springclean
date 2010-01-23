package springclean.generate;

import org.daisychain.source.AClass;
import org.daisychain.source.Instance;
import org.daisychain.source.body.AssignableStatement;
import org.daisychain.source.util.IndentingStringWriter;

import java.io.IOException;
import java.util.Set;

public class SpringFactoryBeanConstructionStrategy implements ConstructionStrategy {
    private final AClass castClass;
    private final ConstructionStrategy innerConstructionStrategy;

    public SpringFactoryBeanConstructionStrategy(AClass castClass, ConstructionStrategy innerConstructionStrategy) {
        this.castClass = castClass;
        this.innerConstructionStrategy = innerConstructionStrategy;
    }

    public static class Cast implements AssignableStatement {

        private final AClass castToType;
        private final AssignableStatement targetStatement;

        public Cast(AClass castToType, AssignableStatement targetStatement) {
            this.castToType = castToType;
            this.targetStatement = targetStatement;
        }

        public Set<AClass> getImports() {
            Set<AClass> imports = targetStatement.getImports();
            imports.addAll(castToType.getImports());
            return imports;
        }

        public void appendSource(IndentingStringWriter indentingStringWriter) throws IOException {
            indentingStringWriter.append("(").append(castToType.simpleName()).append(") ");
            targetStatement.appendSource(indentingStringWriter);
        }
    }

    public AssignableStatement asStatement() {
        final AssignableStatement innerStatement = new Cast(castClass, innerConstructionStrategy.asStatement());
        return new AssignableStatement() {
            public Set<AClass> getImports() {
                return innerStatement.getImports();
            }

            public void appendSource(IndentingStringWriter indentingStringWriter) throws IOException {
                innerStatement.appendSource(indentingStringWriter);
                indentingStringWriter.append(".getObject()");
            }
        };
    }

    public Set<Instance> dependencies() {
        return innerConstructionStrategy.dependencies();
    }
}