package com.repins.infinite.engine.context;

import com.repins.infinite.engine.cache.DeploymentRuntimeCacheStrategy;
import com.repins.infinite.engine.configuration.ProcessEngineConfiguration;
import com.repins.infinite.engine.element.base.BaseElement;
import com.repins.infinite.engine.model.ProcessDeployment;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GlobalContext {

    private final DeploymentRuntimeCacheStrategy deploymentRuntimeCache;


    public GlobalContext(DeploymentRuntimeCacheStrategy deploymentRuntimeCache) {
        this.deploymentRuntimeCache = deploymentRuntimeCache;
    }


    public void putProcessElements(String deployVersionId, List<BaseElement> elements) {
        deploymentRuntimeCache.putProcessElements(deployVersionId, elements);
    }

    public Map<String, BaseElement> getProcessElements(ProcessEngineConfiguration processEngineConfiguration, String deployVersionId) {
        Map<String, BaseElement> elements = deploymentRuntimeCache.getProcessElements(deployVersionId);
        if (null == elements) {
            //find in db when the cache is empty
            ProcessDeployment deployment = processEngineConfiguration.getProcessEngine().getDeploymentService().findDeployedByDeploymentVersionId(deployVersionId);
            List<BaseElement> elementList = processEngineConfiguration.getElementParser().parse(deployment.getMetaInfo());
            deploymentRuntimeCache.putProcessElements(deployVersionId, elementList);
        }
        return deploymentRuntimeCache.getProcessElements(deployVersionId);
    }


}
