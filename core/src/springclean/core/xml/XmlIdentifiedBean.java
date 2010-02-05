package springclean.core.xml;

import static com.google.common.collect.Sets.newHashSet;
import nu.xom.Element;
import org.apache.commons.lang.StringUtils;
import org.daisychain.source.AClass;
import springclean.core.domain.ApplicationContext;
import springclean.core.domain.IdentifiedBean;
import springclean.core.domain.SpringId;
import static springclean.core.domain.SpringId.springId;
import springclean.core.generate.ConstructionStrategy;
import springclean.core.generate.IdentifiedBeanConstructionStrategy;

import java.util.Set;

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

    public ConstructionStrategy asConstructionStrategy(AClass aClass) {
        return isAbstract() ? constructionStrategy() : new IdentifiedBeanConstructionStrategy(this, constructionStrategy());
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

    public boolean isKnownAs(SpringId springId) {
        return id().equals(springId) || names().contains(springId);
    }

    private Set<SpringId> names() {
        if (!hasAttribute("name")) return newHashSet();

        Set<SpringId> names = newHashSet();
        for (String namePart : attributeValue("name").split(",; ")) {
            names.add(SpringId.springId(namePart));
        }
        return names;
    }
}
