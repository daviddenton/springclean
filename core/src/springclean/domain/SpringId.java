package springclean.domain;

import microtypes.StringValue;

public class SpringId extends StringValue {
    private SpringId(String value) {
        super(value);
    }

    public static SpringId springId(String name) {
        return new SpringId(name);
    }
}

