package springclean.core.xml;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import springclean.core.AbstractSpringCleanTestCase;
import static springclean.core.domain.ApplicationContextBuilder.anApplicationContext;

import java.util.HashSet;

public class XmlBeanSetTest extends AbstractSpringCleanTestCase {
    private XmlBeanSet xmlBeanSet = new XmlBeanSet(rl.loadDocument("XmlBeanSetTest.xml").getRootElement(), anApplicationContext().build());

    @Test
    public void clazz() {
        assertThat(xmlBeanSet.clazz().name(), equalTo(HashSet.class.getName()));
    }
}