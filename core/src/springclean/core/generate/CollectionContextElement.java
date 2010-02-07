package springclean.core.generate;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import org.daisychain.source.*;
import static org.daisychain.source.ExistingClass.existingClass;
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
        // this will not work!!!
        return newHashSet();
    }

    public AssignableStatement asStatement() {
        return new CollectionConstruction(beanCollection);
    }

    private static class CollectionConstruction implements AssignableStatement {
        private final BeanCollection beanCollection;
        private final List<AssignableStatement> postConstructionStatements = newArrayList();

        public CollectionConstruction(BeanCollection beanCollection) {
            this.beanCollection = beanCollection;
            Method addMethod = new MethodFinder<ExistingMethod>(beanCollection.clazz()).method("add", 1);

            for (SpringManagedObject member : beanCollection.members()) {
                AssignableStatement memberStatement = member.asConstructionStrategy(existingClass(Object.class)).asStatement();
                postConstructionStatements.add(addMethod.call(singletonList(memberStatement)));
            }
        }

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
    }
}
