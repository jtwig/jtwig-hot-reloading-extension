package org.jtwig.hot.reloading.outdated;

import org.jtwig.environment.Environment;
import org.jtwig.resource.reference.ResourceReference;

import java.util.concurrent.TimeUnit;

public class CheckIntervalIsFileOutdated implements IsFileOutdated {
    private final IsFileOutdated isFileOutdated;
    private final TimeUnit timeUnit;
    private final int wait;
    private volatile long waitUntil = -1;

    public CheckIntervalIsFileOutdated(IsFileOutdated isFileOutdated, TimeUnit timeUnit, int wait) {
        this.isFileOutdated = isFileOutdated;
        this.timeUnit = timeUnit;
        this.wait = wait;
    }

    @Override
    public boolean isOutdated(Environment environment, ResourceReference resourceReference) {
        if (waitUntil == -1 || waitUntil <  System.nanoTime()) {
            waitUntil = System.nanoTime() + timeUnit.toNanos(wait);
            return isFileOutdated.isOutdated(environment, resourceReference);
        }
        return false;
    }

    @Override
    public void check(Environment environment, ResourceReference resourceReference) {
        isFileOutdated.check(environment, resourceReference);
        waitUntil = System.nanoTime() + timeUnit.toNanos(wait);
    }
}
