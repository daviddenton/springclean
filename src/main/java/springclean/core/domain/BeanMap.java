package springclean.core.domain;

import java.util.List;

public interface BeanMap extends Value {
    List<BeanMapEntry> entries();
}
