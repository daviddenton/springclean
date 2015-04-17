package springclean.core.generate.factoryBean;

import springclean.core.generate.data.NoDependencyBean;
import springclean.core.generate.data.SetterInjectedBean;

public class ApplicationContext {
    public final NoDependencyBean noDependencyBean;

    public final SetterInjectedBean setterInjectedBean;

    public ApplicationContext() throws Exception {
        noDependencyBean = new NoDependencyBean();
        setterInjectedBean = noDependencyBean.factoryMethod(noDependencyBean);
    }

}