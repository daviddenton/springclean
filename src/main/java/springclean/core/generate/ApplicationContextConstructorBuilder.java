package springclean.core.generate;

import org.daisychain.source.*;
import springclean.core.domain.ApplicationContext;
import springclean.core.domain.IdentifiedBean;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static org.daisychain.source.DaisyChain.a;
import static org.daisychain.source.ExistingClass.existingClass;
import static org.daisychain.source.Modifier.Public;
import static org.daisychain.source.Modifier.publicFinal;
import static org.daisychain.source.Parameter.ParameterModifier.Final;

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
        constructor.addException(existingClass(Exception.class));

        while (true) {
            try {
                for (IdentifiedBean target : applicationContext.beans()) {
                    Instance instance = new Instance(target.id().value, target.constructedBeanClass());
                    if (target.isAbstract()) processedBeans.add(instance);

                    ConstructionStrategy candidate = target.asConstructionStrategy(target.declaredBeanClass());
                    if (!processedBeans.contains(instance)) {

                        for (Instance dependency : candidate.dependencies()) {
                            if (externalDependencies.contains(dependency)) {
                                constructorParameters.add(dependency);
                            } else if (!processedBeans.contains(dependency)) {
                                throw new UnresolvedDependency(dependency);
                            }
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
                System.out.println(e.getMessage());
            }
        }
        return constructor;
    }

    private Set<Instance> externallyDefinedDependencies(ApplicationContext applicationContext) {
        final Set<Instance> dependencies = newHashSet();
        dependencies.addAll(GlobalSpringBeans.globalBeans());
        for (IdentifiedBean target : applicationContext.importedBeans()) {
            dependencies.add(new Instance(target.id().value, target.declaredBeanClass()));

        }
        return dependencies;
    }

    private class UnresolvedDependency extends Exception {
        public UnresolvedDependency(Instance instance) {
            super("can't find " + instance.reference.name);
        }
    }

}
