package springclean.core.generate.map;

import springclean.core.generate.data.CollectionsBean;
import springclean.core.generate.data.NoDependencyBean;

import java.util.HashMap;

public class ApplicationContext {
    public final NoDependencyBean aNoDependencyBean;

    public final CollectionsBean collectionsBean;

    public ApplicationContext() throws Exception {
        aNoDependencyBean = new NoDependencyBean();
        collectionsBean = new CollectionsBean() {{
            setMap(new HashMap() {{
                put("inlineValue", "anInlineValue");
                put(aNoDependencyBean, aNoDependencyBean);
                put(aNoDependencyBean, new NoDependencyBean());
                put(new NoDependencyBean(), aNoDependencyBean);
                put("expandedValue", "expandedValue");
            }});
        }};
    }

}