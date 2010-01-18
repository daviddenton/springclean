package springclean.domain;

import org.daisychain.source.AClass;
import springclean.generate.ContextElement;

public interface SpringManagedObject {
    ContextElement asContextElement(AClass aClass);
}
