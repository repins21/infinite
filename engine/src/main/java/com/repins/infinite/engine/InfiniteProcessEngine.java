package com.repins.infinite.engine;

import com.repins.infinite.engine.configuration.ProcessEngineConfiguration;
import com.repins.infinite.engine.service.DeploymentService;
import com.repins.infinite.engine.service.ProcessInstanceService;
import com.repins.infinite.engine.service.TaskInstanceService;

public class InfiniteProcessEngine implements ProcessEngine {

    private DeploymentService deploymentService;
    private ProcessInstanceService processInstanceService;
    private TaskInstanceService taskInstanceService;

    public InfiniteProcessEngine(ProcessEngineConfiguration processEngineConfiguration) {
        this.processEngineConfiguration = processEngineConfiguration;
    }

    @Override
    public DeploymentService getDeploymentService() {
        return this.deploymentService;
    }

    @Override
    public ProcessInstanceService getProcessInstanceService() {
        return this.processInstanceService;
    }

    @Override
    public TaskInstanceService getTaskInstanceService() {
        return this.taskInstanceService;
    }


    protected ProcessEngineConfiguration processEngineConfiguration;


    @Override
    public void init() {
        loadServices();
        this.processEngineConfiguration.setProcessEngine(this);
        this.deploymentService = processEngineConfiguration.getProcessEngineServiceFinder().findDeploymentService();
        this.processInstanceService = processEngineConfiguration.getProcessEngineServiceFinder().findProcessInstanceService();
        this.taskInstanceService = processEngineConfiguration.getProcessEngineServiceFinder().findTaskInstanceService();
    }

    protected void loadServices() {
        processEngineConfiguration.getProcessEngineServiceLoader().loadServices(this.processEngineConfiguration);
    }


    @Override
    public ProcessEngineConfiguration getProcessEngineConfiguration() {
        return processEngineConfiguration;
    }


}
