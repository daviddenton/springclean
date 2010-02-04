package springclean.core.xml;

import static com.google.common.collect.Lists.newArrayList;
import springclean.core.domain.ConstructorArg;

import java.util.List;

public class ConstructorArgs {
    private final List<ConstructorArg> localConstructorArgs;

    public ConstructorArgs(List<ConstructorArg> localConstructorArgs) {
        this.localConstructorArgs = localConstructorArgs;
    }

    public ConstructorArgs(ConstructorArg... localConstructorArgs) {
        this(newArrayList(localConstructorArgs));
    }

    public ConstructorArgs mergeIn(ConstructorArgs inheritedConstructorArgs) {
        return this;
    }

    public List<ConstructorArg> constructorArgs() {
        return localConstructorArgs;
    }
}
