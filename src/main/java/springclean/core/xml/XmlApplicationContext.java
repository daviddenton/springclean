package springclean.core.xml;

import com.google.common.base.Function;
import nu.xom.*;
import springclean.core.domain.*;
import springclean.core.exception.Defect;
import springclean.core.util.Functor;
import springclean.core.util.SimpleFunctor;

import java.io.File;
import java.util.Collection;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static springclean.core.domain.ContextName.contextName;
import static springclean.core.xml.XomUtils.transform;

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
        return transform(rootNode().query("bean"), new Function<Element, IdentifiedBean>() {
            public IdentifiedBean apply(Element element) {
                return new XmlIdentifiedBean(element, XmlApplicationContext.this);
            }
        });
    }

    public Collection<Alias> aliases() {
        return transform(rootNode().query("alias"), new Function<Element, Alias>() {
            public Alias apply(Element element) {
                return new XmlAlias(element, XmlApplicationContext.this);
            }
        });
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
            if (identifiedBean.isKnownAs(springId)) return identifiedBean;
        }
        for (Alias alias : aliases()) {
            if (alias.id().equals(springId)) return alias.bean();
        }
        throw new XomProcessingException("Can't find bean named " + springId);
    }

    private List<IdentifiedBean> allAvailableBeans() {
        List<IdentifiedBean> allBeansInScope = newArrayList(beans());
        allBeansInScope.addAll(importedBeans());
        return allBeansInScope;
    }

    private <E extends Throwable> void forAllImports(final Functor<ApplicationContext, E> importFunctor) throws E {
        Nodes anImport = rootNode().query("import");
        for (int i = 0; i < anImport.size(); i++) {
            importFunctor.execute(new XmlApplicationContext(new File(xmlFile.getParentFile(), ((Element)anImport.get(i)).getAttributeValue("resource"))));
        }
    }

    private Node rootNode() {
        final Nodes nodes = xmlDocument.query("/beans");
        if (nodes.size() != 1) {
            throw new XomProcessingException("Found " + nodes.size() + " <beans> elements in document");
        }
        return nodes.get(0);
    }

    private static Element removeNamespaces(Element element) throws Defect {
        final Element stripped = new Element(element.getLocalName());

        XomUtils.attributes(element, new SimpleFunctor<Attribute>() {
            public void execute(Attribute attribute) {
                stripped.addAttribute(new Attribute(attribute.getLocalName(), attribute.getValue()));
            }
        });

        XomUtils.children(element, new SimpleFunctor<Node>() {
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
