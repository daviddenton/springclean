package springclean.core.xml;

import nu.xom.Element;
import org.daisychain.source.AClass;
import org.daisychain.source.ExistingClass;
import org.daisychain.util.SimpleFunctor;
import springclean.core.domain.ApplicationContext;
import springclean.core.domain.BeanCollection;
import springclean.core.domain.SpringManagedObject;
import springclean.core.generate.CollectionContextElement;
import springclean.core.generate.ConstructionStrategy;
import static springclean.core.xml.XmlSpringManagedObjects.springManagedObjectFor;
import static springclean.core.xml.XomUtils.loop;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractXmlBeanCollection extends AbstractElementWrapper implements BeanCollection {


    private final ExistingClass existingClass;

    public AbstractXmlBeanCollection(Element beanNode, ExistingClass existingClass, ApplicationContext applicationContext) {
        super(beanNode, applicationContext);
        this.existingClass = existingClass;
    }

    public List<SpringManagedObject> members() {
        final List<SpringManagedObject> members = new ArrayList<SpringManagedObject>();
        loop(element.getChildElements(), new SimpleFunctor<Element>() {
            public void execute(Element target) {
                members.add(springManagedObjectFor(target, applicationContext));
            }
        });
        return members;
    }

    public ConstructionStrategy asConstructionStrategy(AClass aClass) {
        return new CollectionContextElement(this);
    }

    public AClass clazz() {
        return existingClass;
    }
}
