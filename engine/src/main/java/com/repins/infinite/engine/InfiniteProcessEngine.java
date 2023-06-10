package com.repins.infinite.engine;

import com.repins.infinite.engine.service.DeploymentService;
import com.repins.infinite.engine.service.ProcessInstanceService;

public class InfiniteProcessEngine extends AbstractProcessEngine {

    public InfiniteProcessEngine() {
    }

    @Override
    public DeploymentService getDeploymentService() {
        return processEngineConfiguration.getProcessEngineServiceFinder().findDeploymentService();
    }

    @Override
    public ProcessInstanceService getProcessInstanceService() {
        return processEngineConfiguration.getProcessEngineServiceFinder().findProcessInstanceService();
    }


}
