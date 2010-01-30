package springclean.core.generate;

import static org.apache.commons.lang.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang.builder.ToStringStyle.SHORT_PREFIX_STYLE;
import org.daisychain.source.Instance;
import org.daisychain.source.body.AssignableStatement;
import springclean.core.domain.Bean;

import static java.util.Arrays.asList;
import java.util.Set;

public class BeanContextElement implements ContextElement {

    protected final Bean bean;
    private final ConstructionStrategy constructionStrategy;

    public BeanContextElement(Bean bean, ConstructionStrategy constructionStrategy) {
        this.bean = bean;
        this.constructionStrategy = constructionStrategy;
    }

    public Set<Instance> dependencies() {
        return DependencyExtractor.allDependenciesOf(asList(constructionStrategy));
    }

    @Override
    public String toString() {
        return reflectionToString(this, SHORT_PREFIX_STYLE);
    }

    public AssignableStatement asStatement() {
        return constructionStrategy.asStatement();
    }

}
