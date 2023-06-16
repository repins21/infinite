package com.repins.infinite.engine.executor.process;

import com.repins.infinite.engine.context.RuntimeContext;
import com.repins.infinite.engine.db.repository.ExecutionRepository;
import com.repins.infinite.engine.db.repository.ProcessInstanceRepository;
import com.repins.infinite.engine.db.repository.TaskInstanceRepository;
import com.repins.infinite.engine.loader.ProcessEngineRepositoryFinder;
import com.repins.infinite.engine.state.ProcessInstanceState;

import java.time.LocalDateTime;

public class DefaultProcessInstanceExecutor extends AbstractProcessInstanceExecutor {

    @Override
    protected Boolean beforeExecute(RuntimeContext runtimeContext) {
        return true;
    }

    @Override
    protected void execute(RuntimeContext runtimeContext,boolean activeAtFirst) {
        runtimeContext
                .getActivityExecutorFactory()
                .getExecutor(runtimeContext.getCurElement().getClass())
                .execute(runtimeContext,activeAtFirst);
    }


    @Override
    protected void persistent(RuntimeContext runtimeContext) {
        ProcessEngineRepositoryFinder repositoryFinder =
                runtimeContext.getProcessEngineConfiguration().getProcessEngineRepositoryFinder();

        // persistent processInstance
        ProcessInstanceRepository processInstanceRepository =
                (ProcessInstanceRepository) repositoryFinder.findRepository(ProcessInstanceRepository.class);

        // todo history
        if (runtimeContext.getCompletedProcessInstance() != null){
            processInstanceRepository.updateProcessInstance(runtimeContext.getCompletedProcessInstance());
        }else {
            processInstanceRepository.insertProcessInstance(runtimeContext.getProcessInstance());
        }

        // persistent taskInstances
        TaskInstanceRepository taskInstanceRepository =
                (TaskInstanceRepository) repositoryFinder.findRepository(TaskInstanceRepository.class);
        if (!runtimeContext.getPersistentTasks().isEmpty()){
            taskInstanceRepository.insertBatchTaskInstances(runtimeContext.getPersistentTasks());
        }
        // update completed tasks state
        // todo move completed taskInstance into history and delete
        taskInstanceRepository.updateBatchTaskInstances(runtimeContext.getUpdateTasks());

        // persistent executions
        // todo move completed Executions into history and delete
        ExecutionRepository executionRepository =
                (ExecutionRepository) repositoryFinder.findRepository(ExecutionRepository.class);
        if (!runtimeContext.getPersistentExecutions().isEmpty()){
            executionRepository.insertBatchExecution(runtimeContext.getPersistentExecutions());
        }
        executionRepository.updateBatchExecutions(runtimeContext.getUpdateExecutions());
    }
}
