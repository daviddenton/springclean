package springclean.domain;

import static com.google.common.collect.Lists.newArrayList;
import org.daisychain.source.AClass;
import org.daisychain.source.ExistingClass;
import springclean.core.domain.SpringManagedObject;
import springclean.core.generate.ConstructionStrategy;
import static springclean.core.generate.ConstructionStrategyBuilder.aConstructionStrategy;

import java.util.List;

public class BeanCollectionBuilder<T extends BeanCollectionBuilder> {
    AClass aClass = new ExistingClass(String.class);
    List<SpringManagedObject> members = newArrayList();
    ConstructionStrategy constructionStrategy = aConstructionStrategy().build();

    public T withClass(AClass aClass) {
        this.aClass = aClass;
        return (T) this;
    }

    public T withMember(SpringManagedObject member) {
        this.members.add(member);
        return (T) this;
    }

    public T whichProducesConstructionStrategy(ConstructionStrategy constructionStrategy) {
        this.constructionStrategy = constructionStrategy;
        return (T) this;
    }
}
