package springclean.xml;

import nu.xom.Element;
import springclean.domain.Property;
import springclean.domain.PropertyName;
import springclean.domain.ApplicationContext;
import static springclean.domain.PropertyName.propertyName;

public class XmlProperty extends XmlConstructionDependency implements Property {

    public XmlProperty(Element propertyElement, ApplicationContext applicationContext) {
        super(propertyElement, applicationContext);
    }

    public PropertyName name() {
        return propertyName(attributeValue("name"));
    }
}
