package springclean.core.generate.simplestCase;

import springclean.core.generate.data.NoDependencyBean;

public class ApplicationContext {
    public final NoDependencyBean noDependencyBean;

    public ApplicationContext() throws Exception {
        noDependencyBean = new NoDependencyBean();
    }

}