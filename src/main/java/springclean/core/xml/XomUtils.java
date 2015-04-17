package springclean.core.xml;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import nu.xom.*;
import org.xml.sax.XMLReader;
import springclean.core.exception.Defect;
import springclean.core.util.Functor;
import springclean.core.util.SimpleFunctor;
import springclean.core.xml.dtd.InternalSpringDtdEntityResolver;

import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.net.URI;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class XomUtils {

    public static <E> List<E> transform(Nodes nodes, Function<Element, E> function) {
        return Lists.transform(collect(nodes), function);
    }

    public static <E extends Throwable> void children(Element element, Functor<Node, E> functor) throws E {
        for (int i = 0; i < element.getChildCount(); i++) {
            functor.execute(element.getChild(i));
        }
    }

    public static void loop(Elements elements, SimpleFunctor<Element> closure) throws RuntimeException {
        for (int i = 0; i < elements.size(); i++) {
            closure.execute(elements.get(i));
        }
    }

    public static void loop(Nodes elements, SimpleFunctor<Element> closure) throws RuntimeException {
        for (int i = 0; i < elements.size(); i++) {
            closure.execute((Element) elements.get(i));
        }
    }

    public static <E> List<E> transform(Elements elements, Function<Element, E> function) {
        return Lists.transform(collect(elements), function);
    }

    private static List<Element> collect(Elements elements) {
        final List<Element> elementList = newArrayList();
        loop(elements, new SimpleFunctor<Element>() {
            public void execute(Element element) {
                elementList.add(element);
            }
        });
        return elementList;
    }

    private static List<Element> collect(Nodes nodes) {
        final List<Element> nodeList = newArrayList();
        for (int i = 0; i < nodes.size(); i++) {
            nodeList.add((Element) nodes.get(i));
        }

        return nodeList;
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
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        try {
            final XMLReader reader = factory.newSAXParser().getXMLReader();
            reader.setEntityResolver(new InternalSpringDtdEntityResolver());
            return new Builder(reader, false).build(xmlFile);
        } catch (Exception e) {
            throw new Defect("Problem reading XML", e);
        }
    }

}
