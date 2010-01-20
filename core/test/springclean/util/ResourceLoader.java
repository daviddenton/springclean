package springclean.util;

import nu.xom.Document;
import org.apache.commons.io.IOUtils;
import springclean.exception.Defect;
import static springclean.xml.XomUtils.parse;

import java.io.File;
import java.net.URISyntaxException;

public class ResourceLoader {
    private final Object loader;

    public ResourceLoader(Object loader) {
        this.loader = loader;
    }

    public Document loadDocument(String xmlFileName) {
        try {
            return parse(loadFile(xmlFileName));
        } catch (NullPointerException e) {
            throw new Defect("Can't find " + xmlFileName, e);
        }
    }

    public File loadFile(String name) {
        try {
            return new File(this.loader.getClass().getResource(name).toURI());
        } catch (NullPointerException e) {
            throw new Defect("Can't find " + name, e);
        } catch (URISyntaxException e) {
            throw new Defect("Can't load " + name, e);
        }
    }

    public String loadString(String name) {
        try {
            String fileData = IOUtils.toString(loader.getClass().getResourceAsStream(name));
            return fileData.replaceAll("    ", "");
        } catch (Exception e) {
            throw new RuntimeException(name, e);
        }
    }
}
