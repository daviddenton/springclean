package springclean.core.xml;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import springclean.core.domain.ConstructorArg;

import java.util.List;
import java.util.Set;

public class ConstructorArgs {
    private final List<ConstructorArg> localConstructorArgs;

    public ConstructorArgs(List<ConstructorArg> localConstructorArgs) throws IllegalConstructorArgs {
        this.localConstructorArgs = localConstructorArgs;
        verifyIndexedIntegity(localConstructorArgs);
        verifyIndexDuplicates(localConstructorArgs);
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

    private void verifyIndexedIntegity(List<ConstructorArg> constructorArgs) throws IllegalConstructorArgs {
        int indexedCount = 0;
        for (ConstructorArg constructorArg : constructorArgs) {
            if (constructorArg.isIndexed()) indexedCount++;
        }
        if (indexedCount != 0 && indexedCount != constructorArgs.size()) {
            throw new IllegalConstructorArgs();
        }
    }

    private void verifyIndexDuplicates(List<ConstructorArg> constructorArgs) throws IllegalConstructorArgs {
        Set<Integer> indexes = indexesFrom(constructorArgs);
        if (!indexes.isEmpty() && indexes.size() != constructorArgs.size()) {
            throw new IllegalConstructorArgs();
        }
    }

    private Set<Integer> indexesFrom(List<ConstructorArg> constructorArgs) {
        Set<Integer> indexes = newHashSet();
        for (ConstructorArg constructorArg : constructorArgs) {
            if (constructorArg.isIndexed()) indexes.add(constructorArg.index());
        }
        return indexes;
    }

    public class IllegalConstructorArgs extends Exception {
    }
}
