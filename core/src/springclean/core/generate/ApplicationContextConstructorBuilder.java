package springclean.core.generate;

import static com.google.common.collect.Sets.newHashSet;
import org.daisychain.source.*;
import static org.daisychain.source.DaisyChain.a;
import static org.daisychain.source.Modifier.Public;
import static org.daisychain.source.Modifier.publicFinal;
import static org.daisychain.source.Parameter.ParameterModifier.Final;
import springclean.core.domain.ApplicationContext;
import springclean.core.domain.IdentifiedBean;

import java.util.Set;

public class ApplicationContextConstructorBuilder {
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
        constructor.addException(new ExistingClass(Exception.class));

        while (true) {
            try {
                for (IdentifiedBean target : applicationContext.beans()) {
                    Instance instance = new Instance(target.id().value, target.clazz());
                    if (target.isAbstract()) processedBeans.add(instance);

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
                    constructor.addParameter(new Parameter(Final, constructorParameter));
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
