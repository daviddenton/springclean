package springclean.core.util;

import com.google.common.base.Function;

public class Counter<T> implements Function<T, Object> {
    private int count;

    public Object apply(T target) {
        count = count() + 1;
        return null;
    }

    public int count() {
        return count;
    }
}
