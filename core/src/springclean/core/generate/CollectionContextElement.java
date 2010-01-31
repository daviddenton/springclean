package springclean.core.generate;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import org.daisychain.source.*;
import static org.daisychain.source.HasImports.ImportExtractor.extractImportsFrom;
import org.daisychain.source.body.AssignableStatement;
import org.daisychain.source.util.IndentingStringWriter;
import static org.daisychain.source.util.ListAppender.generateSource;
import static org.daisychain.source.util.ListAppender.loop;
import springclean.core.domain.BeanCollection;
import springclean.core.domain.SpringManagedObject;

import java.io.IOException;
import static java.util.Collections.EMPTY_LIST;
import static java.util.Collections.singletonList;
import java.util.List;
import java.util.Set;


public class CollectionContextElement implements ConstructionStrategy {
    private final BeanCollection beanCollection;

    public CollectionContextElement(BeanCollection beanCollection) {
        this.beanCollection = beanCollection;
    }

    public Set<Instance> dependencies() {
        return newHashSet();
    }

    public AssignableStatement asStatement() {
        Method addMethod = new MethodFinder<ExistingMethod>(beanCollection.clazz()).method("add", 1);
        final List<AssignableStatement> postConstructionStatements = newArrayList();

        for (SpringManagedObject member : beanCollection.members()) {
            AssignableStatement memberStatement = member.asConstructionStrategy(new ExistingClass(Object.class)).asStatement();
            postConstructionStatements.add(addMethod.call(singletonList(memberStatement)));
        }

        return new AssignableStatement() {
            public Set<AClass> getImports() {
                return extractImportsFrom(singletonList(beanCollection.clazz()), postConstructionStatements);
            }

            public void appendSource(IndentingStringWriter writer) throws IOException {
                beanCollection.clazz().instantiate(EMPTY_LIST).appendSource(writer);
                loop(postConstructionStatements)
                        .withPrefix(new InitializerBlockStart())
                        .andForEach(generateSource()).seperatedBy(";\n")
                        .withSuffix(new InitializerBlockEnd())
                        .to(writer);
            }
        };

    }
}
