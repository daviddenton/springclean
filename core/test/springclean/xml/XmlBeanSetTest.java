package springclean.xml;

import org.daisychain.util.IndexingFunctor;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import springclean.core.AbstractSpringCleanTestCase;
import static springclean.domain.ApplicationContextBuilder.anApplicationContext;

import java.util.HashSet;

public class XmlBeanSetTest extends AbstractSpringCleanTestCase {
    private XmlBeanSet xmlBeanSet = new XmlBeanSet(rl.loadDocument("XmlBeanSetTest.xml").getRootElement(), anApplicationContext().build());

    @Test
    public void clazz() {
        assertThat(xmlBeanSet.clazz().name(), equalTo(HashSet.class.getName()));
    }

    @Test
    public void forAllMembers() throws Throwable {
        final IndexingFunctor functor = new IndexingFunctor() {
            protected void execute(Object target, int index) {
                assertNotNull(target);
            }
        };
        xmlBeanSet.forAllMembers(functor);
        assertThat(functor.count(), equalTo(1));
    }
}