package springclean.core.generate.staticFactoryMethod;

import springclean.core.generate.data.NoDependencyBean;
import springclean.core.generate.data.StaticFactoryMethodBean;

public class ApplicationContext {
    public final NoDependencyBean noDependencyBean;

    public final StaticFactoryMethodBean aStaticFactoryMethodBean;

    public ApplicationContext() throws Exception {
        noDependencyBean = new NoDependencyBean();
        aStaticFactoryMethodBean = StaticFactoryMethodBean.staticFactoryMethod(noDependencyBean);
    }

}