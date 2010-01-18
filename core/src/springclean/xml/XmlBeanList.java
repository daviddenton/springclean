package springclean.xml;

import nu.xom.Element;
import org.daisychain.source.ExistingClass;

import java.util.ArrayList;

import springclean.domain.ApplicationContext;

public class XmlBeanList extends AbstractXmlBeanCollection {

    public XmlBeanList(Element beanNode, ApplicationContext applicationContext) {
        super(beanNode, new ExistingClass(ArrayList.class), applicationContext);
    }
}
