package springclean.xml;

import nu.xom.*;
import org.daisychain.util.Functor;

import java.io.File;
import java.io.IOException;
import java.net.URI;

public class XomUtils {

    public static <T extends Node, E extends Throwable> void loop(Nodes nodes, Functor<T, E> functor) throws E {
        for (int i = 0; i < nodes.size(); i++) {
            functor.execute((T) nodes.get(i));
        }
    }

    public static <E extends Throwable> void children(Element element, Functor<Node, E> functor) throws E {
        for (int i = 0; i < element.getChildCount(); i++) {
            functor.execute(element.getChild(i));
        }
    }

    public static <E extends Throwable> void loop(Elements elements, Functor<Element, E> closure) throws E {
        for (int i = 0; i < elements.size(); i++) {
            closure.execute(elements.get(i));
        }
    }

    public static <E extends Throwable> void attributes(Element element, Functor<Attribute, E> closure) throws E {
        for (int i = 0; i < element.getAttributeCount(); i++) {
            closure.execute(element.getAttribute(i));
        }
    }

    public static Document parse(URI uri) {
        return parse(new File(uri));
    }

    public static Document parse(File xmlFile) {
        try {
            return new Builder(false).build(xmlFile);
        } catch (ParsingException e) {
            throw new XomProcessingException("Parsing " + xmlFile.getAbsolutePath(), e);
        } catch (IOException e) {
            throw new XomProcessingException("Accessing " + xmlFile.getAbsolutePath(), e);
        }
    }

}
