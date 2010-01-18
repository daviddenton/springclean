package springclean.xml;

import static com.google.common.collect.Lists.newArrayList;
import nu.xom.*;
import org.daisychain.util.Functor;
import org.daisychain.util.SimpleFunctor;
import springclean.domain.ApplicationContext;
import springclean.domain.ContextName;
import static springclean.domain.ContextName.contextName;
import springclean.domain.IdentifiedBean;
import springclean.domain.SpringId;
import springclean.exception.Defect;
import static springclean.xml.XomUtils.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class XmlApplicationContext implements ApplicationContext {
    private final Document xmlDocument;
    private final File xmlFile;

    public XmlApplicationContext(File xmlFile) {
        this.xmlFile = xmlFile;
        xmlDocument = new Document(removeNamespaces(XomUtils.parse(xmlFile).getRootElement()));
    }

    public ContextName name() {
        return contextName(xmlFile);
    }

    public Collection<IdentifiedBean> beans() {
        final List<IdentifiedBean> identifiedBeans = newArrayList();
        loop(rootNode().query("bean"), new SimpleFunctor<Element>() {
            public void execute(Element node) {
                identifiedBeans.add(new XmlIdentifiedBean(node, XmlApplicationContext.this));
            }
        });
        return identifiedBeans;
    }

    public List<IdentifiedBean> importedBeans() {
        final List<IdentifiedBean> importedBeans = newArrayList();
        forAllImports(new SimpleFunctor<ApplicationContext>() {
            public void execute(ApplicationContext importedContext) {
                importedBeans.addAll(importedContext.importedBeans());
                importedBeans.addAll(importedContext.beans());
            }
        });
        return importedBeans;
    }

    public IdentifiedBean findBean(SpringId springId) {
        for (IdentifiedBean identifiedBean : allAvailableBeans()) {
            if(identifiedBean.id().equals(springId)) return identifiedBean;
        }
        throw new XomProcessingException("Can't find bean named " + springId);
    }

    private List<IdentifiedBean> allAvailableBeans() {
        ArrayList<IdentifiedBean> allBeansInScope = newArrayList();
        allBeansInScope.addAll(beans());
        allBeansInScope.addAll(importedBeans());
        return allBeansInScope;
    }

    private <E extends Throwable> void forAllImports(final Functor<ApplicationContext, E> importFunctor) throws E {
        loop(rootNode().query("import"), new Functor<Element, E>() {
            public void execute(Element target) throws E {
                importFunctor.execute(new XmlApplicationContext(new File(xmlFile.getParentFile(),  target.getAttributeValue("resource"))));
            }
        });
    }

    private Node rootNode() {
        final Nodes nodes = xmlDocument.query("/beans");
        if (nodes.size() != 1) {
            throw new XomProcessingException("Found "+nodes.size()+" <beans> elements in document");
        }
        return nodes.get(0);
    }

    private static Element removeNamespaces(Element element) throws Defect {
        final Element stripped = new Element(element.getLocalName());

        attributes(element, new SimpleFunctor<Attribute>() {
            public void execute(Attribute attribute) {
                stripped.addAttribute(new Attribute(attribute.getLocalName(), attribute.getValue()));
            }
        });

        children(element, new SimpleFunctor<Node>() {
            public void execute(Node n) {
                if (n instanceof Element) {
                    stripped.appendChild(removeNamespaces((Element) n));
                } else {
                    stripped.appendChild(n.copy());
                }
            }
        });

        return stripped;
    }
}
