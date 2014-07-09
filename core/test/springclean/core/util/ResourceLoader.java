package springclean.core.util;

import nu.xom.Document;
import org.apache.commons.io.IOUtils;
import springclean.core.exception.Defect;
import static springclean.core.xml.XomUtils.parse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ResourceLoader {
    private final Object loader;

    public ResourceLoader(Object loader) {
        this.loader = loader;
    }

    public Document loadDocument(String xmlFileName) {
        try {
            return parse(tempFile(xmlFileName));
        } catch (NullPointerException e) {
            throw new Defect("Can't find " + xmlFileName, e);
        }
    }

    public File tempFile(String name) {
        try {
            new File("build").mkdirs(); // TODO: quick hack to make tests work.
            File tmpFile = new File("build", name);
            tmpFile.createNewFile();
            IOUtils.copy(this.loader.getClass().getResourceAsStream(name), new FileOutputStream(tmpFile));
            return tmpFile;
        } catch (IOException e) {
            throw new Defect("Can't copy to " + name, e);
        }
    }

    public String loadString(String name) {
        try {
            InputStream asStream = loader.getClass().getResourceAsStream(name);
            String fileData = IOUtils.toString(asStream);
            return fileData.replaceAll("    ", "");
        } catch (Exception e) {
            throw new RuntimeException(name, e);
        }
    }
}
