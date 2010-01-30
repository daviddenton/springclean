package springclean.core.generate;

import static com.google.common.collect.Sets.newHashSet;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.daisychain.source.AClass;
import org.daisychain.source.Instance;
import org.daisychain.source.body.AssignableStatement;
import org.daisychain.util.SimpleFunctor;
import springclean.core.domain.BeanCollection;
import springclean.core.domain.SpringManagedObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class CollectionContextElement implements ContextElement {
    public final List<SpringManagedObject> members = new ArrayList<SpringManagedObject>();
    private final AClass collectionType;
    private final AClass castTo;

    public CollectionContextElement(BeanCollection beanCollection, AClass castTo) {
        beanCollection.forAllMembers(new SimpleFunctor<SpringManagedObject>() {
            public void execute(SpringManagedObject target) {
                members.add(target);
            }
        });
        this.collectionType = beanCollection.clazz();
        this.castTo = castTo;
    }

    public Set<Instance> dependencies() {
        return newHashSet();
    }

    public AssignableStatement asStatement() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
