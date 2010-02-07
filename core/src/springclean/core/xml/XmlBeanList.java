package springclean.core.xml;

import nu.xom.Element;
import static org.daisychain.source.ExistingClass.existingClass;
import springclean.core.domain.ApplicationContext;

import java.util.ArrayList;

public class XmlBeanList extends AbstractXmlBeanCollection {

    public XmlBeanList(Element beanNode, ApplicationContext applicationContext) {
        super(beanNode, existingClass(ArrayList.class), applicationContext);
    }
}
