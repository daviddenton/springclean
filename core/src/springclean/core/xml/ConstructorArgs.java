package springclean.core.xml;

import static com.google.common.collect.Lists.newArrayList;
import springclean.core.domain.ConstructorArg;

import java.util.List;

public class ConstructorArgs {
    private final List<ConstructorArg> localConstructorArgs;

    public ConstructorArgs(List<ConstructorArg> localConstructorArgs) throws IllegalConstructorArgs {
        this.localConstructorArgs = localConstructorArgs;
        verify(localConstructorArgs);
    }

    public ConstructorArgs(ConstructorArg... localConstructorArgs) throws IllegalConstructorArgs {
        this(newArrayList(localConstructorArgs));
    }

    public ConstructorArgs mergeIn(ConstructorArgs inheritedConstructorArgs) throws IllegalConstructorArgs {
        return this;
    }

    public List<ConstructorArg> constructorArgs() {
        return localConstructorArgs;
    }

    private void verify(List<ConstructorArg> constructorArgs) throws IllegalConstructorArgs {
        verifyIndexes(constructorArgs);
    }

    private void verifyIndexes(List<ConstructorArg> constructorArgs) throws IllegalConstructorArgs {
        int indexedCount = 0;
        for (ConstructorArg constructorArg : constructorArgs) {
            if (constructorArg.isIndexed()) indexedCount++;
        }
        if (indexedCount != 0 && indexedCount != constructorArgs.size()) {
            throw new IllegalConstructorArgs();
        }
    }

    public class IllegalConstructorArgs extends Exception {
    }

    ;
}
