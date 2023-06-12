package com.repins.infinite.engine;

import com.repins.infinite.engine.configuration.ProcessEngineConfiguration;
import com.repins.infinite.engine.service.DeploymentService;
import com.repins.infinite.engine.service.ProcessInstanceService;
import com.repins.infinite.engine.service.TaskInstanceService;

public interface ProcessEngine {

    ProcessEngineConfiguration getProcessEngineConfiguration();

    void init();

    DeploymentService getDeploymentService();

    ProcessInstanceService getProcessInstanceService();

    TaskInstanceService getTaskInstanceService();

}
