package springclean.core.generate.implementsDisposableBean;

import springclean.client.Stoppable;
import springclean.core.generate.data.ADisposableBean;

public class ApplicationContext implements Stoppable {
    public final ADisposableBean aDisposableBean;

    public ApplicationContext() throws Exception {
        aDisposableBean = new ADisposableBean();
    }

    public void stop() throws Exception {
        aDisposableBean.destroy();
    }

}