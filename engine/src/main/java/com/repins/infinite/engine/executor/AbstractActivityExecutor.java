package com.repins.infinite.engine.executor;

import com.repins.infinite.engine.context.RuntimeContext;
import com.repins.infinite.engine.element.base.BaseElement;
import com.repins.infinite.engine.model.ProcessDeployment;
import com.repins.infinite.engine.model.TaskInstance;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractActivityExecutor {

    protected abstract void active(RuntimeContext runtimeContext);

    //todo  distribute lock
    public void execute(RuntimeContext runtimeContext) {
        active(runtimeContext);
        if (pause(runtimeContext)) {
            return;
        }
        complete(runtimeContext);
        exit(runtimeContext);
    }

    protected abstract void complete(RuntimeContext runtimeContext);

    protected abstract boolean pause(RuntimeContext runtimeContext);

    protected void exit(RuntimeContext runtimeContext) {
        BaseElement currentElement = runtimeContext.getCurElement();
        Map<String, BaseElement> elementsCache = getProcessElementsCache(runtimeContext);

        List<TaskInstance> sequenceFlows =
                runtimeContext
                        .getActivityExecutorFactory()
                        .getSequenceFlowExecutor()
                        .findAtLeastOneOrElseThrow(getNextSequenceFlowElements(currentElement, elementsCache), runtimeContext);

        runtimeContext.getCompletedTasks().addAll(sequenceFlows);

        // execute next tasks in the loop
        // todo async support
        sequenceFlows.forEach(sf -> {
            // getOutgoing().get(0) sequenceFlow must have only one outgoing
            BaseElement element = elementsCache.get(elementsCache.get(sf.getElementKey()).getOutgoing().get(0));
            runtimeContext.setPreElement(element);
            runtimeContext.setPreTaskInstance(sf);
            runtimeContext.setCurElement(element);
            runtimeContext.getActivityExecutorFactory().getExecutor(element.getClass()).execute(runtimeContext);
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
}
