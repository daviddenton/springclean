package springclean.core.generate.implementsFactoryBean;

import springclean.core.generate.data.AStringFactoryBean;
import springclean.core.generate.data.NoDependencyBean;

public class ApplicationContext {
    public final NoDependencyBean noDependencyBean;

    public final String aString;

    public ApplicationContext() throws Exception {
        noDependencyBean = new NoDependencyBean();
        aString = (String) new AStringFactoryBean() {{
            setNoDependencyBean(noDependencyBean);
        }}.getObject();
    }

}