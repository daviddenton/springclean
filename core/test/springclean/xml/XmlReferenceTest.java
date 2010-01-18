package springclean.xml;

import nu.xom.Element;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import springclean.AbstractSpringCleanTestCase;
import static springclean.domain.SpringId.springId;
import static springclean.domain.ApplicationContextBuilder.anApplicationContext;

public class XmlReferenceTest extends AbstractSpringCleanTestCase {
    private final Element element = rl.loadDocument("XmlReferenceTest.xml").getRootElement();

    @Test
    public void testBean() {
        assertThat(new XmlReference(element, anApplicationContext().build()).id(), equalTo(springId("beanName")));
    }
}

