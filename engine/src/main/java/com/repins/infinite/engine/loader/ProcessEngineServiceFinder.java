package com.repins.infinite.engine.loader;

import com.repins.infinite.engine.service.DeploymentService;
import com.repins.infinite.engine.service.ProcessInstanceService;
import com.repins.infinite.engine.service.TaskInstanceService;

public interface ProcessEngineServiceFinder {

    DeploymentService findDeploymentService();

    ProcessInstanceService findProcessInstanceService();

    TaskInstanceService findTaskInstanceService();
}
