package springclean.xml;

import org.daisychain.source.AClass;
import springclean.domain.Value;
import springclean.generate.NullContextElement;

public class NullValue implements Value {
    public NullContextElement asContextElement(AClass aClass) {
        return new NullContextElement();
    }

}
