package springclean.core.generate;

import org.daisychain.source.AClass;
import org.daisychain.source.body.AssignableStatement;
import org.daisychain.source.body.Cast;
import org.daisychain.source.util.IndentingStringWriter;

import java.io.IOException;
import java.util.Set;

public class FactoryBeanStatement implements AssignableStatement {
    private final AssignableStatement innerStatement;

    public FactoryBeanStatement(AClass castClass, AssignableStatement assignableStatement) {
        innerStatement = new Cast(castClass, assignableStatement);
    }

    public Set<AClass> getImports() {
        return innerStatement.getImports();
    }

    public void appendSource(IndentingStringWriter indentingStringWriter) throws IOException {
        innerStatement.appendSource(indentingStringWriter);
        indentingStringWriter.append(".getObject()");
    }
}
