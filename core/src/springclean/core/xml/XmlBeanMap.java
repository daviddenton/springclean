package springclean.core.xml;

import com.google.common.base.Function;
import nu.xom.Element;
import org.daisychain.source.AClass;
import org.daisychain.source.ExistingClass;
import springclean.core.domain.ApplicationContext;
import springclean.core.domain.BeanMap;
import springclean.core.domain.BeanMapEntry;
import springclean.core.generate.ConstructionStrategy;
import springclean.core.generate.MapContextElement;
import static springclean.core.xml.XomUtils.transform;

import java.util.HashMap;
import java.util.List;

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

    public List<BeanMapEntry> entries() {
        return transform(element.getChildElements(), new Function<Element, BeanMapEntry>() {
            public BeanMapEntry apply(Element element) {
                return new XmlBeanMapEntry(element, applicationContext);
            }
        });
    }

}