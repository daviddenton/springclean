package springclean.core.util;

public interface SimpleFunctor<T> extends Functor<T, RuntimeException> {
    void execute(T target);
}
