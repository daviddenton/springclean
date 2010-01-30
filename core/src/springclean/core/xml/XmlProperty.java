package springclean.core.xml;

import nu.xom.Element;
import springclean.core.domain.ApplicationContext;
import springclean.core.domain.Property;
import springclean.core.domain.PropertyName;
import static springclean.core.domain.PropertyName.propertyName;

public class XmlProperty extends XmlConstructionDependency implements Property {

    public XmlProperty(Element propertyElement, ApplicationContext applicationContext) {
        super(propertyElement, applicationContext);
    }

    public PropertyName name() {
        return propertyName(attributeValue("name"));
    }
}
