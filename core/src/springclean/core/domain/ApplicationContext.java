package springclean.core.domain;

import java.util.Collection;

public interface ApplicationContext {
    ContextName name();

    Collection<IdentifiedBean> beans();

    Collection<Alias> aliases();

    Collection<IdentifiedBean> importedBeans();

    IdentifiedBean findBean(SpringId springId);
}
