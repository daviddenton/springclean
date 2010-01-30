package springclean.core.xml;

import nu.xom.Element;
import springclean.core.domain.ApplicationContext;
import springclean.core.domain.SpringManagedObject;
import springclean.core.exception.Defect;


public class XmlSpringManagedObjectFactory {

    private enum SpringManagedObjects {
        LIST() {
            SpringManagedObject elementFor(Element element, ApplicationContext applicationContext) {
                return new XmlBeanList(element, applicationContext);
            }},
        SET() {
            SpringManagedObject elementFor(Element element, ApplicationContext applicationContext) {
                return new XmlBeanSet(element, applicationContext);
            }},
        REF() {
            SpringManagedObject elementFor(Element element, ApplicationContext applicationContext) {
                return new XmlReference(element, applicationContext);
            }},
        BEAN() {
            SpringManagedObject elementFor(Element element, ApplicationContext applicationContext) {
                return new XmlIdentifiedBean(element, applicationContext);
            }},
        VALUE() {
            SpringManagedObject elementFor(Element element, ApplicationContext applicationContext) {
                return new XmlPrimitiveValue(element.getValue(), applicationContext);
            }},
        NULL() {
            SpringManagedObject elementFor(Element element, ApplicationContext applicationContext) {
                return new NullValue();
            }};

        abstract SpringManagedObject elementFor(Element element, ApplicationContext applicationContext);
    }

    public static SpringManagedObject build(Element element, ApplicationContext applicationContext) {
        try {
            return SpringManagedObjects.valueOf(element.getLocalName().toUpperCase()).elementFor(element, applicationContext);
        } catch (IllegalArgumentException e) {
            throw new Defect("Don't know what to do with " + element.toXML());
        }
    }
}
