package com.repins.infinite.engine;

import com.repins.infinite.engine.configuration.ProcessEngineConfiguration;
import com.repins.infinite.engine.service.DeploymentService;
import com.repins.infinite.engine.service.ProcessInstanceService;

public interface ProcessEngine {

    ProcessEngineConfiguration getProcessEngineConfiguration();

    void init(ProcessEngineConfiguration processEngineConfiguration);

    void setProcessEngineConfiguration(ProcessEngineConfiguration processEngineConfiguration);

    DeploymentService getDeploymentService();

    ProcessInstanceService getProcessInstanceService();

}
