# Jtwig Hot Reloading Extension

**How to use?**

- Include the dependency in your project (check [bintray](https://bintray.com/jtwig/maven/jtwig-hot-reloading-extension/_latestVersion)).
- Add to your custom Jtwig configuration

```java
EnvironmentConfiguration configuration = EnvironmentConfigurationBuilder
    .configuration()
        .extensions()
            .add(new HotReloadingExtension(TimeUnit.SECONDS, 1))
        .and()
    .build();
```

**How it works?**

- If the referenced resource is a file, it then checks for the last modified date and depending on such date, this may evict the cached template.


**Integration with Spring Boot**

Note that, jtwig-spring-boot-started module, by default, sets the template source as the classpath, bundling such application inside a standalone jar makes it impossible to edit such template files. 
For this to work, an easy solution can be to use templates stored in a specific directory, as shown in the example below:

```java
@Configuration
public class JtwigConfig implements JtwigViewResolverConfigurer {
    @Override
    public void configure(JtwigViewResolver viewResolver) {
        viewResolver.setPrefix("file:/path/to/base/directory");
        viewResolver.setRenderer(new JtwigRenderer(EnvironmentConfigurationBuilder
                .configuration()
                .extensions().add(new HotReloadingExtension(TimeUnit.SECONDS, 1)).and()
                .build()));
    }
}
```

**Build Stats**

[![Build Status](https://travis-ci.org/jtwig/jtwig-hot-reloading-extension.svg?branch=master)](https://travis-ci.org/jtwig/jtwig-hot-reloading-extension)
[![Coverage Status](https://coveralls.io/repos/github/jtwig/jtwig-hot-reloading-extension/badge.svg?branch=master)](https://coveralls.io/github/jtwig/jtwig-hot-reloading-extension?branch=master)
[![Download](https://api.bintray.com/packages/jtwig/maven/jtwig-hot-reloading-extension/images/download.svg) ](https://bintray.com/jtwig/maven/jtwig-hot-reloading-extension/_latestVersion)

**Licensing**

[![Apache License](https://img.shields.io/hexpm/l/plug.svg?maxAge=2592000)]()

**Requirements**

- Java 7
