package springclean.generate.importedBeans;

import springclean.generate.data.AnonymousBean;
import springclean.generate.data.ConstructorInjectedBean;
import springclean.generate.data.NoDependencyBean;

public class ApplicationContext {
    public final NoDependencyBean anotherNoDependencyBean;

    public final ConstructorInjectedBean aConstructorInjectedBean;

    public ApplicationContext(final NoDependencyBean noDependencyBean) throws Exception {
        anotherNoDependencyBean = new NoDependencyBean();
        aConstructorInjectedBean = new ConstructorInjectedBean(anotherNoDependencyBean, noDependencyBean, new AnonymousBean(), 1);
    }

}