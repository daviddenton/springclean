package springclean.core.domain;

import java.util.List;
import java.util.Map;

public interface BeanMap extends Value {
    List<Map.Entry<SpringManagedObject, SpringManagedObject>> entries();
}
