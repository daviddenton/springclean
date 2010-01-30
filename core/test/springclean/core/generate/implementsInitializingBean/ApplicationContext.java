package springclean.core.generate.implementsInitializingBean;

import springclean.core.generate.data.AnInitializingBean;
import springclean.core.generate.data.NoDependencyBean;

public class ApplicationContext {
    public final AnInitializingBean anInitializingBean;

    public ApplicationContext() throws Exception {
        anInitializingBean = new AnInitializingBean(new NoDependencyBean()) {{
            setAnotherNoDependencyBean(new NoDependencyBean());
            afterPropertiesSet();
        }};
    }

}