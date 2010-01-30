package springclean.core.generate;

import static com.google.common.collect.Lists.newArrayList;
import org.daisychain.source.AClass;
import static org.daisychain.source.HasImports.ImportExtractor.extractImportsFrom;
import org.daisychain.source.body.AssignableStatement;
import org.daisychain.source.util.IndentingStringWriter;

import java.io.IOException;
import java.util.Set;

public class NestedStatement implements AssignableStatement {
    private final AssignableStatement[] assignableStatements;

    public NestedStatement(AssignableStatement... assignableStatements) {
        this.assignableStatements = assignableStatements;
    }

    public Set<AClass> getImports() {
        return extractImportsFrom(newArrayList(assignableStatements));
    }

    public void appendSource(IndentingStringWriter writer) throws IOException {
        for (AssignableStatement assignableStatement : assignableStatements) {
            assignableStatement.appendSource(writer);
        }
    }

}
