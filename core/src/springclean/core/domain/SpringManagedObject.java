package springclean.core.domain;

import org.daisychain.source.AClass;
import springclean.core.generate.ConstructionStrategy;

public interface SpringManagedObject {
    ConstructionStrategy asConstructionStrategy(AClass aClass);
}
