package springclean.xml;

import nu.xom.Element;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import springclean.core.AbstractSpringCleanTestCase;
import static springclean.core.domain.SpringId.springId;
import springclean.core.xml.XmlReference;
import static springclean.domain.ApplicationContextBuilder.anApplicationContext;

public class XmlReferenceTest extends AbstractSpringCleanTestCase {
    private final Element element = rl.loadDocument("XmlReferenceTest.xml").getRootElement();

    @Test
    public void testBean() {
        assertThat(new XmlReference(element, anApplicationContext().build()).id(), equalTo(springId("beanName")));
    }
}

