package springclean.core.domain;

import org.daisychain.source.AClass;
import org.daisychain.source.ExistingMethod;
import org.daisychain.source.Method;
import org.daisychain.source.body.AssignableStatement;

import java.util.List;

public interface Bean extends SpringManagedObject {
    AClass<ExistingMethod> clazz();

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

    static class Util {
        public static List<AssignableStatement> asStatements(Bean bean) {
            final List<AssignableStatement> constructorStatements = com.google.common.collect.Lists.newArrayList();
            for (int i = 0; i < bean.constructorArgs().size(); i++) {
                constructorStatements.add(bean.constructorArgs().get(i).referencedObject().asContextElement(bean.constructor().parameters().get(i).instance.aClass).asStatement());
            }
            return constructorStatements;
        }
    }
}
