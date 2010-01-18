package springclean.xml;

import nu.xom.Element;
import org.daisychain.source.AClass;
import org.daisychain.source.ExistingMethod;
import springclean.domain.Reference;
import springclean.domain.SpringId;
import springclean.domain.ApplicationContext;
import static springclean.domain.SpringId.springId;
import springclean.generate.RefContextElement;

public class XmlReference extends AbstractElementWrapper implements Reference {
    public XmlReference(Element referenceNode, ApplicationContext applicationContext) {
        super(referenceNode, applicationContext);
    }

    public SpringId id() {
        if(hasAttribute("bean")) return springId(attributeValue("bean"));
        if(hasAttribute("local")) return springId(attributeValue("local"));
        if(hasAttribute("name")) return springId(attributeValue("name"));
        throw new XomProcessingException("Can't work out id() for ref " + this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        XmlIdentifiedBean xmlIdentifiedBean = (XmlIdentifiedBean) o;

        SpringId id = id();
        if (id != null ? !id.equals(xmlIdentifiedBean.id()) : xmlIdentifiedBean.id() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        SpringId id = id();
        return id != null ? id.hashCode() : 0;
    }

    public RefContextElement asContextElement(AClass aClass) {
        return new RefContextElement(this, aClass);
    }

    public AClass<ExistingMethod> clazz() {
        return applicationContext.findBean(id()).clazz();
    }
}
