package springclean.core.xml.dtd;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class InternalSpringDtdEntityResolver implements EntityResolver {
    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
        String fileName = new File(new URL(systemId).getFile()).getName();
        System.out.println(fileName);
        return new InputSource(this.getClass().getResourceAsStream(fileName));
    }
}
