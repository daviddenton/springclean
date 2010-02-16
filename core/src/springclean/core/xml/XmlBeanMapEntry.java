package springclean.core.xml;

import nu.xom.Element;
import org.daisychain.source.AClass;
import springclean.core.domain.ApplicationContext;
import springclean.core.domain.BeanMapEntry;
import springclean.core.domain.SpringManagedObject;
import springclean.core.generate.ConstructionStrategy;

public class XmlBeanMapEntry extends AbstractElementWrapper implements BeanMapEntry {
    public XmlBeanMapEntry(Element beanNode, ApplicationContext applicationContext) {
        super(beanNode, applicationContext);
    }

    public SpringManagedObject key() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public SpringManagedObject value() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ConstructionStrategy asConstructionStrategy(AClass aClass) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
