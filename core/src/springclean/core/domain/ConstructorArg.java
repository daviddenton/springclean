package springclean.core.domain;

import static com.google.common.collect.Lists.newArrayList;
import org.daisychain.source.Method;
import org.daisychain.source.body.AssignableStatement;
import springclean.core.generate.ConstructionStrategy;

import java.util.List;

public interface ConstructorArg extends InjectedDependency {

    public static class Util {
        public static List<ConstructionStrategy> constructorInjectedDependencies(final Bean bean) {
            final List<ConstructionStrategy> injectedDependencies = newArrayList();
            for (int i = 0; i < bean.constructorArgs().size(); i++) {
                injectedDependencies.add(bean.constructorArgs().get(i).referencedObject().asConstructionStrategy(bean.constructor().parameters().get(i).instance.aClass));
            }
            return injectedDependencies;
        }

        public static List<AssignableStatement> asStatements(Method method, List<ConstructorArg> constructorArgs) {
            final List<AssignableStatement> constructorStatements = newArrayList();
            for (int i = 0; i < constructorArgs.size(); i++) {
                constructorStatements.add(constructorArgs.get(i).referencedObject().asConstructionStrategy(method.parameters().get(i).instance.aClass).asStatement());
            }
            return constructorStatements;
        }

    }
}
