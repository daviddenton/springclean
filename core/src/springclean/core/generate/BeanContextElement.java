package springclean.core.generate;

import static org.apache.commons.lang.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang.builder.ToStringStyle.SHORT_PREFIX_STYLE;
import org.daisychain.source.Instance;
import org.daisychain.source.body.AssignableStatement;
import static springclean.core.generate.ContextElement.DependencyExtractor.allDependenciesOf;

import static java.util.Arrays.asList;
import java.util.Set;

public class BeanContextElement implements ContextElement {

    private final ConstructionStrategy constructionStrategy;

    public BeanContextElement(ConstructionStrategy constructionStrategy) {
        this.constructionStrategy = constructionStrategy;
    }

    public Set<Instance> dependencies() {
        return allDependenciesOf(asList(constructionStrategy));
    }

    @Override
    public String toString() {
        return reflectionToString(this, SHORT_PREFIX_STYLE);
    }

    public AssignableStatement asStatement() {
        return constructionStrategy.asStatement();
    }

}
