package springclean.core.xml;

import nu.xom.Element;
import static org.daisychain.source.ExistingClass.existingClass;
import springclean.core.domain.ApplicationContext;

import java.util.HashSet;

public class XmlBeanSet extends AbstractXmlBeanCollection {

    public XmlBeanSet(Element beanNode, ApplicationContext applicationContext) {
        super(beanNode, existingClass(HashSet.class), applicationContext);
    }
}
