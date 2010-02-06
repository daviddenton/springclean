package springclean.core.domain;

import static com.google.common.collect.Lists.newArrayList;
import static springclean.core.domain.ContextName.contextName;
import springclean.core.xml.XomProcessingException;

import java.io.File;
import java.util.Collection;
import java.util.List;

public class ApplicationContextBuilder {
    private ContextName name = contextName(new File("aContextName.xml"));
    private List<Alias> aliases = newArrayList();
    private List<IdentifiedBean> identifiedBeans = newArrayList();
    private List<IdentifiedBean> importedBeans = newArrayList();

    private ApplicationContextBuilder() {
    }

    public static ApplicationContextBuilder anApplicationContext() {
        return new ApplicationContextBuilder();
    }

    public ApplicationContextBuilder withName(ContextName name) {
        this.name = name;
        return this;
    }

    public ApplicationContextBuilder withBean(IdentifiedBean identifiedBean) {
        this.identifiedBeans.add(identifiedBean);
        return this;
    }

    public ApplicationContextBuilder withAlias(Alias alias) {
        this.aliases.add(alias);
        return this;
    }

    public ApplicationContextBuilder withImportedBean(IdentifiedBean importedBean) {
        this.importedBeans.add(importedBean);
        return this;
    }

    public ApplicationContext build() {
        return new ApplicationContext() {
            public ContextName name() {
                return name;
            }

            public Collection<IdentifiedBean> beans() {
                return identifiedBeans;
            }

            public Collection<Alias> aliases() {
                return aliases;
            }

            public Collection<IdentifiedBean> importedBeans() {
                return importedBeans;
            }

            public IdentifiedBean findBean(SpringId springId) {
                for (IdentifiedBean identifiedBean : identifiedBeans) {
                    if (identifiedBean.isKnownAs(springId)) return identifiedBean;
                }
                throw new XomProcessingException("Can't find bean named " + springId);
            }
        };
    }
}
