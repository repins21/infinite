package com.repins.infinite.engine.service.impl;

import com.repins.infinite.engine.command.StartProcessInstanceCmd;
import com.repins.infinite.engine.configuration.ProcessEngineConfiguration;
import com.repins.infinite.engine.constant.ProcessConstant;
import com.repins.infinite.engine.context.GlobalContext;
import com.repins.infinite.engine.context.RuntimeContext;
import com.repins.infinite.engine.db.repository.ProcessInstanceRepository;
import com.repins.infinite.engine.db.repository.ProcessRepository;
import com.repins.infinite.engine.element.base.BaseElement;
import com.repins.infinite.engine.element.event.StartEvent;
import com.repins.infinite.engine.exception.InfiniteEngineException;
import com.repins.infinite.engine.loader.InfiniteService;
import com.repins.infinite.engine.model.ProcessDeployment;
import com.repins.infinite.engine.model.ProcessInstance;
import com.repins.infinite.engine.service.DeploymentService;
import com.repins.infinite.engine.service.ProcessInstanceService;
import com.repins.infinite.engine.state.ProcessInstanceState;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@InfiniteService
public class ProcessInstanceServiceImpl implements ProcessInstanceService {
    private ProcessRepository processRepository;
    private ProcessEngineConfiguration processEngineConfiguration;
    private DeploymentService deploymentService;
    private GlobalContext globalContext;

    @Override
    public String startProcessInstance(StartProcessInstanceCmd cmd) {
        String deploymentVersionId = buildDeploymentVersionId(cmd.getDeploymentId(), cmd.getVersion());
        Map<String, BaseElement> elements = globalContext.getProcessElements(processEngineConfiguration, deploymentVersionId);
        //build context
        RuntimeContext runtimeContext = buildRuntimeContext(cmd, findStartEvent(elements));
        //start process instance
        ProcessInstance processInstance =
                processEngineConfiguration.getAbstractActivityFactory().getProcessInstanceExecutor().start(runtimeContext);
        return processInstance.getProcessInstanceId();
    }

    private RuntimeContext buildRuntimeContext(StartProcessInstanceCmd cmd, StartEvent startEvent) {
        ProcessInstance processInstance = new ProcessInstance();
        processInstance.setDeploymentId(cmd.getDeploymentId());
        processInstance.setName(cmd.getProcessName());
        processInstance.setStartBy(cmd.getStartBy());
        processInstance.setDeploymentVersionId(buildDeploymentVersionId(cmd.getDeploymentId(), cmd.getVersion()));
        processInstance.setProcessState(ProcessInstanceState.READY.getState());
        processInstance.setProcessInstanceId(processEngineConfiguration.getIdGenerator().nextId());

        RuntimeContext runtimeContext = new RuntimeContext();
        runtimeContext.setProcessInstance(processInstance);
        runtimeContext.setStartEvent(startEvent);
        runtimeContext.setGlobalContext(globalContext);
        runtimeContext.setProcessEngineConfiguration(processEngineConfiguration);
        runtimeContext.setProcessInstanceRepository(processRepository);
        runtimeContext.setActivityExecutorFactory(processEngineConfiguration.getAbstractActivityFactory());
        return runtimeContext;
    }

    private String buildDeploymentVersionId(String deploymentId, Integer version) {
        return deploymentId + ProcessConstant.VERSION_ID_SEPARATOR + version;
    }

    private StartEvent findStartEvent(Map<String, BaseElement> elements) {
        return (StartEvent) elements
                .values()
                .stream()
                .filter(e -> e instanceof StartEvent)
                .findFirst()
                .orElseThrow(() -> new InfiniteEngineException("startEvent not found"));
    }

    @Override
    public void setProcessEngineConfiguration(ProcessEngineConfiguration processEngineConfiguration) {
        this.processEngineConfiguration = processEngineConfiguration;
        this.deploymentService = processEngineConfiguration.getProcessEngineServiceFinder().findDeploymentService();
        this.globalContext = processEngineConfiguration.getGlobalContext();
    }

    @Override
    public ProcessEngineConfiguration getProcessEngineConfiguration() {
        return this.processEngineConfiguration;
    }

    @Override
    public void setProcessRepository(ProcessRepository processRepository) {
        this.processRepository = processRepository;
    }

    @Override
    public Class<? extends ProcessRepository> allowAcceptRepositoryClass() {
        return ProcessInstanceRepository.class;
    }

}
