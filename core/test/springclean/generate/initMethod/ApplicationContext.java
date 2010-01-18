package springclean.generate.initMethod;

import springclean.generate.data.SetterInjectedBean;
import springclean.Stoppable;

public class ApplicationContext {
    public final SetterInjectedBean aSetterInjectedBean;

    public ApplicationContext() {
        aSetterInjectedBean = new SetterInjectedBean() {{
            start();
        }};
    }
    
}