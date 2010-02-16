package springclean.core.domain;

public interface BeanMapEntry extends SpringManagedObject {
    SpringManagedObject key();

    SpringManagedObject value();
}