package springclean.generate;

import static com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.lang.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang.builder.ToStringStyle.SHORT_PREFIX_STYLE;
import org.daisychain.source.Instance;
import org.daisychain.source.Method;
import org.daisychain.source.body.AssignableStatement;
import org.daisychain.util.IndexingFunctor;
import org.daisychain.util.SimpleFunctor;
import springclean.domain.Bean;
import springclean.domain.ConstructorArg;
import springclean.domain.Property;
import springclean.domain.IdentifiedBean;
import static springclean.generate.ContextElement.DependencyExtractor.allDependenciesOf;

import java.util.List;
import java.util.Set;
import static java.util.Arrays.asList;

public class BeanContextElement implements ContextElement {

    protected final Bean bean;
    private final ConstructionStrategy constructionStrategy;

    public BeanContextElement(Bean bean, ConstructionStrategy constructionStrategy) {
        this.bean = bean;
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
