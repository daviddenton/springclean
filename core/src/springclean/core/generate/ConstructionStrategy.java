package springclean.core.generate;

import org.daisychain.source.Instance;
import org.daisychain.source.body.AssignableStatement;

import java.util.HashSet;
import java.util.Set;

public interface ConstructionStrategy {
    AssignableStatement asStatement();

    Set<Instance> dependencies();

    static class Util {
        private Util() {
        }

        public static Set<Instance> allDependenciesOf(Iterable<? extends ConstructionStrategy>... from) {
            Set<Instance> dependencies = new HashSet<Instance>();
            for (Iterable<? extends ConstructionStrategy> iterable : from) {
                for (ConstructionStrategy constructionStrategy : iterable) {
                    dependencies.addAll(constructionStrategy.dependencies());
                }
            }
            return dependencies;
        }
    }
}
