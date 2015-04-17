package springclean.core.util;

public abstract class IndexingFunctor<T> implements Functor<T, RuntimeException> {
    private int index;

    public final void execute(T target) {
        execute(target, index++);
    }

    protected abstract void execute(T target, int index);

    public int count() {
        return index;
    }
}
