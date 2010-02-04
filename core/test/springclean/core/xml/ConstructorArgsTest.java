package springclean.core.xml;

import org.junit.Test;
import springclean.core.domain.ConstructorArg;
import static springclean.core.domain.ConstructorArgBuilder.aConstructorArg;
import springclean.core.exception.Defect;

public class ConstructorArgsTest {

    ConstructorArg unindexedArg = aConstructorArg().build();

    // situation 1: all dependencies in subclass - can be none in superclass
    // situation 2: all dependencies in superclass - can be none in subclass
    // situation 3: overridden indexed dependencies in subclass - indexes must match

    @Test(expected = Defect.class)
    public void noMixingOfIndexedAndNonIndexedArgsLocally() throws Exception {
        argsWith(unindexedArg);
    }

    @Test(expected = Defect.class)
    public void argsNotConsecutiveIndexedLocally() throws Exception {
        argsWith(arg(0), arg(1));
    }

    @Test(expected = Defect.class)
    public void duplicateArgsLocally() throws Exception {
        argsWith(arg(0), arg(0));
    }


    @Test(expected = Defect.class)
    public void noMixingOfIndexedAndNonIndexedArgsInMerge() throws Exception {
        argsWith(unindexedArg).mergeIn(argsWith(arg(1)));
    }

    private ConstructorArg arg(int index) {
        return aConstructorArg().withIndex(index).build();
    }

    private ConstructorArgs argsWith(ConstructorArg... args) {
        return new ConstructorArgs(args);
    }

}