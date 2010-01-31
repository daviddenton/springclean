package springclean.core.domain;

import org.daisychain.source.AClass;
import org.daisychain.util.Functor;
import springclean.core.generate.ConstructionStrategy;

public class BeanSetBuilder extends BeanCollectionBuilder<BeanSetBuilder> {

    private BeanSetBuilder() {
    }

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

            public ConstructionStrategy asConstructionStrategy(AClass aClass) {
                return constructionStrategy;
            }
        };
    }
}