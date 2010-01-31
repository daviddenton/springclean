package springclean.core.generate;

import org.daisychain.source.AClass;
import static org.daisychain.source.HasImports.ImportExtractor.extractImportsFrom;
import org.daisychain.source.body.AssignableStatement;
import org.daisychain.source.body.MethodCall;
import org.daisychain.source.util.IndentingStringWriter;
import springclean.core.domain.Bean;
import springclean.core.domain.Reference;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class FactoryMethodInvocation implements AssignableStatement {
    private final List<AssignableStatement> argumentStatements = com.google.common.collect.Lists.newArrayList();
    private final Reference reference;
    private final Bean bean;

    public FactoryMethodInvocation(Bean bean, Reference reference) {
        this.bean = bean;
        for (int i = 0; i < bean.constructorArgs().size(); i++) {
            argumentStatements.add(bean.constructorArgs().get(i).referencedObject().asContextElement(bean.factoryMethod().parameters().get(i).instance.aClass).asStatement());
        }
        this.reference = reference;
    }

    public Set<AClass> getImports() {
        return extractImportsFrom(argumentStatements);
    }

    public void appendSource(IndentingStringWriter writer) throws IOException {
        new MethodCall(new org.daisychain.source.Reference(reference.id().value), bean.factoryMethod(), argumentStatements).appendSource(writer);
    }
}
