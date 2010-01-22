package springclean.generate.staticFactoryMethod;

import springclean.generate.data.NoDependencyBean;
import springclean.generate.data.StaticFactoryMethodBean;

public class ApplicationContext {
    public final NoDependencyBean noDependencyBean;

    public final StaticFactoryMethodBean aStaticFactoryMethodBean;

    public ApplicationContext() throws Exception {
        noDependencyBean = new NoDependencyBean();
        aStaticFactoryMethodBean = StaticFactoryMethodBean.staticFactoryMethod(noDependencyBean);
    }

}