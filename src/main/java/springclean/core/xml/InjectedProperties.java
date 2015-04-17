package springclean.core.xml;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import springclean.core.domain.Property;
import springclean.core.domain.PropertyName;

import java.util.*;

public class InjectedProperties {
    private final Collection<Property> localProperties;

    public InjectedProperties(Collection<Property> localProperties) {
        this.localProperties = localProperties;
    }

    public InjectedProperties(Property... localProperties) {
        this(newArrayList(localProperties));
    }

    public InjectedProperties mergeIn(InjectedProperties inheritedInjectedProperties) {
        Map<PropertyName, Property> merged = newHashMap();
        for (Property property : inheritedInjectedProperties.properties()) {
            merged.put(property.name(), property);
        }
        for (Property property : localProperties) {
            merged.put(property.name(), property);
        }
        List<Property> output = newArrayList(merged.values());
        Collections.sort(output, new Comparator<Property>() {
            public int compare(Property property, Property property1) {
                return property.name().compareTo(property1.name());
            }
        });
        return new InjectedProperties(output);
    }

    public List<Property> properties() {
        return newArrayList(localProperties);
    }
}