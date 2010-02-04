package springclean.core.xml;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;
import springclean.core.domain.ConstructorArg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        List<ConstructorArg> inheritedArgs = inheritedConstructorArgs.constructorArgs();
        List<ConstructorArg> entireList = new ArrayList<ConstructorArg>(localConstructorArgs);
        entireList.addAll(inheritedArgs);
        verifyIndexedIntegity(entireList);

        Map<Integer, ConstructorArg> mapped = newHashMap();
        for (ConstructorArg inheritedConstructorArg : inheritedArgs) {
            mapped.put(inheritedConstructorArg.index(), inheritedConstructorArg);
        }
        for (ConstructorArg localConstructorArg : localConstructorArgs) {
            mapped.put(localConstructorArg.index(), localConstructorArg);
        }
        List<ConstructorArg> mergedList = newArrayList();
        for (Integer index : mapped.keySet()) {
            mergedList.add(mapped.get(index));
        }
        verifyIndexContinuity(mergedList);
        return new ConstructorArgs(mergedList);
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
            throw new IllegalConstructorArgs("Inconsistent indexing in constructor args");
        }
    }

    private void verifyIndexContinuity(List<ConstructorArg> constructorArgs) throws IllegalConstructorArgs {
        Set<Integer> indexes = indexesFrom(constructorArgs);
        int runningIndex = 0;
        for (Integer index : indexes) {
            if (index != runningIndex)
                throw new IllegalConstructorArgs("Expected index " + runningIndex + " but got " + index);
            runningIndex++;
        }
    }

    private void verifyIndexDuplicates(List<ConstructorArg> constructorArgs) throws IllegalConstructorArgs {
        Set<Integer> indexes = indexesFrom(constructorArgs);
        if (!indexes.isEmpty() && indexes.size() != constructorArgs.size()) {
            throw new IllegalConstructorArgs("Duplicate index encountered");
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
        public IllegalConstructorArgs(String message) {
            super(message);
        }
    }
}
