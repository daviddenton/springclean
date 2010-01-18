package springclean.domain;

import org.daisychain.source.AClass;
import org.daisychain.util.Functor;
import springclean.generate.ContextElement;

public class BeanSetBuilder extends BeanCollectionBuilder<BeanSetBuilder> {

    private BeanSetBuilder() {}

    public static BeanSetBuilder aBeanSet() {
        return new BeanSetBuilder();
    }

    public BeanSet build() {
        return new BeanSet() {
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