package springclean.core.domain;

import org.daisychain.source.AClass;
import org.daisychain.source.ExistingMethod;
import org.daisychain.source.Method;

import java.util.List;

public interface Bean extends SpringManagedObject {
    AClass<ExistingMethod> declaredBeanClass();

    AClass<ExistingMethod> constructedBeanClass();

    List<Property> setterDependencies();

    List<ConstructorArg> constructorArgs();

    Method constructor();

    boolean hasInitMethod();

    boolean hasDestroyMethod();

    Method initMethod();

    Method destroyMethod();

    Method factoryMethod();

    Method setter(Property property);

    boolean isAbstract();
}
