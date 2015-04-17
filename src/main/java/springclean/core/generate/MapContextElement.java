package springclean.core.generate;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import org.daisychain.source.AClass;
import org.daisychain.source.ExistingClass;
import static org.daisychain.source.ExistingClass.existingClass;
import static org.daisychain.source.HasImports.ImportExtractor.extractImportsFrom;
import org.daisychain.source.Instance;
import org.daisychain.source.body.AssignableStatement;
import org.daisychain.source.util.IndentingStringWriter;
import static org.daisychain.source.util.ListAppender.generateSource;
import static org.daisychain.source.util.ListAppender.loop;
import springclean.core.domain.BeanMap;
import springclean.core.domain.BeanMapEntry;

import java.io.IOException;
import static java.util.Collections.EMPTY_LIST;
import static java.util.Collections.singletonList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


public class MapContextElement implements ConstructionStrategy {
    private final BeanMap beanMap;
    private static final ExistingClass MAP_CLASS = existingClass(HashMap.class);

    public MapContextElement(BeanMap beanMap) {
        this.beanMap = beanMap;
    }

    public Set<Instance> dependencies() {
        // will not work
        return newHashSet();
    }

    public AssignableStatement asStatement() {
        return new MapConstruction(beanMap);
    }

    private static class MapConstruction implements AssignableStatement {
        private final List<AssignableStatement> postConstructionStatements = newArrayList();

        public MapConstruction(BeanMap beanMap) {
            for (BeanMapEntry entry : beanMap.entries()) {
                postConstructionStatements.add(entry.asConstructionStrategy(existingClass(Object.class)).asStatement());
            }
        }

        public Set<AClass> getImports() {
            return extractImportsFrom(singletonList(MAP_CLASS), postConstructionStatements);
        }

        public void appendSource(IndentingStringWriter writer) throws IOException {
            MAP_CLASS.instantiate(EMPTY_LIST).appendSource(writer);
            loop(postConstructionStatements)
                    .withPrefix(new InitializerBlockStart())
                    .andForEach(generateSource()).seperatedBy(";\n")
                    .withSuffix(new InitializerBlockEnd())
                    .to(writer);
        }
    }
}