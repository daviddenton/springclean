package springclean.core.generate;

import static com.google.common.collect.Lists.newArrayList;
import org.daisychain.source.AClass;
import static org.daisychain.source.HasImports.ImportExtractor.extractImportsFrom;
import org.daisychain.source.body.AssignableStatement;
import org.daisychain.source.util.IndentingStringWriter;
import springclean.core.domain.Bean;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class ConstructorInvocation implements AssignableStatement {

    private final Bean bean;
    private final List<AssignableStatement> constructorStatements = newArrayList();

    public ConstructorInvocation(Bean bean) {
        this.bean = bean;
        for (int i = 0; i < bean.constructorArgs().size(); i++) {
            constructorStatements.add(bean.constructorArgs().get(i).referencedObject().asContextElement(bean.constructor().parameters().get(i).instance.aClass).asStatement());
        }
    }

    public Set<AClass> getImports() {
        return extractImportsFrom(constructorStatements);
    }

    public void appendSource(IndentingStringWriter writer) throws IOException {
        bean.clazz().instantiate(constructorStatements).appendSource(writer);
    }

}
