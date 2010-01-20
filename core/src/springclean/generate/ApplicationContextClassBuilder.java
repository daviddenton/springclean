package springclean.generate;

import static com.google.common.collect.Sets.newHashSet;
import org.daisychain.source.*;
import static org.daisychain.source.DaisyChain.a;
import static org.daisychain.source.Modifier.Public;
import static org.daisychain.source.Modifier.publicFinal;
import springclean.client.Stoppable;
import springclean.domain.ApplicationContext;
import springclean.domain.IdentifiedBean;

import java.util.Set;


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
            if (target.hasDestroyMethod()) {
                final Arguments arguments = new Arguments();
                method.addStatement(new Instance(target.id().value, target.clazz()).call(target.destroyMethod().name(), arguments.asStatements()));
                classBuilder.implementing(new ExistingClass(Stoppable.class));
                contextClass = classBuilder.build();
                contextClass.addMethod(method);
                break;
            }
        }

        contextClass.addConstructor(new ApplicationContextConstructorBuilder(applicationContext, contextClass).build());

        return contextClass;
    }

    private ExistingMethod stopMethod() {
        return new MethodFinder<ExistingMethod>(new ExistingClass(Stoppable.class)).method("stop", 0);
    }

    private static class ApplicationContextConstructorBuilder {
        private final ApplicationContext applicationContext;
        private final MutableClass contextClass;

        public GeneratedConstructor constructor;

        public ApplicationContextConstructorBuilder(ApplicationContext applicationContext, MutableClass contextClass) {
            this.applicationContext = applicationContext;
            this.contextClass = contextClass;
        }

        public Constructor build() {
            final Set<Instance> processedBeans = newHashSet();
            final Set<Instance> constructorParameters = newHashSet();
            final Set<Instance> externalDependencies = externallyDefinedDependencies(applicationContext);
            final GeneratedConstructor constructor = a(Public).constructor(contextClass).build();
            while (true) {
                try {
                    for (IdentifiedBean target : applicationContext.beans()) {
                        Instance instance = new Instance(target.id().value, target.clazz());
                        ContextElement candidate = target.asContextElement(target.clazz());
                        if (!processedBeans.contains(instance)) {

                            for (Instance dependency : candidate.dependencies()) {
                                if (externalDependencies.contains(dependency))
                                    constructorParameters.add(dependency);
                                else if (!processedBeans.contains(dependency)) throw new UnresolvedDependency();
                            }

                            contextClass.addField(a(publicFinal).generatedField(instance).build());
                            constructor.addStatement(candidate.asStatement());
                            processedBeans.add(instance);
                        }
                    }

                    for (Instance constructorParameter : constructorParameters) {
                        constructor.addParameter(new Parameter(Parameter.ParameterModifier.Final, constructorParameter));
                    }
                    break;
                } catch (UnresolvedDependency e) {
                }
            }
            return constructor;
        }

        private Set<Instance> externallyDefinedDependencies(ApplicationContext applicationContext) {
            final Set<Instance> dependencies = newHashSet();
            for (IdentifiedBean target : applicationContext.importedBeans()) {
                dependencies.add(new Instance(target.id().value, target.clazz()));

            }
            return dependencies;
        }

        private class UnresolvedDependency extends Exception {
        }

    }

}
