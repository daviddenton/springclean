This library generates Java code from Spring XML configuration files, and thus adds the facility of being able to refactor you code!

Effectively inlines the XML into Java whilst retaining the use of the various Spring libraries. The plan is to add Annotation support as well in the future.

Note that the process is not perfect. After conversion, some work will be required to make a runnable application. Any issues are noted next to the features below.

###Supported Features

- Constructor Injection

- Setter Injection

- Abstract/parent beans

- Initialising Bean

- Factory Bean Issue: Currently cannot determine the type of the class created by the factory (due to this only being available at Spring runtime) and hence uses the class type of the Factory. Fix: Simply change the bean type (and cast on getObject()).

- Init Method

- Static Factory Method

- Destroy Method Issue: Code for automatically calling destroy methods is only generated for identified beans. Destroy methods for prototype-scoped and anonymous beans not are not called. Fix: Extract bean to a field and manually call destroy method inside generated stop() method

- DisposableBean?

- Prototype and Singleton Scopes

- Collections (lists, sets, maps, properties) Issue: Ues ungenerified collections, which will result in IDE warnings. Fix: Add generification parameters.

- Aliases and bean names

- Values Issue: Cannot always detect what type of object is required, especially when inside a collection. If in doubt, uses a String. Fix: Convert String values back into primitives as required

###Currently Unsupported

- depends-on

- multiple application contexts and classpath searching

- system properties and property config loaders ($XXX)

- proxies and pointcuts

- p: namespace

- idref bean

- lookup-method

- replaced-method

- default-init-method

- collection merging

- annotation based beans

- FieldRetrievingFactoryBean?

- lazy initialisation

###How to currently run SpringClean? over your Application Context files
At the moment, the SpringClean? main-able entrypoint class is not enabled - due to a combination of laziness and the API not being ready yet.

However, you can still run the cleaning process over your Spring XML to check out what you would get so far. The best way to do this is to integrate the SpringClean? libraries and dependencies (from the ZIP file of source tree) into your project and to then write a TestCase? that does the following:

```java
        ApplicationContext applicationContext = new XmlApplicationContext("mycrazyapplicationcontext.xml");
        System.out.println(new ApplicationContextClassBuilder(applicationContext.build());
```

This will give you a pretty well formatted Java source file for that particular context file. It will cope with imported contexts, which will render external beans as injected dependencies through the constructor.

Lather, rinse and repeat for your other context files and you should have a pretty good start on removing the XML.

Alternatively, check out the testcases in springclean/core/test/springclean/core/generate to get an idea of the input and output XML that you can expect.

