package springclean.core.xml;

import org.daisychain.source.AClass;
import springclean.core.domain.Value;
import springclean.core.generate.ConstructionStrategy;
import springclean.core.generate.PrimitiveConstructionStrategy;
import springclean.core.generate.PropertyPlaceholderConstructionStrategy;
import static springclean.core.generate.PropertyPlaceholderConstructionStrategy.PROPERTY_PLACEHOLDER_PATTERN;

public class XmlPrimitiveValue implements Value {

    private final String rawValue;

    public XmlPrimitiveValue(String rawValue) {
        this.rawValue = rawValue;
    }

    public ConstructionStrategy asConstructionStrategy(AClass aClass) {
        return PROPERTY_PLACEHOLDER_PATTERN.matcher(rawValue).matches() ?
                new PropertyPlaceholderConstructionStrategy(rawValue) :
                new PrimitiveConstructionStrategy(rawValue, aClass);
    }
}
