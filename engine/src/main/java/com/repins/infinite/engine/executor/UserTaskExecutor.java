package com.repins.infinite.engine.executor;

import com.repins.infinite.engine.context.RuntimeContext;
import com.repins.infinite.engine.element.base.BaseElement;
import com.repins.infinite.engine.model.TaskInstance;
import com.repins.infinite.engine.state.TaskInstanceState;

import java.time.LocalDateTime;
import java.util.Map;

public class UserTaskExecutor extends AbstractActivityExecutor {

    @Override
    protected void active(RuntimeContext runtimeContext) {
        runtimeContext.getCompletedTasks().add(buildUserTaskInstance(runtimeContext));

    }

    @Override
    protected void complete(RuntimeContext runtimeContext) {

    }

    @Override
    protected boolean pause(RuntimeContext runtimeContext) {
        return true;
    }

    private TaskInstance buildUserTaskInstance(RuntimeContext runtimeContext) {
        BaseElement userTaskElement = runtimeContext.getCurElement();
        TaskInstance userTaskInstance = new TaskInstance();
        userTaskInstance.setTaskId(runtimeContext.getProcessEngineConfiguration().getIdGenerator().nextId());
        userTaskInstance.setStartTime(LocalDateTime.now());
        userTaskInstance.setElementType(userTaskElement.getType());
        userTaskInstance.setElementName(userTaskElement.getName());
        userTaskInstance.setElementKey(userTaskElement.getKey());
        userTaskInstance.setAssignee("user_todo");//todo figure out user
        userTaskInstance.setSourceTaskInstanceId(runtimeContext.getPreTaskInstance().getTaskId());
        userTaskInstance.setProcessInstanceId(runtimeContext.getProcessInstance().getProcessInstanceId());
        userTaskInstance.setInstanceState(TaskInstanceState.ACTIVE.getState());
        return userTaskInstance;
    }
}
