package springclean.xml;

import nu.xom.Element;
import springclean.domain.ConstructorArg;
import springclean.domain.ApplicationContext;

public class XmlConstructorArg extends XmlConstructionDependency implements ConstructorArg {

    public XmlConstructorArg(Element constructorArgElement, ApplicationContext applicationContext) {
        super(constructorArgElement, applicationContext);
    }

}
