package springclean.core.xml;

import org.daisychain.source.AClass;
import org.daisychain.source.ExistingMethod;
import springclean.core.domain.ApplicationContext;
import springclean.core.domain.Reference;
import springclean.core.domain.SpringId;
import springclean.core.generate.ConstructionStrategy;
import springclean.core.generate.RefConstructionStrategy;

public class InlineXmlReference implements Reference {
    private final SpringId id;
    private final ApplicationContext applicationContext;

    public InlineXmlReference(SpringId id, ApplicationContext applicationContext) {
        this.id = id;
        this.applicationContext = applicationContext;
    }

    public SpringId id() {
        return id;
    }

    public ConstructionStrategy asConstructionStrategy(AClass aClass) {
        return new RefConstructionStrategy(this, aClass);
    }

    public AClass<ExistingMethod> clazz() {
        return applicationContext.findBean(id).clazz();
    }
}
