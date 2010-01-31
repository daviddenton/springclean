package springclean.core.generate;

import org.daisychain.source.AClass;
import static org.daisychain.source.HasImports.ImportExtractor.extractImportsFrom;
import org.daisychain.source.Instance;
import org.daisychain.source.body.AssignableStatement;
import org.daisychain.source.util.IndentingStringWriter;
import springclean.core.domain.Bean;
import static springclean.core.domain.ConstructorArg.Util.asStatements;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class StaticFactoryMethodInvocation implements AssignableStatement {
    private final List<AssignableStatement> argumentStatements;
    private final Bean bean;

    public StaticFactoryMethodInvocation(Bean bean) {
        this.bean = bean;
        this.argumentStatements = asStatements(bean.factoryMethod(), bean.constructorArgs());
    }

    public Set<AClass> getImports() {
        return extractImportsFrom(argumentStatements);
    }

    public void appendSource(IndentingStringWriter writer) throws IOException {
        bean.clazz().call(bean.factoryMethod().name(), argumentStatements).appendSource(writer);
        if (bean.hasInitMethod()) {
            new Instance("bean", bean.clazz()).call(bean.initMethod().name()).appendSource(writer);
        }
    }
}
