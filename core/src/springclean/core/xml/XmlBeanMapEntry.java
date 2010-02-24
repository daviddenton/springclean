package springclean.core.xml;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import nu.xom.Element;
import org.daisychain.source.*;
import static org.daisychain.source.ExistingClass.existingClass;
import org.daisychain.source.body.AssignableStatement;
import springclean.core.domain.ApplicationContext;
import springclean.core.domain.BeanMapEntry;
import static springclean.core.domain.SpringId.springId;
import springclean.core.domain.SpringManagedObject;
import springclean.core.exception.Defect;
import springclean.core.generate.ConstructionStrategy;
import static springclean.core.xml.XmlSpringManagedObjects.springManagedObjectFor;

import java.util.HashMap;
import java.util.Set;

public class XmlBeanMapEntry extends AbstractElementWrapper implements BeanMapEntry {
    private static final ExistingClass MAP_CLASS = existingClass(HashMap.class);

    public XmlBeanMapEntry(Element beanNode, ApplicationContext applicationContext) {
        super(beanNode, applicationContext);
    }

    public SpringManagedObject key() {
        if (hasAttribute("key")) return new XmlPrimitiveValue(attributeValue("key"));
        if (hasAttribute("key-ref"))
            return new InlineXmlReference(springId(attributeValue("key-ref")), applicationContext);
        if (hasChild("key"))
            return springManagedObjectFor(firstChild("key").getChildElements().get(0), applicationContext);
        throw new Defect("Can't work out key bean " + this);
    }

    public SpringManagedObject value() {
        if (hasAttribute("value")) return new XmlPrimitiveValue(attributeValue("value"));
        if (hasAttribute("value-ref"))
            return new InlineXmlReference(springId(attributeValue("value-ref")), applicationContext);
        if (hasChild("key")) return springManagedObjectFor(element.getChildElements().get(1), applicationContext);
        return springManagedObjectFor(element.getChildElements().get(0), applicationContext);
    }

    public ConstructionStrategy asConstructionStrategy(AClass aClass) {
        return new ConstructionStrategy() {
            public AssignableStatement asStatement() {
                Method putMethod = new MethodFinder<ExistingMethod>(MAP_CLASS).method("put", 2);
                AssignableStatement keyStatement = key().asConstructionStrategy(existingClass(Object.class)).asStatement();
                AssignableStatement valueStatement = value().asConstructionStrategy(existingClass(Object.class)).asStatement();
                return putMethod.call(newArrayList(keyStatement, valueStatement));
            }

            public Set<Instance> dependencies() {
                // will not work
                return newHashSet();
            }
        };
    }
}
