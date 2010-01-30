package springclean.core.generate;

import static org.daisychain.util.TestHelper.assertSource;
import static org.hamcrest.CoreMatchers.equalTo;
import org.junit.After;
import org.junit.Test;
import springclean.core.AbstractSpringCleanTestCase;
import springclean.domain.ApplicationContext;
import springclean.generate.ApplicationContextClassBuilder;
import springclean.xml.XmlApplicationContext;

import java.io.File;

public abstract class AbstractFeatureTest extends AbstractSpringCleanTestCase {
    private File xmlFile = rl.tempFile("ApplicationContext.xml");

    @Test
    public void featureGeneratesCorrectSource() throws Exception {
        ApplicationContext applicationContext = new XmlApplicationContext(xmlFile);
        ApplicationContextClassBuilder contextClassBuilder = new ApplicationContextClassBuilder(applicationContext);
        assertSource(contextClassBuilder.build(), equalTo(rl.loadString("ApplicationContext.javatest")));
    }

    @After
    public void after() {
        xmlFile.delete();
    }

}