package springclean.core.generate.propertyPlaceholders;

import springclean.core.generate.data.PropertyBean;

import java.util.Properties;

public class ApplicationContext {

    public final PropertyBean propertyBean;

    public ApplicationContext(final Properties properties) throws Exception {
        propertyBean = new PropertyBean(properties.getProperty("constructorStringProperty")) {{
            setSetterStringProperty(properties.getProperty("setterStringProperty"));
        }};
    }

}