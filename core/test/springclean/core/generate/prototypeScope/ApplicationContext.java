package springclean.core.generate.prototypeScope;

import springclean.core.generate.data.AnonymousBean;
import springclean.core.generate.data.ConstructorInjectedBean;
import springclean.core.generate.data.NoDependencyBean;

public class ApplicationContext {
    public final ConstructorInjectedBean aConstructorInjectedBean;

    public ApplicationContext() throws Exception {
        aConstructorInjectedBean = new ConstructorInjectedBean(new NoDependencyBean(), new NoDependencyBean(), new AnonymousBean(), 1);
    }

}