package springclean.generate.factoryBean;

import springclean.generate.data.*;

public class ApplicationContext {
    public final NoDependencyBean noDependencyBean;

    public final SetterInjectedBean setterInjectedBean;

    public ApplicationContext() {
        noDependencyBean = new NoDependencyBean();
        setterInjectedBean = noDependencyBean.factoryMethod(noDependencyBean);
    }

}