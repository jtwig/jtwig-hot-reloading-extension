package org.jtwig.hot.reloading;

import org.jtwig.environment.EnvironmentConfigurationBuilder;
import org.jtwig.extension.Extension;
import org.jtwig.hot.reloading.cache.HotReloadingTemplateCache;
import org.jtwig.hot.reloading.outdated.CheckIntervalIsFileOutdated;
import org.jtwig.hot.reloading.outdated.DefaultIsFileOutdated;
import org.jtwig.hot.reloading.outdated.IsFileOutdated;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class HotReloadingExtension implements Extension {
    private final TimeUnit waitUnit;
    private final int waitTime;

    public HotReloadingExtension() {
        waitUnit = TimeUnit.SECONDS;
        waitTime = -1;
    }

    public HotReloadingExtension(TimeUnit waitUnit, int waitTime) {
        this.waitUnit = waitUnit;
        this.waitTime = waitTime;
    }

    @Override
    public void configure(EnvironmentConfigurationBuilder configurationBuilder) {
        IsFileOutdated fileOutdated = new DefaultIsFileOutdated(new HashMap<String, Long>());

        if (waitTime != -1) {
            fileOutdated = new CheckIntervalIsFileOutdated(
                    fileOutdated,
                    waitUnit,
                    waitTime
            );
        }
        configurationBuilder.parser().withTemplateCache(new HotReloadingTemplateCache(fileOutdated));
    }
}
