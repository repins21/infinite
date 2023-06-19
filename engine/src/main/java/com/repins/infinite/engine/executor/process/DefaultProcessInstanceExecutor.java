package com.repins.infinite.engine.executor.process;

import com.repins.infinite.engine.context.RuntimeContext;
import com.repins.infinite.engine.db.repository.ExecutionRepository;
import com.repins.infinite.engine.db.repository.ProcessInstanceRepository;
import com.repins.infinite.engine.db.repository.TaskInstanceRepository;
import com.repins.infinite.engine.loader.ProcessEngineRepositoryFinder;
import com.repins.infinite.engine.model.ProcessInstance;
import com.repins.infinite.engine.state.ProcessInstanceState;

public class DefaultProcessInstanceExecutor extends AbstractProcessInstanceExecutor {

    @Override
    protected Boolean beforeExecute(RuntimeContext runtimeContext) {
        return true;
    }

    @Override
    protected void execute(RuntimeContext runtimeContext, boolean activeAtFirst) {
        runtimeContext
                .getActivityExecutorFactory()
                .getExecutor(runtimeContext.getCurElement().getClass())
                .execute(runtimeContext, activeAtFirst);
    }


    @Override
    protected void persistent(RuntimeContext runtimeContext) {
        ProcessEngineRepositoryFinder repositoryFinder =
                runtimeContext.getProcessEngineConfiguration().getProcessEngineRepositoryFinder();

        // persistent processInstance
        persistentProcessInstance(runtimeContext, repositoryFinder);

        // persistent taskInstances
        persistentTasks(runtimeContext, repositoryFinder);

        // persistent executions
        persistentExecutions(runtimeContext, repositoryFinder);
    }

    private static void persistentExecutions(RuntimeContext runtimeContext, ProcessEngineRepositoryFinder repositoryFinder) {
        // todo move completed Executions into history and delete
        ExecutionRepository executionRepository =
                (ExecutionRepository) repositoryFinder.findRepository(ExecutionRepository.class);
        if (!runtimeContext.getPersistentExecutions().isEmpty()) {
            executionRepository.insertBatchExecution(runtimeContext.getPersistentExecutions());
        }
        executionRepository.updateBatchExecutions(runtimeContext.getUpdateExecutions());
    }

    private static void persistentTasks(RuntimeContext runtimeContext, ProcessEngineRepositoryFinder repositoryFinder) {
        TaskInstanceRepository taskInstanceRepository =
                (TaskInstanceRepository) repositoryFinder.findRepository(TaskInstanceRepository.class);
        if (!runtimeContext.getPersistentTasks().isEmpty()) {
            taskInstanceRepository.insertBatchTaskInstances(runtimeContext.getPersistentTasks());
        }
        // update completed tasks state
        // todo move completed taskInstance into history and delete
        taskInstanceRepository.updateBatchTaskInstances(runtimeContext.getUpdateTasks());
    }

    private static void persistentProcessInstance(RuntimeContext runtimeContext, ProcessEngineRepositoryFinder repositoryFinder) {
        // todo history
        ProcessInstance processInstance = runtimeContext.getProcessInstance();
        if (processInstance == null || processInstance.getProcessState() == null) {
            return;
        }

        ProcessInstanceRepository processInstanceRepository =
                (ProcessInstanceRepository) repositoryFinder.findRepository(ProcessInstanceRepository.class);

        if (ProcessInstanceState.END.getState().equals(processInstance.getProcessState())) {
            processInstanceRepository.updateProcessInstance(runtimeContext.getProcessInstance());
        } else {
            processInstanceRepository.insertProcessInstance(runtimeContext.getProcessInstance());
        }
    }
}
