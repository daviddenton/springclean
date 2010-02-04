package springclean.core.xml;

import nu.xom.Element;
import springclean.core.domain.ApplicationContext;
import springclean.core.domain.ConstructorArg;

public class XmlConstructorArg extends XmlConstructionDependency implements ConstructorArg {

    public XmlConstructorArg(Element constructorArgElement, ApplicationContext applicationContext) {
        super(constructorArgElement, applicationContext);
    }

    public boolean isIndexed() {
        return hasAttribute("index");
    }

    public int index() {
        return Integer.parseInt(attributeValue("index"));
    }
}
