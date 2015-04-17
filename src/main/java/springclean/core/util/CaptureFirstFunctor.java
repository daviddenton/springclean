package springclean.core.util;

public abstract class CaptureFirstFunctor<T> implements Functor<T, RuntimeException> {
    private T match;

    public final void execute(T target) {
        if(match != null && accept(target)) {
            match = target;
        }
    }

    protected abstract boolean accept(T target);

    public boolean matched() {
        return match != null;
    }

    public T match() {
        return match;
    }
}
