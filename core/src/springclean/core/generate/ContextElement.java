package springclean.core.generate;

import org.daisychain.source.Instance;

import java.util.HashSet;
import java.util.Set;


public interface ContextElement extends ConstructionStrategy {

    public static class DependencyExtractor {
        private DependencyExtractor() {
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
