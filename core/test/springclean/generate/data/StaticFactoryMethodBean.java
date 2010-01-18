package springclean.generate.data;

public class StaticFactoryMethodBean {
    private static NoDependencyBean noDependencyBean;

    public static StaticFactoryMethodBean staticFactoryMethod(NoDependencyBean noDependencyBean) {
        StaticFactoryMethodBean.noDependencyBean = noDependencyBean;
        return new StaticFactoryMethodBean();
    }

}