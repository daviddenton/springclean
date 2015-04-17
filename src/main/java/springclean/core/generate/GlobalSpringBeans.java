package springclean.core.generate;

import org.daisychain.source.Instance;

import java.util.Collections;
import java.util.Properties;
import java.util.Set;

public class GlobalSpringBeans {
    public static final Instance PROPERTIES_BEAN_INSTANCE = new Instance("properties", org.daisychain.source.ExistingClass.existingClass(Properties.class));

    public static Set<Instance> globalBeans() {
        return Collections.singleton(PROPERTIES_BEAN_INSTANCE);
    }
}
