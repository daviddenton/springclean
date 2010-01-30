package springclean.core.generate.constructorInjection;

import springclean.core.generate.data.AnonymousBean;
import springclean.core.generate.data.ConstructorInjectedBean;
import springclean.core.generate.data.NoDependencyBean;

public class ApplicationContext {
    public final NoDependencyBean noDependencyBean;

    public final NoDependencyBean anotherNoDependencyBean;

    public final ConstructorInjectedBean aConstructorInjectedBean;

    public ApplicationContext() throws Exception {
        noDependencyBean = new NoDependencyBean();
        anotherNoDependencyBean = new NoDependencyBean();
        aConstructorInjectedBean = new ConstructorInjectedBean(anotherNoDependencyBean, noDependencyBean, new AnonymousBean(), 1);
    }

}
