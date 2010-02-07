package springclean.core.xml;

import com.google.common.base.Function;
import nu.xom.Element;
import org.daisychain.source.AClass;
import org.daisychain.source.ExistingClass;
import springclean.core.domain.ApplicationContext;
import springclean.core.domain.BeanCollection;
import springclean.core.domain.SpringManagedObject;
import springclean.core.generate.CollectionContextElement;
import springclean.core.generate.ConstructionStrategy;
import static springclean.core.xml.XmlSpringManagedObjects.springManagedObjectFor;

import java.util.List;

public abstract class AbstractXmlBeanCollection extends AbstractElementWrapper implements BeanCollection {


    private final ExistingClass existingClass;

    public AbstractXmlBeanCollection(Element beanNode, ExistingClass existingClass, ApplicationContext applicationContext) {
        super(beanNode, applicationContext);
        this.existingClass = existingClass;
    }

    public List<SpringManagedObject> members() {
        return XomUtils.transform(element.getChildElements(), new Function<Element, SpringManagedObject>() {
            public SpringManagedObject apply(Element target) {
                return springManagedObjectFor(target, applicationContext);
            }
        });
    }

    public ConstructionStrategy asConstructionStrategy(AClass aClass) {
        return new CollectionContextElement(this);
    }

    public AClass clazz() {
        return existingClass;
    }
}
