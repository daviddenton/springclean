package springclean.core.generate;

import org.daisychain.source.AClass;
import org.daisychain.source.Instance;
import org.daisychain.source.body.AssignableStatement;
import org.daisychain.source.util.IndentingStringWriter;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;


public class NullValue implements ConstructionStrategy {

    public Set<Instance> dependencies() {
        return Collections.EMPTY_SET;
    }

    public AssignableStatement asStatement() {
        return new AssignableStatement() {
            public Set<AClass> getImports() {
                return Collections.EMPTY_SET;
            }

            public void appendSource(IndentingStringWriter writer) throws IOException {
                writer.append("null");
            }
        };
    }
}
