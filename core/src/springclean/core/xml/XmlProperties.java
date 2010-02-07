package springclean.core.xml;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import nu.xom.Element;
import org.daisychain.source.*;
import static org.daisychain.source.ExistingClass.existingClass;
import org.daisychain.source.body.AssignableStatement;
import static org.daisychain.source.body.Value.quotedValue;
import org.daisychain.source.util.IndentingStringWriter;
import org.daisychain.source.util.ListAppender;
import static org.daisychain.source.util.ListAppender.generateSource;
import org.daisychain.util.SimpleFunctor;
import springclean.core.domain.ApplicationContext;
import springclean.core.domain.SpringManagedObject;
import springclean.core.generate.ConstructionStrategy;
import springclean.core.generate.InitializerBlockEnd;
import springclean.core.generate.InitializerBlockStart;
import static springclean.core.xml.XomUtils.loop;

import java.io.IOException;
import java.util.*;

public class XmlProperties extends AbstractElementWrapper implements SpringManagedObject {
    private final ExistingClass propertiesClass = existingClass(Properties.class);

    public XmlProperties(Element propertiesElement, ApplicationContext applicationContext) {
        super(propertiesElement, applicationContext);
    }

    public ConstructionStrategy asConstructionStrategy(AClass aClass) {

        return new ConstructionStrategy() {
            public AssignableStatement asStatement() {
                return new AssignableStatement() {
                    public Set<AClass> getImports() {
                        return new HashSet<AClass>() {{
                            add(propertiesClass);
                        }};
                    }

                    public void appendSource(IndentingStringWriter writer) throws IOException {
                        propertiesClass.instantiate(Collections.EMPTY_LIST).appendSource(writer);
                        ListAppender.loop(members())
                                .withPrefix(new InitializerBlockStart())
                                .andForEach(generateSource()).seperatedBy(";\n")
                                .withSuffix(new InitializerBlockEnd())
                                .to(writer);
                    }
                };
            }

            public Set<Instance> dependencies() {
                return newHashSet();
            }
        };
    }

    private List<AssignableStatement> members() {
        final Method putMethod = new MethodFinder<ExistingMethod>(propertiesClass).method("put", 2);
        final List<AssignableStatement> propertyPutStatements = newArrayList();
        loop(element.getChildElements("prop"), new SimpleFunctor<Element>() {
            public void execute(final Element target) {
                propertyPutStatements.add(putMethod.call(
                        new ArrayList<AssignableStatement>() {{
                            add(quotedValue(target.getAttributeValue("key")));
                            add(quotedValue(target.getValue()));
                        }}));
            }
        });
        return propertyPutStatements;
    }
}
