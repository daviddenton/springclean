package springclean.core.xml;

import nu.xom.Element;
import org.daisychain.source.AClass;
import org.daisychain.source.ExistingClass;
import org.daisychain.util.Functor;
import springclean.core.domain.ApplicationContext;
import springclean.core.domain.BeanCollection;
import springclean.core.domain.SpringManagedObject;
import springclean.core.generate.CollectionContextElement;
import springclean.core.generate.ConstructionStrategy;
import static springclean.core.xml.XomUtils.loop;

public abstract class AbstractXmlBeanCollection extends AbstractElementWrapper implements BeanCollection {

    private final ExistingClass existingClass;

    public AbstractXmlBeanCollection(Element beanNode, ExistingClass existingClass, ApplicationContext applicationContext) {
        super(beanNode, applicationContext);
        this.existingClass = existingClass;
    }

    public <E extends Throwable> void forAllMembers(final Functor<SpringManagedObject, E> memberFunctor) throws E {
        loop(element.getChildElements(), new Functor<Element, E>() {
            public void execute(Element target) throws E {
                memberFunctor.execute(XmlSpringManagedObjectFactory.build(target, applicationContext));
            }
        });
    }

    public ConstructionStrategy asConstructionStrategy(AClass aClass) {
        return new CollectionContextElement(this, clazz());
    }

    public AClass clazz() {
        return existingClass;
    }
}
