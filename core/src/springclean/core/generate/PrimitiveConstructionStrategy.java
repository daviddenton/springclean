package springclean.core.generate;

import org.apache.commons.lang.builder.ToStringBuilder;
import static org.apache.commons.lang.builder.ToStringStyle.SHORT_PREFIX_STYLE;
import org.daisychain.source.AClass;
import org.daisychain.source.ExistingClass;
import static org.daisychain.source.ExistingClass.existingClass;
import org.daisychain.source.Instance;
import org.daisychain.source.body.AssignableStatement;
import static org.daisychain.source.body.Value.quotedValue;
import static org.daisychain.source.body.Value.value;
import springclean.core.exception.Defect;

import java.util.Collections;
import java.util.Set;


public class PrimitiveConstructionStrategy implements ConstructionStrategy {
    private static final ExistingClass OBJECT_CLASS = existingClass(Object.class);
    private static final ExistingClass STRING_CLASS = existingClass(String.class);

    private final String content;
    private final AClass type;

    public PrimitiveConstructionStrategy(String content, AClass type) {
        this.content = content;
        this.type = type;
    }

    public Set<Instance> dependencies() {
        return Collections.EMPTY_SET;
    }

    public AssignableStatement asStatement() {
        if (type.isPrimitive()) {
            return value(content);
        } else if (Class.class.getName().equals(type.name())) {
            return value(content);
        } else if (Enum.class.getName().equals(type.name())) {
            return new EnumConstant(type, content);
        } else if (type.equals(STRING_CLASS) || type.equals(OBJECT_CLASS)) {
            return quotedValue(asStringLiteral(content));
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
