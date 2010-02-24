package springclean.core.xml;

import nu.xom.Element;
import springclean.core.domain.ApplicationContext;
import springclean.core.domain.SpringManagedObject;
import springclean.core.exception.Defect;

public enum XmlSpringManagedObjects {
    LIST() {
        SpringManagedObject value(Element element, ApplicationContext applicationContext) {
            return new XmlBeanList(element, applicationContext);
        }},
    PROPS() {
        SpringManagedObject value(Element element, ApplicationContext applicationContext) {
            return new XmlProperties(element, applicationContext);
        }},
    SET() {
        SpringManagedObject value(Element element, ApplicationContext applicationContext) {
            return new XmlBeanSet(element, applicationContext);
        }},
    MAP() {
        SpringManagedObject value(Element element, ApplicationContext applicationContext) {
            return new XmlBeanMap(element, applicationContext);
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
            return new XmlPrimitiveValue(element.getValue());
        }},
    NULL() {
        SpringManagedObject value(Element element, ApplicationContext applicationContext) {
            return new NullValue();
        }};

    abstract SpringManagedObject value(Element element, ApplicationContext applicationContext);


    public static SpringManagedObject springManagedObjectFor(Element child, ApplicationContext applicationContext) {
        try {
            return XmlSpringManagedObjects.valueOf(child.getLocalName().toUpperCase()).value(child, applicationContext);
        } catch (IllegalArgumentException e) {
            throw new Defect("Don't know what to do with " + child.toXML());
        }
    }

}
