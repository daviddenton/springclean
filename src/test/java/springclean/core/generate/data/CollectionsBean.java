package springclean.core.generate.data;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class CollectionsBean {
    private List list;
    private Set set;
    private Map map;
    private Properties properties;

    public void setList(List list) {
        this.list = list;
    }

    public void setSet(Set set) {
        this.set = set;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
