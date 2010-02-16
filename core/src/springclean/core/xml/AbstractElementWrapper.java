package springclean.core.xml;

import nu.xom.Element;
import nu.xom.Elements;
import springclean.core.domain.ApplicationContext;

class AbstractElementWrapper {
    protected final Element element;
    protected final ApplicationContext applicationContext;

    protected AbstractElementWrapper(Element beanNode, ApplicationContext applicationContext) {
        this.element = beanNode;
        this.applicationContext = applicationContext;
    }

    protected boolean hasChild(String name) {
        return element.getChildElements(name).size() > 0;
    }

    protected Element firstChild(String name) {
        return element.getChildElements(name).get(0);
    }

    protected Elements children(String name) {
        return element.getChildElements(name);
    }

    protected boolean hasAttribute(String name) {
        return element.getAttribute(name) != null;
    }

    protected String attributeValue(String name) {
        if (!hasAttribute(name))
            throw new XomProcessingException("Attribute " + name + " not found in " + element.toXML());
        return element.getAttributeValue(name);
    }

    protected boolean attributeValueEquals(String name, String value) {
        return hasAttribute(name) && value.equalsIgnoreCase(element.getAttributeValue(name));
    }

    @Override
    public String toString() {
        return element.toXML();
    }
}
