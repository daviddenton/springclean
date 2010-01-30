package springclean.core.generate;

import static com.google.common.collect.Lists.newArrayList;
import org.daisychain.source.AClass;
import static org.daisychain.source.HasImports.ImportExtractor.extractImportsFrom;
import org.daisychain.source.body.AssignableStatement;
import org.daisychain.source.util.DynamicContent;
import org.daisychain.source.util.IndentingStringWriter;
import static org.daisychain.source.util.ListAppender.generateSource;
import static org.daisychain.source.util.ListAppender.loop;
import springclean.core.domain.Bean;
import springclean.core.domain.Property;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class SetterInjection implements AssignableStatement {

    private final List<AssignableStatement> postConstructionStatements = newArrayList();

    public SetterInjection(Bean bean) {
        for (Property property : bean.setterDependencies()) {
            AssignableStatement assignableStatement = property.referencedObject().asContextElement(bean.setter(property).parameters().get(0).instance.aClass).asStatement();
            postConstructionStatements.add(bean.setter(property).call(Collections.singletonList(assignableStatement)));
        }

        if (bean.hasInitMethod()) {
            postConstructionStatements.add(bean.initMethod().call(Collections.EMPTY_LIST));
        }
    }

    public Set<AClass> getImports() {
        return extractImportsFrom(postConstructionStatements);
    }

    public void appendSource(IndentingStringWriter writer) throws IOException {
        loop(postConstructionStatements)
                .withPrefix(new InitializerBlockStart())
                .andForEach(generateSource()).seperatedBy(";\n")
                .withSuffix(new InitializerBlockEnd())
                .to(writer);
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