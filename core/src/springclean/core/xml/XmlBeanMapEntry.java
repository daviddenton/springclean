package springclean.core.xml;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import nu.xom.Element;
import org.daisychain.source.*;
import static org.daisychain.source.ExistingClass.existingClass;
import org.daisychain.source.body.AssignableStatement;
import springclean.core.domain.ApplicationContext;
import springclean.core.domain.BeanMapEntry;
import springclean.core.domain.SpringManagedObject;
import springclean.core.generate.ConstructionStrategy;

import java.util.HashMap;
import java.util.Set;

public class XmlBeanMapEntry extends AbstractElementWrapper implements BeanMapEntry {
    private static final ExistingClass MAP_CLASS = existingClass(HashMap.class);

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
