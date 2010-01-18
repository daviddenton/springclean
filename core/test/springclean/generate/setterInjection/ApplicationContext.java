package springclean.generate.setterInjection;

import springclean.generate.data.AnonymousBean;
import springclean.generate.data.NoDependencyBean;
import springclean.generate.data.SetterInjectedBean;
import springclean.Stoppable;

public class ApplicationContext {
    public final NoDependencyBean noDependencyBean;

    public final SetterInjectedBean aSetterInjectedBean;

    public ApplicationContext() {
        noDependencyBean = new NoDependencyBean();
        aSetterInjectedBean = new SetterInjectedBean() {{
            setNoDependencyBean(noDependencyBean);
            setAnonymousBean(new AnonymousBean());
            setExpandedValue(1);
        }};
    }

}