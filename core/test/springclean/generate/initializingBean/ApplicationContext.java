package springclean.generate.initializingBean;

import springclean.generate.data.AnInitializingBean;

public class ApplicationContext {
    public final AnInitializingBean anInitializingBean;

    public ApplicationContext() throws Exception {
        anInitializingBean = new AnInitializingBean() {{
            afterPropertiesSet();
        }};
    }

}