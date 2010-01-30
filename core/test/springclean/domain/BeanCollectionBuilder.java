package springclean.domain;

import static com.google.common.collect.Lists.newArrayList;
import org.daisychain.source.AClass;
import org.daisychain.source.ExistingClass;
import static springclean.core.generate.ContextElementBuilder.aContextElement;
import springclean.generate.ContextElement;

import java.util.List;

public class BeanCollectionBuilder<T extends BeanCollectionBuilder> {
    AClass aClass = new ExistingClass(String.class);
    List<SpringManagedObject> members = newArrayList();
    ContextElement contextElement = aContextElement().build();

    public T withClass(AClass aClass) {
        this.aClass = aClass;
        return (T) this;
    }

    public T withMember(SpringManagedObject member) {
        this.members.add(member);
        return (T) this;
    }

    public T whichProducesContextElement(ContextElement contextElement) {
        this.contextElement = contextElement;
        return (T) this;
    }
}
