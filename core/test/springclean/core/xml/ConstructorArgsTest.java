package springclean.core.xml;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.matchers.TypeSafeMatcher;
import springclean.core.domain.ConstructorArg;
import static springclean.core.domain.ConstructorArgBuilder.aConstructorArg;
import springclean.core.exception.Defect;

import java.util.List;

public class ConstructorArgsTest {

    ConstructorArg unindexedArg = aConstructorArg().build();

    // situation 1: all dependencies in subclass - can be none in superclass
    // situation 2: all dependencies in superclass - can be none in subclass
    // situation 3: overridden indexed dependencies in subclass - indexes must match

    @Test(expected = Defect.class)
    public void mergingWithoutClash() throws Exception {
        ConstructorArg arg0 = arg(0);
        ConstructorArg arg1 = arg(1);
        assertThat(argsWith(arg0).mergeIn(argsWith(arg1)).constructorArgs(), matchesIndexed(arg0, arg1));
    }

    @Test(expected = Defect.class)
    public void noMixingOfIndexedAndNonIndexedArgs_construction() throws Exception {
        argsWith(unindexedArg);
    }

    @Test(expected = Defect.class)
    public void argsNotConsecutiveIndexed_construction() throws Exception {
        argsWith(arg(0), arg(1));
    }

    @Test(expected = Defect.class)
    public void duplicateArgIndexes_construction() throws Exception {
        argsWith(arg(0), arg(0));
    }

    @Test(expected = Defect.class)
    public void noMixingOfIndexedAndNonIndexedArgs_inMerge() throws Exception {
        argsWith(unindexedArg).mergeIn(argsWith(arg(1)));
    }

    @Test(expected = Defect.class)
    public void noMixingOfNonIndexedAndIndexedArgs_inMerge() throws Exception {
        argsWith(arg(0)).mergeIn(argsWith(unindexedArg));
    }

    @Test(expected = Defect.class)
    public void argsNotConsecutiveIndexed_inMerge() throws Exception {
        argsWith(arg(0)).mergeIn(argsWith(arg(1)));
    }

    @Test(expected = Defect.class)
    public void duplicateArgIndexes_inMerge() throws Exception {
        argsWith(arg(0)).mergeIn(argsWith(arg(0)));
    }

    private ConstructorArg arg(int index) {
        return aConstructorArg().withIndex(index).build();
    }

    private ConstructorArgs argsWith(ConstructorArg... args) {
        return new ConstructorArgs(args);
    }

    private Matcher<List<ConstructorArg>> matchesIndexed(final ConstructorArg... expected) {
        return new TypeSafeMatcher<List<ConstructorArg>>() {
            public boolean matchesSafely(List<ConstructorArg> actual) {
                if (expected.length != actual.size()) return false;
                for (int i = 0; i < expected.length; i++) {
                    if (expected[i].index() != actual.get(i).index()) return false;
                }
                return true;
            }

            public void describeTo(Description description) {
            }
        };
    }


}