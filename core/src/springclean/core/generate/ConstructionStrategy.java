package springclean.core.generate;

import org.daisychain.source.Instance;
import org.daisychain.source.body.AssignableStatement;

import java.util.Set;

public interface ConstructionStrategy {
    AssignableStatement asStatement();

    Set<Instance> dependencies();
}
