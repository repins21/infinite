package com.repins.infinite.engine.loader;

import com.repins.infinite.engine.service.DeploymentService;
import com.repins.infinite.engine.service.ProcessInstanceService;
import com.repins.infinite.engine.service.TaskInstanceService;
import com.repins.infinite.engine.utils.SpringContextHolder;

public class DefaultProcessEngineServiceFinder implements ProcessEngineServiceFinder {

    @Override
    public DeploymentService findDeploymentService() {
        return SpringContextHolder.getBean(DeploymentService.class);
    }

    @Override
    public ProcessInstanceService findProcessInstanceService() {
        return SpringContextHolder.getBean(ProcessInstanceService.class);
    }

    @Override
    public TaskInstanceService findTaskInstanceService() {
        return SpringContextHolder.getBean(TaskInstanceService.class);
    }

}
