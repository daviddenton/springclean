package springclean.core.generate.importedBeans;

import springclean.core.generate.data.AnonymousBean;
import springclean.core.generate.data.ConstructorInjectedBean;
import springclean.core.generate.data.NoDependencyBean;

public class ApplicationContext {
    public final NoDependencyBean anotherNoDependencyBean;

    public final ConstructorInjectedBean aConstructorInjectedBean;

    public ApplicationContext(final NoDependencyBean noDependencyBean) throws Exception {
        anotherNoDependencyBean = new NoDependencyBean();
        aConstructorInjectedBean = new ConstructorInjectedBean(anotherNoDependencyBean, noDependencyBean, new AnonymousBean(), 1);
    }

}