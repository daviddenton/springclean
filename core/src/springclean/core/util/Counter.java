package springclean.core.util;

import org.daisychain.util.SimpleFunctor;

public class Counter<T> implements SimpleFunctor<T> {
    private int count;

    public void execute(T target) {
        count = count() + 1;
    }

    public int count() {
        return count;
    }
}
