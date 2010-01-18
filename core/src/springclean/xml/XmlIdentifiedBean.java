package springclean.xml;

import nu.xom.Element;
import org.apache.commons.lang.StringUtils;
import org.daisychain.source.AClass;
import springclean.domain.ApplicationContext;
import springclean.domain.IdentifiedBean;
import springclean.domain.SpringId;
import static springclean.domain.SpringId.springId;
import springclean.generate.BeanContextElement;
import springclean.generate.ConstructionStrategy;
import springclean.generate.ContextElement;
import springclean.generate.IdentifiedBeanConstructionStrategy;

public class XmlIdentifiedBean extends XmlBean implements IdentifiedBean {

    public XmlIdentifiedBean(Element beanNode, ApplicationContext applicationContext) {
        super(beanNode, applicationContext);
    }

    public SpringId id() {
        if (hasAttribute("id")) {
            return springId(element.getAttributeValue("id"));
        } else if(hasAttribute("name")){
            return springId(element.getAttributeValue("name"));
        } else if (hasAttribute("class")) {
            return springId(StringUtils.uncapitalize(beanClass().getSimpleName()));
        }
        throw new XomProcessingException("can't work out id for bean " + element.toXML());
    }

    public ContextElement asContextElement(AClass aClass) {
        ConstructionStrategy constructionStrategy = new IdentifiedBeanConstructionStrategy(this, constructionStrategy());
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
