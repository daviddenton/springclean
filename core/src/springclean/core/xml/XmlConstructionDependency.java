package springclean.core.xml;

import nu.xom.Element;
import springclean.core.domain.ApplicationContext;
import springclean.core.domain.IdentifiedBean;
import springclean.core.domain.SpringId;
import static springclean.core.domain.SpringId.springId;
import springclean.core.domain.SpringManagedObject;
import static springclean.core.xml.XmlSpringManagedObjects.springManagedObjectFor;

public class XmlConstructionDependency extends AbstractElementWrapper {
    public XmlConstructionDependency(Element beanNode, ApplicationContext applicationContext) {
        super(beanNode, applicationContext);
    }

    public SpringManagedObject referencedObject() {
        if (hasAttribute("ref")) {
            SpringId id = springId(attributeValue("ref"));
            IdentifiedBean identifiedBean = applicationContext.findBean(id);
            return identifiedBean.isAbstract() ? identifiedBean : new InlineXmlReference(identifiedBean.id(), applicationContext);
        }

        if (hasAttribute("value")) return new XmlPrimitiveValue(attributeValue("value"));

        return springManagedObjectFor(element.getChildElements().get(0), applicationContext);
    }

}
