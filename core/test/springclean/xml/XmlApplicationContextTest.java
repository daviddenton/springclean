package springclean.xml;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import springclean.AbstractSpringCleanTestCase;
import static springclean.domain.ContextName.contextName;

import java.io.File;

public class XmlApplicationContextTest extends AbstractSpringCleanTestCase {
    private final File file = rl.tempFile("XmlApplicationContextTest_main.xml");

    @Test
    public void forAllBeans() throws Exception {
        assertThat(new XmlApplicationContext(file).beans().size(), equalTo(1));
    }

    @Test
    public void name() throws Exception {
        assertThat(new XmlApplicationContext(file).name(), equalTo(contextName(file)));
    }

    @Test
    public void forAllImportedBeans() throws Exception {
        assertThat(new XmlApplicationContext(file).importedBeans().size(), equalTo(2));
    }
}
