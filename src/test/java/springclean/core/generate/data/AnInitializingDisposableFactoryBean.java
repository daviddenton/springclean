package springclean.core.generate.data;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class AnInitializingDisposableFactoryBean implements InitializingBean, DisposableBean {
    public void destroy() throws Exception {

    }

    public void afterPropertiesSet() throws Exception {

    }
}