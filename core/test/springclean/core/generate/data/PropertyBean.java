package springclean.core.generate.data;

public class PropertyBean {
    private final String constructorStringProperty;
    private String setterStringProperty;

    public PropertyBean(String constructorStringProperty) {
        this.constructorStringProperty = constructorStringProperty;
    }

    public void setSetterStringProperty(String setterStringProperty) {
        this.setterStringProperty = setterStringProperty;
    }
}