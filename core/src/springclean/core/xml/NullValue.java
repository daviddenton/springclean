package springclean.core.xml;

import org.daisychain.source.AClass;
import springclean.core.domain.Value;
import springclean.core.generate.NullContextElement;

public class NullValue implements Value {
    public NullContextElement asContextElement(AClass aClass) {
        return new NullContextElement();
    }

}
