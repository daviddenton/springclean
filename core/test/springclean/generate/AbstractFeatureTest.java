package springclean.generate;

import static org.daisychain.util.TestHelper.assertSource;
import static org.hamcrest.CoreMatchers.equalTo;
import org.junit.Test;
import springclean.AbstractSpringCleanTestCase;
import springclean.domain.ApplicationContext;
import springclean.xml.XmlApplicationContext;

public abstract class AbstractFeatureTest extends AbstractSpringCleanTestCase {

    @Test
    public void featureGeneratesCorrectSource() throws Exception {
        ApplicationContext applicationContext = new XmlApplicationContext(rl.loadFile("ApplicationContext.xml"));
        ApplicationContextClassBuilder contextClassBuilder = new ApplicationContextClassBuilder(applicationContext);
        assertSource(contextClassBuilder.build(), equalTo(rl.loadString("ApplicationContext.javatest")));
    }

}