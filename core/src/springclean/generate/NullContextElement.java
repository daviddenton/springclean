package springclean.generate;

import org.apache.commons.lang.builder.ToStringBuilder;
import static org.apache.commons.lang.builder.ToStringStyle.SHORT_PREFIX_STYLE;
import org.daisychain.source.Instance;
import org.daisychain.source.body.AssignableStatement;

import java.util.Collections;
import java.util.Set;


public class NullContextElement implements ContextElement {

    public Set<Instance> dependencies() {
        return Collections.EMPTY_SET;
    }

    public AssignableStatement asStatement() {
        return null;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, SHORT_PREFIX_STYLE);
    }
}
