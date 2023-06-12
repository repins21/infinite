package com.repins.infinite.engine.executor;

import com.repins.infinite.engine.behavior.TaskAssigneeBehavior;
import com.repins.infinite.engine.context.RuntimeContext;
import com.repins.infinite.engine.element.base.BaseElement;
import com.repins.infinite.engine.model.Execution;
import com.repins.infinite.engine.model.TaskAssignee;
import com.repins.infinite.engine.model.TaskInstance;
import com.repins.infinite.engine.state.ExecutionState;
import com.repins.infinite.engine.state.TaskInstanceState;

import java.time.LocalDateTime;
import java.util.List;

public class UserTaskExecutor extends AbstractActivityExecutor {

    @Override
    protected void active(RuntimeContext runtimeContext) {
        Execution execution =
                buildExecution(runtimeContext, runtimeContext.getCurElement(), runtimeContext.getProcessInstance());
        execution.setState(ExecutionState.ACTIVE.getState());

        TaskAssigneeBehavior taskAssigneeBehavior = runtimeContext.getProcessEngineConfiguration().getTaskAssigneeBehavior();
        List<TaskAssignee> taskAssignees = taskAssigneeBehavior.findTaskAssignees(runtimeContext);
        runtimeContext.getExecutions().add(execution);
        List<TaskInstance> tasks = runtimeContext.getTasks();
        taskAssignees.forEach(taskAssignee -> tasks.add(buildUserTaskInstance(runtimeContext,taskAssignee,execution)));
    }

    @Override
    protected void complete(RuntimeContext runtimeContext) {

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
        userTaskInstance.setSourceTaskInstanceId(runtimeContext.getPreTaskInstance().getTaskId());
        userTaskInstance.setProcessInstanceId(runtimeContext.getProcessInstance().getProcessInstanceId());
        userTaskInstance.setInstanceState(TaskInstanceState.ACTIVE.getState());
        return userTaskInstance;
    }
}
