package com.repins.infinite.engine.cache;

import com.repins.infinite.engine.element.base.BaseElement;

import java.util.List;
import java.util.Map;

public interface DeploymentRuntimeCacheStrategy {
    void putProcessElements(String deployVersionId, List<BaseElement> elements);

    Map<String,BaseElement> getProcessElements(String deployVersionId);

    void delete(String deployVersionId);
}
