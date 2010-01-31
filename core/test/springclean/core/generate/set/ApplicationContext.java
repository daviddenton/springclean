package springclean.core.generate.set;

import springclean.core.generate.data.NoDependencyBean;
import springclean.core.generate.data.SetterInjectedBean;

import java.util.HashSet;

public class ApplicationContext {
    public final NoDependencyBean aNoDependencyBean;

    public final SetterInjectedBean setterInjectedBean;

    public ApplicationContext() throws Exception {
        aNoDependencyBean = new NoDependencyBean();
        setterInjectedBean = new SetterInjectedBean() {{
            setASet(new HashSet() {{
                add(new NoDependencyBean());
                add(aNoDependencyBean);
                add("aString");
            }});
        }};
    }

}