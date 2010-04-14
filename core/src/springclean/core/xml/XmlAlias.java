package springclean.core.xml;

import nu.xom.Element;
import org.daisychain.source.AClass;
import org.daisychain.source.ExistingMethod;
import springclean.core.domain.Alias;
import springclean.core.domain.ApplicationContext;
import springclean.core.domain.IdentifiedBean;
import springclean.core.domain.SpringId;
import static springclean.core.domain.SpringId.springId;

public class XmlAlias extends AbstractElementWrapper implements Alias {

    public XmlAlias(Element beanNode, ApplicationContext applicationContext) {
        super(beanNode, applicationContext);
    }

    public AClass<ExistingMethod> clazz() {
        return bean().declaredBeanClass();
    }

    public SpringId id() {
        return springId(attributeValue("alias"));
    }

    public IdentifiedBean bean() {
        return applicationContext.findBean(springId(attributeValue("name")));
    }
}
