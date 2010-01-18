package microtypes;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;

import java.io.File;

public class FileValueTest {

    @Test
    public void testEquals() {
        assertThat(forFile("./data/"), equalTo(forFile("data/../data")));
    }

    private FileValue forFile(String filePath) {
        return new FileValue(new File(filePath)) {
        };
    }
}
