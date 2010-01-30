package springclean.core.generate;

import org.daisychain.source.SourceFile;
import org.daisychain.source.SourcePackage;
import org.daisychain.source.util.IndentingStringWriter;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Ignore;
import org.junit.Test;
import springclean.core.AbstractSpringCleanTestCase;
import springclean.core.domain.ApplicationContext;
import springclean.core.xml.XmlApplicationContext;

import java.io.File;
import java.io.StringWriter;

public class ApplicationContextClassBuilderTest extends AbstractSpringCleanTestCase {

    @Test
    @Ignore
    public void test() throws Exception {
        final File file = rl.tempFile("data/ApplicationContextSourceFileBuilderTestContext.xml");
        final ApplicationContext applicationContext = new XmlApplicationContext(file);
        final ApplicationContextClassBuilder contextClassBuilder = new ApplicationContextClassBuilder(applicationContext);

        StringWriter sWriter = new StringWriter();
        IndentingStringWriter writer = new IndentingStringWriter(sWriter);
        try {
            new SourceFile(new SourcePackage(applicationContext.name().asPackage()), contextClassBuilder.build()).appendSource(writer);
        } finally {
            writer.close();
        }

        System.out.println(rl.tempFile("data/ApplicationContextSourceFileBuilderTestContext.xml").getAbsolutePath());
        System.out.println(sWriter.toString());

        assertThat(sWriter.toString(), equalTo(rl.loadString("data/ApplicationContextSourceFileBuilderTestContext.javatest")));
    }
}
