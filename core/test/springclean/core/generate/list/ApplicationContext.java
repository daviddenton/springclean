package springclean.core.generate.list;

import springclean.core.generate.data.NoDependencyBean;
import springclean.core.generate.data.SetterInjectedBean;

import java.util.ArrayList;

public class ApplicationContext {
    private final SetterInjectedBean setterInjectedBean;

    public ApplicationContext() throws Exception {
        setterInjectedBean = new SetterInjectedBean() {{
            setAList(new ArrayList() {{
                add(new NoDependencyBean());
                add("aString");
            }});
        }};
    }

}