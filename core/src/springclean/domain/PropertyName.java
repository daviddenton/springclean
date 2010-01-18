package springclean.domain;

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
}