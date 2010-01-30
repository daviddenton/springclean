package springclean.core.generate.data;

import org.springframework.beans.factory.InitializingBean;

public class AnInitializingBean implements InitializingBean {

    private final NoDependencyBean noDependencyBean;
    private NoDependencyBean anotherNoDependencyBean;

    public AnInitializingBean(NoDependencyBean noDependencyBean) {
        this.noDependencyBean = noDependencyBean;
    }

    public void setAnotherNoDependencyBean(NoDependencyBean anotherNoDependencyBean) {
        this.anotherNoDependencyBean = anotherNoDependencyBean;
    }

    public void afterPropertiesSet() throws Exception {

    }
}
