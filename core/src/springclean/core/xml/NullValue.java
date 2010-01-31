package springclean.core.xml;

import org.daisychain.source.AClass;
import springclean.core.domain.Value;
import springclean.core.generate.ConstructionStrategy;

public class NullValue implements Value {
    public ConstructionStrategy asConstructionStrategy(AClass aClass) {
        return new springclean.core.generate.NullValue();
    }

}
