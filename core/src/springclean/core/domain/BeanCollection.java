package springclean.core.domain;

import org.daisychain.source.AClass;

import java.util.List;

public interface BeanCollection extends Value {
    AClass clazz();

    List<SpringManagedObject> members();
}