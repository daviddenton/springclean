package springclean.generate.data;

public class ConstructorInjectedBean {

    private final NoDependencyBean anotherNoDependencyBean;
    private final NoDependencyBean noDependencyBean;
    private final AnonymousBean anonymousBean;
    private final int i;

    public ConstructorInjectedBean(NoDependencyBean anotherNoDependencyBean, NoDependencyBean noDependencyBean, AnonymousBean anonymousBean, int i) {
        this.anotherNoDependencyBean = anotherNoDependencyBean;
        this.noDependencyBean = noDependencyBean;
        this.anonymousBean = anonymousBean;
        this.i = i;
    }
}