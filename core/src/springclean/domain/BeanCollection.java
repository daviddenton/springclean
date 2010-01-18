package springclean.domain;

import org.daisychain.source.AClass;
import org.daisychain.util.Functor;

public interface BeanCollection extends Value {
    AClass clazz();
    <E extends Throwable> void forAllMembers(Functor<SpringManagedObject, E> functor) throws E;

}