package springclean.core.generate;

import static com.google.common.collect.Sets.newHashSet;
import org.daisychain.source.Instance;
import org.daisychain.source.body.AssignableStatement;
import springclean.core.domain.BeanCollection;

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
        return new CollectionConstruction(beanCollection);
    }
}
