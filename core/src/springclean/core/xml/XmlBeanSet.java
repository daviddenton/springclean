package springclean.core.xml;

import nu.xom.Element;
import org.daisychain.source.ExistingClass;
import springclean.core.domain.ApplicationContext;

import java.util.HashSet;

public class XmlBeanSet extends AbstractXmlBeanCollection {

    public XmlBeanSet(Element beanNode, ApplicationContext applicationContext) {
        super(beanNode, new ExistingClass(HashSet.class), applicationContext);
    }
}
