package springclean.xml;

import nu.xom.Element;
import org.daisychain.source.AClass;
import org.daisychain.source.ExistingClass;
import org.daisychain.util.Functor;
import springclean.domain.BeanCollection;
import springclean.domain.SpringManagedObject;
import springclean.domain.ApplicationContext;
import springclean.generate.CollectionContextElement;
import static springclean.xml.XomUtils.loop;

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

    public CollectionContextElement asContextElement(AClass aClass) {
        return new CollectionContextElement(this, clazz());
    }

    public AClass clazz() {
        return existingClass;
    }
}
