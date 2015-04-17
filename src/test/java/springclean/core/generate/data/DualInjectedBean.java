package springclean.core.generate.data;

public class DualInjectedBean {

    private final NoDependencyBean anotherNoDependencyBean;
    private final NoDependencyBean noDependencyBean;
    private AnonymousBean anonymousBean;
    private AnonymousBean anonymousBean2;
    private AnonymousBean anonymousBean3;

    public DualInjectedBean(NoDependencyBean anotherNoDependencyBean, NoDependencyBean noDependencyBean) {
        this.anotherNoDependencyBean = anotherNoDependencyBean;
        this.noDependencyBean = noDependencyBean;
    }

    public void setAnonymousBean(AnonymousBean anonymousBean) {
        this.anonymousBean = anonymousBean;
    }

    public void setAnonymousBean2(AnonymousBean anonymousBean) {
        this.anonymousBean = anonymousBean;
    }

    public void setAnonymousBean3(AnonymousBean anonymousBean3) {
        this.anonymousBean3 = anonymousBean3;
    }
}