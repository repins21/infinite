package com.repins.infinite.engine.executor;

import com.repins.infinite.engine.context.RuntimeContext;
import com.repins.infinite.engine.element.base.BaseElement;
import com.repins.infinite.engine.element.base.SequenceFlow;
import com.repins.infinite.engine.model.Execution;
import com.repins.infinite.engine.model.ProcessInstance;
import com.repins.infinite.engine.model.TaskInstance;
import com.repins.infinite.engine.state.ExecutionState;
import com.repins.infinite.engine.state.TaskInstanceState;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractActivityExecutor {

    protected abstract void active(RuntimeContext runtimeContext);

    //todo  distribute lock
    public void execute(RuntimeContext runtimeContext,boolean activeAtFirst) {
        if (activeAtFirst){
            active(runtimeContext);
            if (pause(runtimeContext)) {
                return;
            }
        }

        boolean completed = complete(runtimeContext);

        leave(runtimeContext, completed);
    }

    protected abstract boolean complete(RuntimeContext runtimeContext);

    protected abstract boolean pause(RuntimeContext runtimeContext);

    protected void leave(RuntimeContext runtimeContext, boolean completed) {
        if (!completed) {
            return;
        }

        BaseElement currentElement = runtimeContext.getCurElement();
        Map<String, BaseElement> elementsCache = getProcessElementsCache(runtimeContext);
        List<BaseElement> sequenceFlowElements = getNextSequenceFlowElements(currentElement, elementsCache);

        List<SequenceFlow> sequenceFlows =
                runtimeContext
                        .getActivityExecutorFactory()
                        .getSequenceFlowExecutor()
                        .findAtLeastOneOrElseThrow(sequenceFlowElements, runtimeContext);

        sequenceFlows.forEach(sequenceFlow -> {
            Execution execution = buildExecution(runtimeContext, sequenceFlow, runtimeContext.getProcessInstance());
            runtimeContext.getPersistentExecutions().add(execution);
            // execute next tasks in the loop
            // todo async support
            // getOutgoing().get(0) sequenceFlow must have only one outgoing
            BaseElement element = elementsCache.get(elementsCache.get(sequenceFlow.getKey()).getOutgoing().get(0));
            runtimeContext.setPreElement(element);
            runtimeContext.setCurElement(element);
            runtimeContext.getActivityExecutorFactory().getExecutor(element.getClass()).execute(runtimeContext,true);
        });
    }

    private static List<BaseElement> getNextSequenceFlowElements(BaseElement currentElement,
                                                                 Map<String, BaseElement> elementsCache) {
        return currentElement.getOutgoing()
                .stream()
                .map(elementsCache::get)
                .collect(Collectors.toList());
    }

    private static Map<String, BaseElement> getProcessElementsCache(RuntimeContext runtimeContext) {
        return runtimeContext.getGlobalContext().getProcessElements(
                runtimeContext.getProcessEngineConfiguration(),
                runtimeContext.getProcessInstance().getDeploymentVersionId());
    }

    private TaskInstance buildSequenceFlowTaskInstance(SequenceFlow sequenceFlow, RuntimeContext runtimeContext) {
        TaskInstance taskInstance = new TaskInstance();
        taskInstance.setElementName(sequenceFlow.getName());
        taskInstance.setElementKey(sequenceFlow.getKey());
        taskInstance.setElementType(sequenceFlow.getType());
        taskInstance.setTaskId(runtimeContext.getProcessEngineConfiguration().getIdGenerator().nextId());
        taskInstance.setProcessInstanceId(runtimeContext.getProcessInstance().getProcessInstanceId());
        taskInstance.setInstanceState(TaskInstanceState.COMPLETE.getState());
        taskInstance.setSourceTaskInstanceId(runtimeContext.getPreTaskInstance().getTaskId());
        taskInstance.setStartTime(runtimeContext.getPreTaskInstance().getEndTime());
        taskInstance.setEndTime(LocalDateTime.now());
        taskInstance.setDeploymentVersionId(runtimeContext.getProcessInstance().getDeploymentVersionId());
        return taskInstance;
    }

    protected Execution buildExecution(RuntimeContext runtimeContext,
                                       BaseElement element,
                                       ProcessInstance processInstance) {
        Execution execution = new Execution();
        execution.setExecutionId(runtimeContext.getProcessEngineConfiguration().getIdGenerator().nextId());
        execution.setDeploymentVersionId(processInstance.getDeploymentVersionId());
        execution.setElementKey(element.getKey());
        execution.setProcessInstanceId(processInstance.getProcessInstanceId());
        execution.setState(ExecutionState.COMPLETE.getState());
        return execution;
    }
}
