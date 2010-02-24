package springclean.core.generate;

import org.daisychain.source.Instance;
import org.daisychain.source.body.AssignableStatement;
import static org.daisychain.source.body.Value.quotedValue;

import java.util.Collections;
import static java.util.Collections.singletonList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PropertyPlaceholderConstructionStrategy implements ConstructionStrategy {

    public static final String PROPERTY_PLACEHOLDER_REGEX = "\\$\\{(.*)\\}";

    private final String propertyName;

    public PropertyPlaceholderConstructionStrategy(String propertyName) {
        this.propertyName = propertyName;
    }

    public Set<Instance> dependencies() {
        return Collections.singleton(GlobalSpringBeans.PROPERTIES_BEAN_INSTANCE);
    }

    public AssignableStatement asStatement() {
        return GlobalSpringBeans.PROPERTIES_BEAN_INSTANCE.call("getProperty", propertyName());
    }

    private List<AssignableStatement> propertyName() {
        final Pattern pattern = Pattern.compile(PROPERTY_PLACEHOLDER_REGEX);
        final Matcher matcher = pattern.matcher(propertyName);
        matcher.matches();
        return singletonList((AssignableStatement) quotedValue(matcher.group(1)));
    }
}