package springclean.core.generate.list;

import springclean.core.generate.data.NoDependencyBean;
import springclean.core.generate.data.SetterInjectedBean;

import java.util.ArrayList;

public class ApplicationContext {
    public final NoDependencyBean aNoDependencyBean;

    public final SetterInjectedBean setterInjectedBean;

    public ApplicationContext() throws Exception {
        aNoDependencyBean = new NoDependencyBean();
        setterInjectedBean = new SetterInjectedBean() {{
            setAList(new ArrayList() {{
                add(new NoDependencyBean());
                add(aNoDependencyBean);
                add("aString");
            }});
        }};
    }

}