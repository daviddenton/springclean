package springclean.generate.implementsFactoryBean;

import springclean.generate.data.AStringFactoryBean;
import springclean.generate.data.NoDependencyBean;

public class ApplicationContext {
    public final NoDependencyBean noDependencyBean;

    public final AStringFactoryBean aString;

    public ApplicationContext() throws Exception {
        noDependencyBean = new NoDependencyBean();
        aString = (AStringFactoryBean) new AStringFactoryBean() {{
            setNoDependencyBean(noDependencyBean);
        }}.getObject();
    }

}