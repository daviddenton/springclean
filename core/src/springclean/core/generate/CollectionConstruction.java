package springclean.core.generate;

import static com.google.common.collect.Lists.newArrayList;
import org.daisychain.source.AClass;
import org.daisychain.source.ExistingClass;
import static org.daisychain.source.HasImports.ImportExtractor.extractImportsFrom;
import org.daisychain.source.Method;
import org.daisychain.source.MethodFinder;
import org.daisychain.source.body.AssignableStatement;
import org.daisychain.source.util.IndentingStringWriter;
import static org.daisychain.source.util.ListAppender.generateSource;
import static org.daisychain.source.util.ListAppender.loop;
import springclean.core.domain.BeanCollection;
import springclean.core.domain.SpringManagedObject;

import java.io.IOException;
import java.util.Collections;
import static java.util.Collections.singletonList;
import java.util.List;
import java.util.Set;

public class CollectionConstruction implements AssignableStatement {

    private final List<AssignableStatement> postConstructionStatements = newArrayList();
    private final BeanCollection beanCollection;

    public CollectionConstruction(BeanCollection beanCollection) {
        this.beanCollection = beanCollection;
        Method addMethod = new MethodFinder(beanCollection.clazz()).method("add", 1);

        for (SpringManagedObject member : beanCollection.members()) {
            AssignableStatement memberStatement = member.asConstructionStrategy(new ExistingClass(Object.class)).asStatement();
            postConstructionStatements.add(addMethod.call(singletonList(memberStatement)));
        }
    }

    public Set<AClass> getImports() {
        return extractImportsFrom(singletonList(beanCollection.clazz()), postConstructionStatements);
    }

    public void appendSource(IndentingStringWriter writer) throws IOException {
        beanCollection.clazz().instantiate(Collections.EMPTY_LIST).appendSource(writer);
        loop(postConstructionStatements)
                .withPrefix(new InitializerBlockStart())
                .andForEach(generateSource()).seperatedBy(";\n")
                .withSuffix(new InitializerBlockEnd())
                .to(writer);
    }

}