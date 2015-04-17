package springclean.core.generate.parentBean;

import springclean.core.generate.data.client.Stoppable;
import springclean.core.generate.data.AnonymousBean;
import springclean.core.generate.data.DestroyableBean;
import springclean.core.generate.data.DualInjectedBean;
import springclean.core.generate.data.NoDependencyBean;

public class ApplicationContext implements Stoppable {
    public final AnonymousBean anAnonymousBean;

    public final NoDependencyBean noDependencyBean;

    public final NoDependencyBean overridingNoDependencyBean;

    public final DualInjectedBean aDualInjectedBean;

    public final DualInjectedBean anotherDualInjectedBean;

    public final DualInjectedBean yetAnotherDualInjectedBean;

    public final DestroyableBean destroyableBean;

    public ApplicationContext() throws Exception {
        anAnonymousBean = new AnonymousBean();
        noDependencyBean = new NoDependencyBean();
        overridingNoDependencyBean = new NoDependencyBean();
        aDualInjectedBean = new DualInjectedBean(noDependencyBean, noDependencyBean) {{
            setAnonymousBean(new AnonymousBean());
        }};
        anotherDualInjectedBean = new DualInjectedBean(new NoDependencyBean(), noDependencyBean) {{
            setAnonymousBean(new AnonymousBean());
            setAnonymousBean2(new AnonymousBean());
        }};
        yetAnotherDualInjectedBean = new DualInjectedBean(noDependencyBean, new NoDependencyBean());
        destroyableBean = new DestroyableBean();
    }

    public void stop() throws Exception {
        destroyableBean.destroyMethod();
    }

}