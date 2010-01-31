package springclean.core.xml;

import org.daisychain.util.IndexingFunctor;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import springclean.core.AbstractSpringCleanTestCase;
import static springclean.core.domain.ApplicationContextBuilder.anApplicationContext;

import java.util.ArrayList;

public class XmlBeanListTest extends AbstractSpringCleanTestCase {
    private XmlBeanList xmlBeanList = new XmlBeanList(rl.loadDocument("XmlBeanListTest.xml").getRootElement(), anApplicationContext().build());

    @Test
    public void clazz() {
        assertThat(xmlBeanList.clazz().name(), equalTo(ArrayList.class.getName()));
    }

    @Test
    public void forAllMembers() throws Throwable {
        final IndexingFunctor functor = new IndexingFunctor() {
            protected void execute(Object target, int index) {
                assertNotNull(target);
            }
        };
        xmlBeanList.forAllMembers(functor);
        assertThat(functor.count(), equalTo(1));
    }
}
