package springclean.core.generate;

import org.apache.commons.lang.builder.ToStringBuilder;
import static org.apache.commons.lang.builder.ToStringStyle.SHORT_PREFIX_STYLE;
import org.daisychain.source.AClass;
import org.daisychain.source.Instance;
import org.daisychain.source.body.AssignableStatement;
import springclean.core.domain.Reference;

import static java.util.Collections.singleton;
import java.util.Set;


public class RefConstructionStrategy implements ConstructionStrategy {
    private final Instance instance;

    public RefConstructionStrategy(Reference reference, AClass clazz) {
        instance = new Instance(reference.id().value, clazz);
    }

    public Set<Instance> dependencies() {
        return singleton(instance);
    }

    public AssignableStatement asStatement() {
        return instance.reference;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, SHORT_PREFIX_STYLE);
    }

}
