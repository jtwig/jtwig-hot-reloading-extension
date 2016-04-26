package org.jtwig.hot.reloading.outdated;

import com.google.common.base.Optional;
import org.jtwig.environment.Environment;
import org.jtwig.resource.metadata.ResourceMetadata;
import org.jtwig.resource.reference.ResourceReference;

import java.io.File;
import java.net.URL;
import java.util.Map;

public class DefaultIsFileOutdated implements IsFileOutdated {
    public static final String FILE = "file";
    private final Map<String, Long> lastModifiedDate;

    public DefaultIsFileOutdated(Map<String, Long> lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public boolean isOutdated(Environment environment, ResourceReference resourceReference) {
        ResourceMetadata resourceMetadata = environment.getResourceEnvironment().getResourceService().loadMetadata(resourceReference);
        Optional<URL> urlOptional = resourceMetadata.toUrl();
        if (urlOptional.isPresent()) {
            URL url = urlOptional.get();
            if (FILE.equals(url.getProtocol())) {
                String pathname = url.getFile();
                long lastModified = new File(pathname).lastModified();
                if (!lastModifiedDate.containsKey(pathname)) {
                    lastModifiedDate.put(pathname, lastModified);
                } else {
                    if (lastModifiedDate.get(pathname) != lastModified) {
                        lastModifiedDate.put(pathname, lastModified);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void check(Environment environment, ResourceReference resourceReference) {
        ResourceMetadata resourceMetadata = environment.getResourceEnvironment().getResourceService().loadMetadata(resourceReference);
        Optional<URL> urlOptional = resourceMetadata.toUrl();
        if (urlOptional.isPresent()) {
            URL url = urlOptional.get();
            if (FILE.equals(url.getProtocol())) {
                String pathname = url.getFile();
                long lastModified = new File(pathname).lastModified();
                if (!lastModifiedDate.containsKey(pathname)) {
                    lastModifiedDate.put(pathname, lastModified);
                }
            }
        }
    }
}
