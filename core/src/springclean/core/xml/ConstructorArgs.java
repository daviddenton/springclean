package springclean.core.xml;

import springclean.core.domain.ConstructorArg;

import java.util.List;

public class ConstructorArgs {
    private final List<ConstructorArg> localConstructorArgs;

    public ConstructorArgs(List<ConstructorArg> localConstructorArgs) {
        this.localConstructorArgs = localConstructorArgs;
    }

    // situation 1: all dependencies in subclass - can be none in superclass
    // situation 2: all dependencies in superclass - can be none in subclass
    // situation 3: overridden indexed dependencies in subclass - indexes must match
    public ConstructorArgs mergeIn(ConstructorArgs inheritedConstructorArgs) {
        return this;
    }

    public List<ConstructorArg> constructorArgs() {
        return localConstructorArgs;
    }
}
