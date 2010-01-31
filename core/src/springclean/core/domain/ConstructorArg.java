package springclean.core.domain;

import static com.google.common.collect.Lists.newArrayList;
import springclean.core.generate.ContextElement;

import java.util.List;

public interface ConstructorArg extends InjectedDependency {

    public static class Util {
        public static List<ContextElement> constructorInjectedDependencies(final Bean bean) {
            final List<ContextElement> injectedDependencies = newArrayList();
            for (int i = 0; i < bean.constructorArgs().size(); i++) {
                injectedDependencies.add(bean.constructorArgs().get(i).referencedObject().asContextElement(bean.constructor().parameters().get(i).instance.aClass));
            }
            return injectedDependencies;
        }
    }
}
