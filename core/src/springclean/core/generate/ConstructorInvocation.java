package springclean.core.generate;

import org.daisychain.source.AClass;
import static org.daisychain.source.HasImports.ImportExtractor.extractImportsFrom;
import org.daisychain.source.body.AssignableStatement;
import org.daisychain.source.util.IndentingStringWriter;
import springclean.core.domain.Bean;
import static springclean.core.domain.ConstructorArg.Util.asStatements;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class ConstructorInvocation implements AssignableStatement {

    private final Bean bean;
    private final List<AssignableStatement> constructorStatements;

    public ConstructorInvocation(Bean bean) {
        this.bean = bean;
        constructorStatements = asStatements(bean.constructor(), bean.constructorArgs());
    }

    public Set<AClass> getImports() {
        return extractImportsFrom(constructorStatements);
    }

    public void appendSource(IndentingStringWriter writer) throws IOException {
        bean.clazz().instantiate(constructorStatements).appendSource(writer);
    }

}
