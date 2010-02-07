package springclean.core.domain;

import static com.google.common.collect.Lists.newArrayList;
import org.daisychain.source.AClass;
import static org.daisychain.source.ExistingClass.existingClass;
import springclean.core.generate.ConstructionStrategy;
import static springclean.core.generate.ConstructionStrategyBuilder.aConstructionStrategy;

import java.util.List;

public class BeanCollectionBuilder<T extends BeanCollectionBuilder> {
    AClass aClass = existingClass(String.class);
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
