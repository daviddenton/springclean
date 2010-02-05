package springclean.core.domain;

import com.sun.xml.internal.ws.util.StringUtils;
import microtypes.StringValue;

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