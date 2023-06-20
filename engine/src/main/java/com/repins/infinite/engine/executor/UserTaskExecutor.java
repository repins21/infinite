package com.repins.infinite.engine.executor;

import com.repins.infinite.engine.behavior.TaskAssigneeBehavior;
import com.repins.infinite.engine.behavior.TaskBehaviorFactory;
import com.repins.infinite.engine.context.RuntimeContext;
import com.repins.infinite.engine.element.base.BaseElement;
import com.repins.infinite.engine.element.task.UserTask;
import com.repins.infinite.engine.model.Execution;
import com.repins.infinite.engine.model.ProcessInstance;
import com.repins.infinite.engine.model.TaskAssignee;
import com.repins.infinite.engine.model.TaskInstance;
import com.repins.infinite.engine.state.ExecutionState;
import com.repins.infinite.engine.state.ProcessInstanceState;
import com.repins.infinite.engine.state.TaskInstanceState;

import java.time.LocalDateTime;
import java.util.List;

public class UserTaskExecutor extends AbstractActivityExecutor {

    @Override
    protected void active(RuntimeContext runtimeContext) {
        UserTask userTask = (UserTask) runtimeContext.getCurElement();
        Execution execution =
                buildExecution(runtimeContext, userTask, runtimeContext.getProcessInstance());
        execution.setState(ExecutionState.ACTIVE.getState());
        TaskBehaviorFactory taskBehaviorFactory = runtimeContext.getProcessEngineConfiguration().getTaskBehaviorFactory();
        TaskAssigneeBehavior taskAssigneeBehavior = taskBehaviorFactory.getTaskAssigneeBehavior(userTask.getAssigneeBehavior());
        List<TaskAssignee> taskAssignees = taskAssigneeBehavior.findTaskAssignees(runtimeContext);
        runtimeContext.getPersistentExecutions().add(execution);
        List<TaskInstance> tasks = runtimeContext.getPersistentTasks();
        taskAssignees.forEach(taskAssignee -> tasks.add(buildUserTaskInstance(runtimeContext, taskAssignee, execution)));
        taskAssigneeBehavior.beforeComplete(taskAssignees,runtimeContext);
    }

    @Override
    protected boolean complete(RuntimeContext runtimeContext) {
        UserTask userTask = (UserTask) runtimeContext.getCurElement();

        boolean completed = runtimeContext
                .getProcessEngineConfiguration()
                .getTaskBehaviorFactory()
                .getTaskAssigneeBehavior(userTask.getAssigneeBehavior())
                .completed(runtimeContext);

        if (completed) {
            updateExecution(runtimeContext);
        }

        return completed;
    }

    private void updateExecution(RuntimeContext context) {
        Execution execution = new Execution();
        execution.setState(ExecutionState.COMPLETE.getState());
        execution.setExecutionId(context.getCurTaskInstance().getExecutionId());
        context.getUpdateExecutions().add(execution);
    }

    @Override
    protected boolean pause(RuntimeContext runtimeContext) {
        return true;
    }

    private TaskInstance buildUserTaskInstance(RuntimeContext runtimeContext, TaskAssignee taskAssignee, Execution execution) {
        BaseElement userTaskElement = runtimeContext.getCurElement();
        TaskInstance userTaskInstance = new TaskInstance();
        userTaskInstance.setTaskId(runtimeContext.getProcessEngineConfiguration().getIdGenerator().nextId());
        userTaskInstance.setStartTime(LocalDateTime.now());
        userTaskInstance.setElementType(userTaskElement.getType());
        userTaskInstance.setElementName(userTaskElement.getName());
        userTaskInstance.setElementKey(userTaskElement.getKey());
        userTaskInstance.setExecutionId(execution.getExecutionId());
        userTaskInstance.setAssignee(taskAssignee.getAssignee());
        userTaskInstance.setAssigneeType(taskAssignee.getAssigneeType());
        userTaskInstance.setOwner(taskAssignee.getAssignee());
        userTaskInstance.setProcessInstanceId(runtimeContext.getProcessInstance().getProcessInstanceId());
        userTaskInstance.setInstanceState(TaskInstanceState.ACTIVE.getState());
        userTaskInstance.setDeploymentVersionId(runtimeContext.getProcessInstance().getDeploymentVersionId());

        if (runtimeContext.getPreTaskInstance() != null) {
            userTaskInstance.setSourceTaskInstanceId(runtimeContext.getPreTaskInstance().getTaskId());
        }

        return userTaskInstance;
    }
}
