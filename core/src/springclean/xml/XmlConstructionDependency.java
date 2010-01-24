package springclean.xml;

import nu.xom.Element;
import springclean.domain.ApplicationContext;
import springclean.domain.IdentifiedBean;
import springclean.domain.SpringId;
import static springclean.domain.SpringId.springId;
import springclean.domain.SpringManagedObject;
import springclean.exception.Defect;

public class XmlConstructionDependency extends AbstractElementWrapper {
    public XmlConstructionDependency(Element beanNode, ApplicationContext applicationContext) {
        super(beanNode, applicationContext);
    }

    public SpringManagedObject referencedObject() {
        if (hasAttribute("ref")) {
            SpringId id = springId(attributeValue("ref"));
            IdentifiedBean identifiedBean = applicationContext.findBean(id);
            return identifiedBean.isAbstract() ? identifiedBean : new SimpleReference(id, applicationContext);
        }

        if (hasAttribute("value")) return new XmlPrimitiveValue(attributeValue("value"), applicationContext);

        Element child = element.getChildElements().get(0);
        try {
            return XmlSpringManagedObjects.valueOf(child.getLocalName().toUpperCase()).value(child, applicationContext);
        } catch (IllegalArgumentException e) {
            throw new Defect("Don't know what to do with " + child.toXML());
        }

    }

    private enum XmlSpringManagedObjects {
        LIST() {
            SpringManagedObject value(Element element, ApplicationContext applicationContext) {
                return new XmlBeanList(element, applicationContext);
            }},
        SET() {
            SpringManagedObject value(Element element, ApplicationContext applicationContext) {
                return new XmlBeanSet(element, applicationContext);
            }},
        REF() {
            SpringManagedObject value(Element element, ApplicationContext applicationContext) {
                return new XmlReference(element, applicationContext);
            }},
        BEAN() {
            SpringManagedObject value(Element element, ApplicationContext applicationContext) {
                return new XmlBean(element, applicationContext);
            }},
        VALUE() {
            SpringManagedObject value(Element element, ApplicationContext applicationContext) {
                return new XmlPrimitiveValue(element.getValue(), applicationContext);
            }},
        NULL() {
            SpringManagedObject value(Element element, ApplicationContext applicationContext) {
                return new NullValue();
            }};

        abstract SpringManagedObject value(Element element, ApplicationContext applicationContext);

    }
}
