package springclean.core.xml;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;
import springclean.core.domain.ConstructorArg;

import java.util.List;

import static org.junit.Assert.assertThat;
import static springclean.core.domain.ConstructorArgBuilder.aConstructorArg;

public class ConstructorArgsTest {

    ConstructorArg unindexedArg = aConstructorArg().build();

    @Test
    public void mergingWithAllIndexedInSub() throws Exception {
        ConstructorArg arg0 = arg(0);
        assertThat(argsWith().mergeIn(argsWith(arg0)).constructorArgs(), matchesIndexed(arg0));
    }

    @Test
    public void mergingWithAllIndexedInSuper() throws Exception {
        ConstructorArg arg0 = arg(0);
        assertThat(argsWith(arg0).mergeIn(argsWith()).constructorArgs(), matchesIndexed(arg0));
    }

    @Test
    public void mergingWithAllNonIndexedInSub() throws Exception {
        assertThat(argsWith().mergeIn(argsWith(unindexedArg)).constructorArgs(), matchesUnindexed(unindexedArg));
    }

    @Test
    public void mergingWithAllNonIndexedInSuper() throws Exception {
        assertThat(argsWith(unindexedArg).mergeIn(argsWith()).constructorArgs(), matchesUnindexed(unindexedArg));
    }

    @Test
    public void mergingWithoutClash() throws Exception {
        ConstructorArg arg0 = arg(0);
        ConstructorArg arg1 = arg(1);
        assertThat(argsWith(arg0).mergeIn(argsWith(arg1)).constructorArgs(), matchesIndexed(arg0, arg1));
    }

    @Test
    public void mergingWithIndexClash() throws Exception {
        ConstructorArg subclassArg0 = arg(0);
        ConstructorArg superArg0 = arg(0);
        assertThat(argsWith(subclassArg0).mergeIn(argsWith(superArg0)).constructorArgs(), matchesIndexed(subclassArg0));
    }

    @Test(expected = ConstructorArgs.IllegalConstructorArgs.class)
    public void noMixingOfIndexedAndNonIndexedArgs_construction() throws Exception {
        argsWith(unindexedArg, arg(1));
    }

    @Test(expected = ConstructorArgs.IllegalConstructorArgs.class)
    public void duplicateArgIndexes_construction() throws Exception {
        argsWith(arg(0), arg(0));
    }

    @Test(expected = ConstructorArgs.IllegalConstructorArgs.class)
    public void noMixingOfIndexedAndNonIndexedArgs_inMerge() throws Exception {
        argsWith(unindexedArg).mergeIn(argsWith(arg(1)));
    }

    @Test(expected = ConstructorArgs.IllegalConstructorArgs.class)
    public void noMixingOfNonIndexedAndIndexedArgs_inMerge() throws Exception {
        argsWith(arg(0)).mergeIn(argsWith(unindexedArg));
    }

    @Test(expected = ConstructorArgs.IllegalConstructorArgs.class)
    public void argsNotConsecutiveIndexed_inMerge() throws Exception {
        argsWith(arg(0)).mergeIn(argsWith(arg(2)));
    }

    private ConstructorArg arg(int index) {
        return aConstructorArg().withIndex(index).build();
    }

    private ConstructorArgs argsWith(ConstructorArg... args) throws ConstructorArgs.IllegalConstructorArgs {
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

    private Matcher<List<ConstructorArg>> matchesUnindexed(final ConstructorArg... expected) {
        return new TypeSafeMatcher<List<ConstructorArg>>() {
            public boolean matchesSafely(List<ConstructorArg> actual) {
                return expected.length == actual.size();
            }

            public void describeTo(Description description) {
            }
        };
    }


}