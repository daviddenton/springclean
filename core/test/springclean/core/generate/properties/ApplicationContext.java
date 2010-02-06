package springclean.core.generate.properties;

import springclean.core.generate.data.CollectionsBean;

import java.util.Properties;

public class ApplicationContext {
    public final CollectionsBean collectionsBean;

    public ApplicationContext() throws Exception {
        collectionsBean = new CollectionsBean() {{
            setProperties(new Properties() {{
                put("key1", "value1");
                put("key2", "value2");
            }});
        }};
    }

}