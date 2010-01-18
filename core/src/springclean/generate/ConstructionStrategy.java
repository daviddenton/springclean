package springclean.generate;

import org.daisychain.source.body.AssignableStatement;
import org.daisychain.source.Instance;

import java.util.Set;

public interface ConstructionStrategy {
    AssignableStatement asStatement();
    Set<Instance> dependencies();
}
