package springclean.core.generate;

import org.apache.commons.lang.builder.ToStringBuilder;
import static org.apache.commons.lang.builder.ToStringStyle.SHORT_PREFIX_STYLE;
import org.daisychain.source.AClass;
import static org.daisychain.source.ExistingClass.existingClass;
import org.daisychain.source.Instance;
import org.daisychain.source.body.AssignableStatement;
import static org.daisychain.source.body.Value.quotedValue;
import static org.daisychain.source.body.Value.value;
import org.daisychain.source.util.IndentingStringWriter;
import springclean.core.exception.Defect;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;


public class PrimitiveConstructionStrategy implements ConstructionStrategy {
    private final String content;
    private final AClass type;

    public PrimitiveConstructionStrategy(String content, AClass type) {
        this.content = (!existingClass(String.class, (List<? extends AClass>) Collections.EMPTY_LIST).equals(type) && "".equals(content)) ? null : content;
        this.type = type;
    }

    public Set<Instance> dependencies() {
        return Collections.EMPTY_SET;
    }

    public AssignableStatement asStatement() {
        if (type.equals(existingClass(String.class, Collections.EMPTY_LIST))) {
            return quotedValue(asStringLiteral(content));
        } else if (type.isPrimitive()) {
            return value(content);
        } else if (Class.class.getName().equals(type.name())) {
            return value(content);
        } else if (Enum.class.getName().equals(type.name())) {
            return new AssignableStatement() {
                public Set<AClass> getImports() {
                    return Collections.singleton(type);
                }

                public void appendSource(IndentingStringWriter writer) throws IOException {
                    writer.append(type.simpleName() + "." + content);
                }
            };
        } else {
            throw new Defect("Don't know what to do with primitive type " + type + " (for value '" + content + "')");
        }
    }

    private String asStringLiteral(String stuff) {
        return stuff.replace("\\", "\\\\").replace("\n", "\\n").replace("\t", "\\t").replace("\"", "\\\"");
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, SHORT_PREFIX_STYLE);
    }

}
