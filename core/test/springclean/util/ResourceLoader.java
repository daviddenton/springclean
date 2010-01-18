package springclean.util;

import nu.xom.Document;
import springclean.exception.Defect;
import static springclean.xml.XomUtils.parse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
            StringBuffer fileData = new StringBuffer(1000);
            BufferedReader reader = new BufferedReader(new FileReader(loader.getClass().getResource(name).getPath()));
            char[] buf = new char[1024];
            int numRead;
            while ((numRead = reader.read(buf)) != -1) {
                String readData = String.valueOf(buf, 0, numRead);
                fileData.append(readData);
                buf = new char[1024];
            }
            reader.close();
            return fileData.toString().replaceAll("    ", "");
        } catch (Exception e) {
            throw new RuntimeException(name, e);
        }
    }
}
