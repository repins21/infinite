package com.repins.infinite.engine.cache.local;

import com.repins.infinite.engine.cache.DeploymentRuntimeCacheStrategy;
import com.repins.infinite.engine.element.base.BaseElement;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class LocalDeploymentRuntimeCacheStrategy implements DeploymentRuntimeCacheStrategy {

    private static final ConcurrentHashMap<String, Map<String, BaseElement>> elementsCache = new ConcurrentHashMap<>();


    @Override
    public void putProcessElements(String deployVersionId, List<BaseElement> elements) {
        if (null == elements) {
            return;
        }
        elementsCache.put(deployVersionId, elements.stream().collect(Collectors.toMap(BaseElement::getKey, e -> e)));
    }

    @Override
    public Map<String, BaseElement> getProcessElements(String deployVersionId) {
        return elementsCache.get(deployVersionId);
    }

    @Override
    public void delete(String deployVersionId) {
        elementsCache.remove(deployVersionId);
    }
}
