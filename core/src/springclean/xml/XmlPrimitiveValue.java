package springclean.xml;

import org.daisychain.source.AClass;
import springclean.domain.Value;
import springclean.domain.ApplicationContext;
import springclean.generate.PrimitiveContextElement;

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
