package springclean.core.domain;

import org.daisychain.source.AClass;
import springclean.core.generate.ContextElement;

public interface SpringManagedObject {
    ContextElement asContextElement(AClass aClass);
}
