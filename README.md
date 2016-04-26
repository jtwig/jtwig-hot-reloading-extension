# Jtwig Hot Reloading Extension

**How to use?**

- Include the dependency in your project (check [bintray](https://bintray.com/jtwig/maven/jtwig-hot-reloading-extension/_latestVersion)).

```java
EnvironmentConfiguration configuration = EnvironmentConfigurationBuilder
    .configuration()
        .extensions()
            .add(new HotReloadingExtension(TimeUnit.SECONDS, 1))
        .and()
    .build();
```

**Build Stats**

[![Build Status](https://travis-ci.org/jtwig/jtwig-hot-reloading-extension.svg?branch=master)](https://travis-ci.org/jtwig/jtwig-hot-reloading-extension)
[![Coverage Status](https://coveralls.io/repos/github/jtwig/jtwig-hot-reloading-extension/badge.svg?branch=master)](https://coveralls.io/github/jtwig/jtwig-hot-reloading-extension?branch=master)
[![Download](https://api.bintray.com/packages/jtwig/maven/jtwig-hot-reloading-extension/images/download.svg) ](https://bintray.com/jtwig/maven/jtwig-hot-reloading-extension/_latestVersion)

**Licensing**

[![Apache License](https://img.shields.io/hexpm/l/plug.svg?maxAge=2592000)]()

**Requirements**

- Java 7
