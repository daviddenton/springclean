package springclean.core.xml;

import nu.xom.Element;
import org.daisychain.source.ExistingClass;
import springclean.core.domain.ApplicationContext;

import java.util.ArrayList;

public class XmlBeanList extends AbstractXmlBeanCollection {

    public XmlBeanList(Element beanNode, ApplicationContext applicationContext) {
        super(beanNode, new ExistingClass(ArrayList.class), applicationContext);
    }
}
