package springclean.generate.prototypeScope;

import springclean.generate.data.AnonymousBean;
import springclean.generate.data.ConstructorInjectedBean;
import springclean.generate.data.NoDependencyBean;

public class ApplicationContext {
    public final ConstructorInjectedBean aConstructorInjectedBean;

    public ApplicationContext() throws Exception {
        aConstructorInjectedBean = new ConstructorInjectedBean(new NoDependencyBean(), new NoDependencyBean(), new AnonymousBean(), 1);
    }

}