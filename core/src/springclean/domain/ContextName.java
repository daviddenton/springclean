package springclean.domain;

import microtypes.FileValue;

import java.io.File;

public class ContextName extends FileValue {

    private ContextName(File file) {
        super(file);
    }

    public String asJavaClassName() {
        return javaize(value.getName());
    }

    private String javaize(String name) {
        return name.replaceAll(".xml", "").replaceFirst(".", name.substring(0, 1).toUpperCase());
    }

    public String asPackage() {
        return javaize(value.getParentFile().getPath());
    }

    public static ContextName contextName(File contextFile) {
        return new ContextName(contextFile);
    }
}
