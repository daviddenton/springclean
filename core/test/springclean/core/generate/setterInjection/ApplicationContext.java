package springclean.core.generate.setterInjection;

import springclean.core.generate.data.AnonymousBean;
import springclean.core.generate.data.NoDependencyBean;
import springclean.core.generate.data.SetterInjectedBean;

public class ApplicationContext {
    public final NoDependencyBean noDependencyBean;

    public final SetterInjectedBean aSetterInjectedBean;

    public ApplicationContext() throws Exception {
        noDependencyBean = new NoDependencyBean();
        aSetterInjectedBean = new SetterInjectedBean() {{
            setNoDependencyBean(noDependencyBean);
            setAnonymousBean(new AnonymousBean());
            setExpandedValue(1);
        }};
    }

}