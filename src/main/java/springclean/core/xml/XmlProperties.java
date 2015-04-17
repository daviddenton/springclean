package springclean.core.xml;

import com.google.common.base.Function;
import nu.xom.Element;
import org.daisychain.source.*;
import org.daisychain.source.body.AssignableStatement;
import org.daisychain.source.util.IndentingStringWriter;
import org.daisychain.source.util.ListAppender;
import springclean.core.domain.ApplicationContext;
import springclean.core.domain.SpringManagedObject;
import springclean.core.generate.ConstructionStrategy;
import springclean.core.generate.InitializerBlockEnd;
import springclean.core.generate.InitializerBlockStart;

import java.io.IOException;
import java.util.*;

import static com.google.common.collect.Lists.transform;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.Collections.EMPTY_LIST;
import static org.daisychain.source.ExistingClass.existingClass;
import static org.daisychain.source.body.Value.quotedValue;
import static org.daisychain.source.util.ListAppender.generateSource;

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
                        propertiesClass.instantiate(EMPTY_LIST).appendSource(writer);
                        ListAppender.loop(memberStatements())
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

    private List<XmlPropertyEntry> members() {
        return XomUtils.transform(element.getChildElements("prop"), new Function<Element, XmlPropertyEntry>() {
            public XmlPropertyEntry apply(Element element) {
                return new XmlPropertyEntry(element, applicationContext);
            }
        });
    }

    private List<AssignableStatement> memberStatements() {
        final Method putMethod = new MethodFinder<>(propertiesClass).method("put", 2);
        return transform(members(), new Function<XmlPropertyEntry, AssignableStatement>() {
            public AssignableStatement apply(final XmlPropertyEntry entry) {
                return putMethod.call(
                        new ArrayList<AssignableStatement>() {{
                            add(quotedValue(entry.key()));
                            add(quotedValue(entry.value()));
                        }});
            }
        });
    }

    private static class XmlPropertyEntry extends AbstractElementWrapper {
        protected XmlPropertyEntry(Element beanNode, ApplicationContext applicationContext) {
            super(beanNode, applicationContext);
        }

        public String key() {
            return attributeValue("key");
        }

        public String value() {
            return element.getValue();
        }
    }
}
