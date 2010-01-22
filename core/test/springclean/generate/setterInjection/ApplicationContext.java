package springclean.generate.setterInjection;

import springclean.generate.data.AnonymousBean;
import springclean.generate.data.NoDependencyBean;
import springclean.generate.data.SetterInjectedBean;

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