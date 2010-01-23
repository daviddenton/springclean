package springclean.generate.data;

import org.springframework.beans.factory.FactoryBean;

public class AStringFactoryBean implements FactoryBean {

    public Object getObject() throws Exception {
        return "aString";
    }

    public Class getObjectType() {
        return String.class;
    }

    public boolean isSingleton() {
        return true;
    }
}