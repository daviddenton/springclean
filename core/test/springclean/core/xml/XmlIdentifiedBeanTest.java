package springclean.core.xml;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import springclean.core.AbstractSpringCleanTestCase;
import static springclean.core.domain.ApplicationContextBuilder.anApplicationContext;
import static springclean.core.domain.SpringId.springId;

public class XmlIdentifiedBeanTest extends AbstractSpringCleanTestCase {

    @Test
    public void usesId() {
        assertThat(xmlIdentifiedBeanFrom("XmlIdentifiedBeanTest_id.xml").id(), equalTo(springId("idString")));
    }

    @Test
    public void usesNameIfNoId() {
        assertThat(xmlIdentifiedBeanFrom("XmlIdentifiedBeanTest_name.xml").id(), equalTo(springId("nameString")));
    }

    @Test
    public void guessesBeanNameFromClass() {
        assertThat(xmlIdentifiedBeanFrom("XmlIdentifiedBeanTest_guessName.xml").id(), equalTo(springId("string")));
    }

    @Test
    public void clazz() {
        assertThat(xmlIdentifiedBeanFrom("XmlIdentifiedBeanTest_id.xml").clazz().name(), equalTo(String.class.getName()));
    }

    private XmlIdentifiedBean xmlIdentifiedBeanFrom(String xmlFileName) {
        return new XmlIdentifiedBean(rl.loadDocument(xmlFileName).getRootElement(), anApplicationContext().build());
    }
}
