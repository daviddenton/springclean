package springclean.core.generate.initMethod;

import springclean.core.generate.data.SetterInjectedBean;

public class ApplicationContext {
    public final SetterInjectedBean aSetterInjectedBean;

    public ApplicationContext() throws Exception {
        aSetterInjectedBean = new SetterInjectedBean() {{
            start();
        }};
    }

}