package springclean.core.generate.data;

public class DualInjectedBean {

    private final NoDependencyBean anotherNoDependencyBean;
    private final NoDependencyBean noDependencyBean;
    private AnonymousBean anonymousBean;

    public DualInjectedBean(NoDependencyBean anotherNoDependencyBean, NoDependencyBean noDependencyBean) {
        this.anotherNoDependencyBean = anotherNoDependencyBean;
        this.noDependencyBean = noDependencyBean;
    }

    public void setAnonymousBean(AnonymousBean anonymousBean) {
        this.anonymousBean = anonymousBean;
    }
}