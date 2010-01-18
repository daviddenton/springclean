package springclean.generate.staticFactoryMethod;

import springclean.generate.data.*;

public class ApplicationContext {
    public final NoDependencyBean noDependencyBean;

    public final StaticFactoryMethodBean aStaticFactoryMethodBean;

    public ApplicationContext() {
        noDependencyBean = new NoDependencyBean();
        aStaticFactoryMethodBean = StaticFactoryMethodBean.staticFactoryMethod(noDependencyBean);
    }

}