package org.jtwig.hot.reloading.outdated;

import org.jtwig.environment.Environment;
import org.jtwig.resource.reference.ResourceReference;

public interface IsFileOutdated {
    boolean isOutdated (Environment environment, ResourceReference resourceReference);
    void check (Environment environment, ResourceReference resourceReference);
}
