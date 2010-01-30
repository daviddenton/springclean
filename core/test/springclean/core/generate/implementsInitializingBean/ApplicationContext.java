package springclean.core.generate.implementsInitializingBean;

import springclean.core.generate.data.AnInitializingBean;

public class ApplicationContext {
    public final AnInitializingBean anInitializingBean;

    public ApplicationContext() throws Exception {
        anInitializingBean = new AnInitializingBean() {{
            afterPropertiesSet();
        }};
    }

}