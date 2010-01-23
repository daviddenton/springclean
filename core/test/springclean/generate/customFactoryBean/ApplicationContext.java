package springclean.generate.customFactoryBean;

import springclean.generate.data.NoDependencyBean;
import springclean.generate.data.SetterInjectedBean;

public class ApplicationContext {
    public final NoDependencyBean noDependencyBean;

    public final SetterInjectedBean setterInjectedBean;

    public ApplicationContext() throws Exception {
        noDependencyBean = new NoDependencyBean();
        setterInjectedBean = noDependencyBean.factoryMethod(noDependencyBean);
    }

}