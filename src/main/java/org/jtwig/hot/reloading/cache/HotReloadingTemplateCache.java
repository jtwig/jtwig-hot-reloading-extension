package org.jtwig.hot.reloading.cache;

import org.jtwig.environment.Environment;
import org.jtwig.hot.reloading.outdated.IsFileOutdated;
import org.jtwig.model.tree.Node;
import org.jtwig.parser.JtwigParser;
import org.jtwig.parser.cache.TemplateCache;
import org.jtwig.resource.reference.ResourceReference;

import java.util.HashMap;
import java.util.Map;

public class HotReloadingTemplateCache implements TemplateCache {
    private final Map<ResourceReference, Node> cache;
    private final IsFileOutdated isFileOutdated;

    public HotReloadingTemplateCache(IsFileOutdated isFileOutdated) {
        this.isFileOutdated = isFileOutdated;
        this.cache = new HashMap<>();
    }

    @Override
    public synchronized Node get(JtwigParser parser, Environment environment, ResourceReference resource) {
        if (cache.containsKey(resource)) {
            if (isFileOutdated.isOutdated(environment, resource)) {
                cache.remove(resource);
            }
        }

        if (!cache.containsKey(resource)) {
            Node node = parser.parse(environment, resource);
            isFileOutdated.check(environment, resource);
            cache.put(resource, node);
            return node;
        }

        return cache.get(resource);
    }
}
