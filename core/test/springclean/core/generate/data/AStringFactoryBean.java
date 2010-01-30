package springclean.core.generate.data;

import org.springframework.beans.factory.FactoryBean;

public class AStringFactoryBean implements FactoryBean {

    private NoDependencyBean noDependencyBean;

    public void setNoDependencyBean(NoDependencyBean noDependencyBean) {
        this.noDependencyBean = noDependencyBean;
    }

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