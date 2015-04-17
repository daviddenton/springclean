package springclean.core.xml;

import nu.xom.Element;
import org.daisychain.source.AClass;
import org.daisychain.source.ExistingMethod;
import springclean.core.domain.ApplicationContext;
import springclean.core.domain.IdentifiedBean;
import springclean.core.domain.Reference;
import springclean.core.domain.SpringId;
import static springclean.core.domain.SpringId.springId;
import springclean.core.generate.ConstructionStrategy;
import springclean.core.generate.RefConstructionStrategy;

public class XmlReference extends AbstractElementWrapper implements Reference {
    public XmlReference(Element referenceNode, ApplicationContext applicationContext) {
        super(referenceNode, applicationContext);
    }

    public SpringId id() {
        if (hasAttribute("bean")) return springId(attributeValue("bean"));
        if (hasAttribute("local")) return springId(attributeValue("local"));
        if (hasAttribute("name")) return springId(attributeValue("name"));
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

    public ConstructionStrategy asConstructionStrategy(AClass aClass) {
        IdentifiedBean identifiedBean = applicationContext.findBean(id());
        return identifiedBean.isAbstract() ? identifiedBean.asConstructionStrategy(aClass) : new RefConstructionStrategy(this, aClass);
    }

    public AClass<ExistingMethod> clazz() {
        return applicationContext.findBean(id()).declaredBeanClass();
    }
}
