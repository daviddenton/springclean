package springclean.core.xml;

import nu.xom.Element;
import org.apache.commons.lang.StringUtils;
import org.daisychain.source.AClass;
import springclean.core.domain.ApplicationContext;
import springclean.core.domain.IdentifiedBean;
import springclean.core.domain.SpringId;
import static springclean.core.domain.SpringId.springId;
import springclean.core.generate.BeanContextElement;
import springclean.core.generate.ConstructionStrategy;
import springclean.core.generate.ContextElement;
import springclean.core.generate.IdentifiedBeanConstructionStrategy;

public class XmlIdentifiedBean extends XmlBean implements IdentifiedBean {

    public XmlIdentifiedBean(Element beanNode, ApplicationContext applicationContext) {
        super(beanNode, applicationContext);
    }

    public SpringId id() {
        if (hasAttribute("id")) {
            return springId(element.getAttributeValue("id"));
        } else if (hasAttribute("name")) {
            return springId(element.getAttributeValue("name"));
        } else if (hasAttribute("class")) {
            return springId(StringUtils.uncapitalize(beanClass().getSimpleName()));
        }
        throw new XomProcessingException("can't work out id for bean " + element.toXML());
    }

    public ContextElement asContextElement(AClass aClass) {
        ConstructionStrategy constructionStrategy = isAbstract() ? constructionStrategy() : new IdentifiedBeanConstructionStrategy(this, constructionStrategy());
        return new BeanContextElement(this, constructionStrategy);
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
        return id != null ? id().hashCode() : 0;
    }

    @Override
    public String toString() {
        return id().value;
    }

}
