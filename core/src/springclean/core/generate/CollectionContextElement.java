package springclean.core.generate;

import static com.google.common.collect.Sets.newHashSet;
import org.daisychain.source.AClass;
import org.daisychain.source.Instance;
import org.daisychain.source.body.AssignableStatement;
import springclean.core.domain.BeanCollection;

import java.util.Set;


public class CollectionContextElement implements ConstructionStrategy {
    private final AClass castTo;
    private final BeanCollection beanCollection;

    public CollectionContextElement(BeanCollection beanCollection, AClass castTo) {
        this.beanCollection = beanCollection;
        this.castTo = castTo;
    }

    public Set<Instance> dependencies() {
        return newHashSet();
    }

    public AssignableStatement asStatement() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
