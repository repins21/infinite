package com.repins.infinite.engine.executor;

import com.repins.infinite.engine.context.RuntimeContext;
import com.repins.infinite.engine.element.base.BaseElement;
import com.repins.infinite.engine.model.ProcessInstance;
import com.repins.infinite.engine.model.TaskInstance;
import com.repins.infinite.engine.state.TaskInstanceState;

import java.time.LocalDateTime;

public class StartEventExecutor extends AbstractActivityExecutor {

    @Override
    protected void active(RuntimeContext runtimeContext) {

    }

    @Override
    protected void complete(RuntimeContext runtimeContext) {
        BaseElement startEventElement = runtimeContext.getCurElement();

        ProcessInstance processInstance = runtimeContext.getProcessInstance();

        TaskInstance startEventTask = buildTaskInstance(runtimeContext, startEventElement, processInstance);
        // put into completed cache,we will persist them when all the tasks done
        runtimeContext.getCompletedTasks().add(startEventTask);

        // as pre
        runtimeContext.setPreElement(startEventElement);
        runtimeContext.setPreTaskInstance(startEventTask);

    }

    @Override
    protected boolean pause(RuntimeContext runtimeContext) {
        return false;
    }

    private static TaskInstance buildTaskInstance(RuntimeContext runtimeContext,
                                                  BaseElement startEventElement,
                                                  ProcessInstance processInstance) {
        TaskInstance startEventTask = new TaskInstance();
        startEventTask.setTaskId(runtimeContext.getProcessEngineConfiguration().getIdGenerator().nextId());
        startEventTask.setStartTime(processInstance.getStartTime());
        startEventTask.setElementName(startEventElement.getName());
        startEventTask.setElementKey(startEventElement.getKey());
        startEventTask.setElementType(startEventElement.getType());
        startEventTask.setEndTime(LocalDateTime.now());
        startEventTask.setInstanceState(TaskInstanceState.COMPLETE.getState());
        startEventTask.setProcessInstanceId(runtimeContext.getProcessInstance().getProcessInstanceId());
        return startEventTask;
    }

}
