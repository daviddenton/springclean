package springclean.core.xml;

import org.daisychain.source.AClass;
import springclean.core.domain.ApplicationContext;
import springclean.core.domain.Value;
import springclean.core.generate.PrimitiveContextElement;

public class XmlPrimitiveValue implements Value {

    private final String rawValue;
    private final ApplicationContext applicationContext;

    public XmlPrimitiveValue(String rawValue, ApplicationContext applicationContext) {
        this.rawValue = rawValue;
        this.applicationContext = applicationContext;
    }

    public PrimitiveContextElement asContextElement(AClass aClass) {
        return new PrimitiveContextElement(rawValue, aClass);
    }


}
