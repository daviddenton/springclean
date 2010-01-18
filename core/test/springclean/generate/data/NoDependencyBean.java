package springclean.generate.data;

public class NoDependencyBean {
    public SetterInjectedBean factoryMethod(NoDependencyBean anotherNoDependencyBean) {
        return new SetterInjectedBean();
    }
}