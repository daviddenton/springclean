package springclean.core.domain;

import microtypes.StringValue;
import org.apache.commons.lang.StringUtils;

public class PropertyName extends StringValue {
    private PropertyName(String value) {
        super(value);
    }

    public static PropertyName propertyName(String name) {
        return new PropertyName(name);
    }

    public String setterName() {
        return "set" + StringUtils.capitalize(value);
    }

    public int compareTo(PropertyName propertyName) {
        return value.compareTo(propertyName.value);
    }
}