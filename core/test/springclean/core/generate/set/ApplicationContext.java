package springclean.core.generate.set;

import springclean.core.generate.data.NoDependencyBean;
import springclean.core.generate.data.SetterInjectedBean;

import java.util.HashSet;

public class ApplicationContext {
    private final SetterInjectedBean setterInjectedBean;

    public ApplicationContext() throws Exception {
        setterInjectedBean = new SetterInjectedBean() {{
            setASet(new HashSet() {{
                add(new NoDependencyBean());
                add("aString");
            }});
        }};
    }

}