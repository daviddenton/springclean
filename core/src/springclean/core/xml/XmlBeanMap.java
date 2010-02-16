package springclean.core.xml;

import nu.xom.Element;
import org.daisychain.source.AClass;
import org.daisychain.source.ExistingClass;
import springclean.core.domain.ApplicationContext;
import springclean.core.domain.BeanMap;
import springclean.core.domain.SpringManagedObject;
import springclean.core.generate.ConstructionStrategy;
import springclean.core.generate.MapContextElement;

import static java.util.Collections.EMPTY_LIST;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlBeanMap extends AbstractElementWrapper implements BeanMap {

    public XmlBeanMap(Element beanNode, ApplicationContext applicationContext) {
        super(beanNode, applicationContext);
    }

    public ConstructionStrategy asConstructionStrategy(AClass aClass) {
        return new MapContextElement(this);
    }

    public AClass clazz() {
        return ExistingClass.existingClass(HashMap.class);
    }

    public List<Map.Entry<SpringManagedObject, SpringManagedObject>> entries() {
        return (List<Map.Entry<SpringManagedObject, SpringManagedObject>>) EMPTY_LIST;
    }
}