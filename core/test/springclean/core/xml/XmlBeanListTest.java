package springclean.core.xml;

import static org.hamcrest.Matchers.equalTo;
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
}
