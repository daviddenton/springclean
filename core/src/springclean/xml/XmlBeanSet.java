package springclean.xml;

import nu.xom.Element;
import org.daisychain.source.ExistingClass;

import java.util.HashSet;

import springclean.domain.ApplicationContext;

public class XmlBeanSet extends AbstractXmlBeanCollection {

    public XmlBeanSet(Element beanNode, ApplicationContext applicationContext) {
        super(beanNode, new ExistingClass(HashSet.class), applicationContext);
    }
}
