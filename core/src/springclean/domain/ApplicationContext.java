package springclean.domain;

import java.util.Collection;

public interface ApplicationContext {
    ContextName name();

    Collection<IdentifiedBean> beans();
    Collection<IdentifiedBean> importedBeans();

    IdentifiedBean findBean(SpringId springId);
}
