package springclean.domain;

import org.daisychain.source.AClass;
import org.daisychain.util.Functor;
import springclean.core.domain.BeanList;
import springclean.core.domain.SpringManagedObject;
import springclean.core.generate.ContextElement;

public class BeanListBuilder extends BeanCollectionBuilder<BeanListBuilder> {

    private BeanListBuilder() {
    }

    public static BeanListBuilder aBeanList() {
        return new BeanListBuilder();
    }

    public BeanList build() {
        return new BeanList() {
            public AClass clazz() {
                return aClass;
            }

            public <E extends Throwable> void forAllMembers(Functor<SpringManagedObject, E> functor) throws E {
                for (SpringManagedObject member : members) {
                    functor.execute(member);
                }
            }

            public ContextElement asContextElement(AClass aClass) {
                return contextElement;
            }
        };
    }
}