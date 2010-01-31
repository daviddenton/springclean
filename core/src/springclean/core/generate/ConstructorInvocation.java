package springclean.core.generate;

import org.daisychain.source.AClass;
import static org.daisychain.source.HasImports.ImportExtractor.extractImportsFrom;
import org.daisychain.source.body.AssignableStatement;
import org.daisychain.source.util.IndentingStringWriter;
import springclean.core.domain.Bean;
import static springclean.core.domain.Bean.Util.asStatements;

import java.io.IOException;
import java.util.Set;

public class ConstructorInvocation implements AssignableStatement {

    private final Bean bean;

    public ConstructorInvocation(Bean bean) {
        this.bean = bean;
    }

    public Set<AClass> getImports() {
        return extractImportsFrom(asStatements(bean));
    }

    public void appendSource(IndentingStringWriter writer) throws IOException {
        bean.clazz().instantiate(asStatements(bean)).appendSource(writer);
    }

}
