package springclean.generate.initMethod;

import springclean.generate.data.SetterInjectedBean;

public class ApplicationContext {
    public final SetterInjectedBean aSetterInjectedBean;

    public ApplicationContext() throws Exception {
        aSetterInjectedBean = new SetterInjectedBean() {{
            start();
        }};
    }

}