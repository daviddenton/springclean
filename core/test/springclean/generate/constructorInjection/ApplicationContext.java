package springclean.generate.constructorInjection;

import springclean.generate.data.AnonymousBean;
import springclean.generate.data.ConstructorInjectedBean;
import springclean.generate.data.NoDependencyBean;
import springclean.Stoppable;

public class ApplicationContext {
    public final NoDependencyBean noDependencyBean;

    public final NoDependencyBean anotherNoDependencyBean;

    public final ConstructorInjectedBean aConstructorInjectedBean;

    public ApplicationContext() {
        noDependencyBean = new NoDependencyBean();
        anotherNoDependencyBean = new NoDependencyBean();
        aConstructorInjectedBean = new ConstructorInjectedBean(anotherNoDependencyBean, noDependencyBean, new AnonymousBean(), 1);
    }

}
