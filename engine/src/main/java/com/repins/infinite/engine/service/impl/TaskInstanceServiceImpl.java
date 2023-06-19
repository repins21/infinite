package com.repins.infinite.engine.service.impl;

import com.repins.infinite.engine.command.CompleteTaskCmd;
import com.repins.infinite.engine.configuration.ProcessEngineConfiguration;
import com.repins.infinite.engine.context.RuntimeContext;
import com.repins.infinite.engine.db.repository.ProcessInstanceRepository;
import com.repins.infinite.engine.db.repository.ProcessRepository;
import com.repins.infinite.engine.db.repository.TaskInstanceRepository;
import com.repins.infinite.engine.element.base.BaseElement;
import com.repins.infinite.engine.exception.InfiniteEngineException;
import com.repins.infinite.engine.exception.InfiniteIllegalAssigneeException;
import com.repins.infinite.engine.loader.InfiniteService;
import com.repins.infinite.engine.model.ProcessInstance;
import com.repins.infinite.engine.model.TaskInstance;
import com.repins.infinite.engine.service.TaskInstanceService;
import com.repins.infinite.engine.state.ProcessInstanceState;
import com.repins.infinite.engine.state.TaskInstanceState;

import java.time.LocalDateTime;

@InfiniteService
public class TaskInstanceServiceImpl implements TaskInstanceService {

    private ProcessEngineConfiguration processEngineConfiguration;
    private TaskInstanceRepository taskInstanceRepository;


    @Override
    public void complete(CompleteTaskCmd cmd) {
        TaskInstance taskInstance =
                taskInstanceRepository.selectByTaskIdAndProcessInstanceId(cmd.getTaskId(), cmd.getProcessInstanceId());

        validateAssignee(cmd, taskInstance);

        BaseElement taskElement = processEngineConfiguration
                .getGlobalContext()
                .getProcessElements(processEngineConfiguration, taskInstance.getDeploymentVersionId())
                .get(taskInstance.getElementKey());

        taskInstance.setInstanceState(TaskInstanceState.COMPLETE.getState());
        taskInstance.setEndTime(LocalDateTime.now());

        RuntimeContext runtimeContext = buildRuntimeContext(taskInstance, taskElement);

        processEngineConfiguration
                .getAbstractActivityFactory()
                .getProcessInstanceExecutor()
                .completeTask(runtimeContext);
    }


    @Override
    public void setProcessEngineConfiguration(ProcessEngineConfiguration processEngineConfiguration) {
        this.processEngineConfiguration = processEngineConfiguration;
    }

    @Override
    public ProcessEngineConfiguration getProcessEngineConfiguration() {
        return this.processEngineConfiguration;
    }

    @Override
    public void setProcessRepository(ProcessRepository processRepository) {
        this.taskInstanceRepository = (TaskInstanceRepository) processRepository;
    }

    @Override
    public Class<? extends ProcessRepository> allowAcceptRepositoryClass() {
        return TaskInstanceRepository.class;
    }

    protected void validateAssignee(CompleteTaskCmd cmd, TaskInstance taskInstance) {
        if (taskInstance == null) {
            throw new InfiniteIllegalAssigneeException(String.format("taskInstance not exist,taskId: %s", cmd.getTaskId()));
        }
        if (taskInstance.getAssignee() == null) {
            return;
        }
        if (!taskInstance.getAssignee().equals(cmd.getAssignee())) {
            throw new InfiniteIllegalAssigneeException(String.format("wrong assignee: %s,taskId: %s", cmd.getAssignee(), taskInstance.getTaskId()));
        }
    }

    private RuntimeContext buildRuntimeContext(TaskInstance taskInstance, BaseElement taskElement) {
        RuntimeContext runtimeContext = new RuntimeContext();
        runtimeContext.setCurElement(taskElement);
        runtimeContext.setCurTaskInstance(taskInstance);
        runtimeContext.getUpdateTasks().add(taskInstance);

        ProcessInstanceRepository processInstanceRepository =
                (ProcessInstanceRepository) this.processEngineConfiguration.getProcessEngineRepositoryFinder()
                        .findRepository(ProcessInstanceRepository.class);
        ProcessInstance processInstance =
                processInstanceRepository.selectByProcessInstanceIdAndState(taskInstance.getProcessInstanceId(), ProcessInstanceState.RUNNING.getState());
        if (processInstance == null){
            throw new InfiniteEngineException(String.format("processInstance not found or not running,processInstanceId:%s",taskInstance.getProcessInstanceId()));
        }
        runtimeContext.setProcessInstance(processInstance);

        runtimeContext.setProcessEngineConfiguration(this.processEngineConfiguration);
        runtimeContext.setGlobalContext(this.processEngineConfiguration.getGlobalContext());
        runtimeContext.setActivityExecutorFactory(this.processEngineConfiguration.getAbstractActivityFactory());
        return runtimeContext;
    }


}
