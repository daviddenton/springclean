package springclean.core.generate;

import org.daisychain.source.AClass;
import org.daisychain.source.body.AssignableStatement;
import org.daisychain.source.util.IndentingStringWriter;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

class EnumConstant implements AssignableStatement {
    private final AClass enumClass;
    private final String name;

    public EnumConstant(AClass enumClass, String name) {
        this.enumClass = enumClass;
        this.name = name;
    }

    public Set<AClass> getImports() {
        return Collections.singleton(enumClass);
    }

    public void appendSource(IndentingStringWriter writer) throws IOException {
        writer.append(enumClass.simpleName() + "." + name);
    }
}
