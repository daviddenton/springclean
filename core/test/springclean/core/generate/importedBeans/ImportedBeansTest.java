package springclean.core.generate.importedBeans;

import org.junit.After;
import springclean.core.generate.AbstractFeatureTest;

import java.io.File;

public class ImportedBeansTest extends AbstractFeatureTest {
    private final File importedFile = rl.tempFile("ApplicationContext_import.xml");

    @After
    public void after() {
        importedFile.delete();
    }
}