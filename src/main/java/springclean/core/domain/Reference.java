package springclean.core.domain;

import org.daisychain.source.AClass;
import org.daisychain.source.ExistingMethod;

public interface Reference extends Identified, SpringManagedObject {
    AClass<ExistingMethod> clazz();
}
