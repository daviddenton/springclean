package springclean.generate.implementsFactoryBean;

import springclean.generate.data.AStringFactoryBean;

public class ApplicationContext {
    public final String aString;

    public ApplicationContext() throws Exception {
        aString = (String) new AStringFactoryBean().getObject();
    }

}