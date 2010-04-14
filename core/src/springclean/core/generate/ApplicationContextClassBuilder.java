package springclean.core.generate;

import org.daisychain.source.*;
import static org.daisychain.source.DaisyChain.a;
import static org.daisychain.source.ExistingClass.existingClass;
import static org.daisychain.source.Modifier.Public;
import springclean.client.Stoppable;
import springclean.core.domain.ApplicationContext;
import springclean.core.domain.IdentifiedBean;


public class ApplicationContextClassBuilder {

    private final ApplicationContext applicationContext;

    public ApplicationContextClassBuilder(ApplicationContext applicationContext) throws Exception {
        this.applicationContext = applicationContext;
    }

    public MutableClass build() {
        DaisyChain.DcGeneratedClassBuilder classBuilder = a(Public)
                .generatedClass(applicationContext.name().asJavaClassName());

        final ClassMethod method = stopMethod().override();
        GeneratedClass contextClass = classBuilder.build();
        for (IdentifiedBean target : applicationContext.beans()) {
            if (!target.isAbstract() && target.hasDestroyMethod()) {
                final Arguments arguments = new Arguments();
                method.addStatement(new Instance(target.id().value, target.declaredBeanClass()).call(target.destroyMethod().name(), arguments.asStatements()));
                classBuilder.implementing(existingClass(Stoppable.class));
                contextClass = classBuilder.build();
                contextClass.addMethod(method);
                break;
            }
        }

        contextClass.addConstructor(new ApplicationContextConstructorBuilder(applicationContext, contextClass).build());

        return contextClass;
    }

    private ExistingMethod stopMethod() {
        return new MethodFinder<ExistingMethod>(existingClass(Stoppable.class)).method("stop", 0);
    }

}
