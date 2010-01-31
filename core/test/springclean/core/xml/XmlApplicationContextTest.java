package springclean.core.xml;

import static org.hamcrest.Matchers.equalTo;
import org.junit.After;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import springclean.core.AbstractSpringCleanTestCase;
import static springclean.core.domain.ContextName.contextName;

import java.io.File;

public class XmlApplicationContextTest extends AbstractSpringCleanTestCase {
    private final File file = rl.tempFile("XmlApplicationContextTest_main.xml");
    private final File importedFile = rl.tempFile("XmlApplicationContextTest_imported.xml");
    private final File doubleImportedFile = rl.tempFile("XmlApplicationContextTest_doubleImported.xml");

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

    @After
    public void after() {
        file.delete();
        importedFile.delete();
        doubleImportedFile.delete();
    }
}
