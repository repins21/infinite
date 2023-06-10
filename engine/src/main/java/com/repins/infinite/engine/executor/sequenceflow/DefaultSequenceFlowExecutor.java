package com.repins.infinite.engine.executor.sequenceflow;


import com.repins.infinite.engine.context.RuntimeContext;
import com.repins.infinite.engine.element.base.BaseElement;
import com.repins.infinite.engine.element.base.SequenceFlow;
import com.repins.infinite.engine.exception.InfiniteEngineException;
import com.repins.infinite.engine.model.TaskInstance;
import com.repins.infinite.engine.state.TaskInstanceState;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DefaultSequenceFlowExecutor extends AbstractSequenceFlowExecutor {

    @Override
    public List<TaskInstance> findAtLeastOneOrElseThrow(List<BaseElement> sequenceFlows, RuntimeContext runtimeContext) {
        LocalDateTime now = LocalDateTime.now();
        List<TaskInstance> taskInstances = new ArrayList<>(sequenceFlows.size());
        sequenceFlows.forEach(sf -> {
            SequenceFlow sequenceFlow = (SequenceFlow) sf;
            if (calculateExpression(sequenceFlow.getExpression(), runtimeContext)) {
                taskInstances.add(buildTaskInstance(sequenceFlow,runtimeContext,now));
            }
        });
        if (taskInstances.isEmpty()){
            throw new InfiniteEngineException("non executable sequenceFLow");
        }
        return taskInstances;
    }

    protected boolean calculateExpression(String expression, RuntimeContext runtimeContext) {
        // todo calculate
        return true;
    }

    private TaskInstance buildTaskInstance(SequenceFlow sequenceFlow,RuntimeContext runtimeContext,LocalDateTime now){
        TaskInstance taskInstance = new TaskInstance();
        taskInstance.setElementName(sequenceFlow.getName());
        taskInstance.setElementKey(sequenceFlow.getKey());
        taskInstance.setElementType(sequenceFlow.getType());
        taskInstance.setTaskId(runtimeContext.getProcessEngineConfiguration().getIdGenerator().nextId());
        taskInstance.setProcessInstanceId(runtimeContext.getProcessInstance().getProcessInstanceId());
        taskInstance.setInstanceState(TaskInstanceState.COMPLETE.getState());
        taskInstance.setSourceTaskInstanceId(runtimeContext.getPreTaskInstance().getTaskId());
        taskInstance.setStartTime(now);
        taskInstance.setEndTime(LocalDateTime.now());
        return taskInstance;
    }
}
