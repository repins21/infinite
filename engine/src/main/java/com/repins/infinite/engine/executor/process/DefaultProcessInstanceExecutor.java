package com.repins.infinite.engine.executor.process;

import com.repins.infinite.engine.context.RuntimeContext;
import com.repins.infinite.engine.db.repository.ExecutionRepository;
import com.repins.infinite.engine.db.repository.ProcessInstanceRepository;
import com.repins.infinite.engine.db.repository.ProcessRepository;
import com.repins.infinite.engine.db.repository.TaskInstanceRepository;
import com.repins.infinite.engine.element.base.BaseElement;
import com.repins.infinite.engine.loader.ProcessEngineRepositoryFinder;
import com.repins.infinite.engine.state.ProcessInstanceState;

import java.time.LocalDateTime;

public class DefaultProcessInstanceExecutor extends AbstractProcessInstanceExecutor {

    @Override
    protected Boolean beforeExecute(RuntimeContext runtimeContext) {
        return true;
    }

    @Override
    protected void execute(RuntimeContext runtimeContext, BaseElement element) {
        runtimeContext.getProcessInstance().setProcessState(ProcessInstanceState.RUNNING.getState());
        runtimeContext.getProcessInstance().setStartTime(LocalDateTime.now());
        runtimeContext.setCurElement(element);
        runtimeContext.getActivityExecutorFactory().getExecutor(element.getClass()).execute(runtimeContext);
    }

    @Override
    protected void persistent(RuntimeContext runtimeContext) {
        ProcessEngineRepositoryFinder repositoryFinder =
                runtimeContext.getProcessEngineConfiguration().getProcessEngineRepositoryFinder();
        // persistent processInstance
        ProcessInstanceRepository processInstanceRepository =
                (ProcessInstanceRepository) repositoryFinder.findRepository(ProcessInstanceRepository.class);
        processInstanceRepository.insertProcessInstance(runtimeContext.getProcessInstance());

        // persistent taskInstances
        TaskInstanceRepository taskInstanceRepository =
                (TaskInstanceRepository) repositoryFinder.findRepository(TaskInstanceRepository.class);
        taskInstanceRepository.insertBatchTaskInstances(runtimeContext.getTasks());

        // persistent executions
        ExecutionRepository executionRepository =
                (ExecutionRepository) repositoryFinder.findRepository(ExecutionRepository.class);
        executionRepository.insertBatchExecution(runtimeContext.getExecutions());

    }
}
