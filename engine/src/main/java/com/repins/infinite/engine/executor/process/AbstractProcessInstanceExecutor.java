package com.repins.infinite.engine.executor.process;

import com.repins.infinite.engine.context.RuntimeContext;
import com.repins.infinite.engine.element.base.BaseElement;
import com.repins.infinite.engine.model.ProcessInstance;

public abstract class AbstractProcessInstanceExecutor {

    protected abstract Boolean beforeExecute(RuntimeContext runtimeContext);

    protected abstract void execute(RuntimeContext runtimeContext, BaseElement element);

    /**
     * persistent in db
     */
    protected abstract void persistent(RuntimeContext runtimeContext);

    public ProcessInstance start(RuntimeContext runtimeContext) {
        Boolean executeImmediately = beforeExecute(runtimeContext);
        if (executeImmediately) {
            execute(runtimeContext,runtimeContext.getStartEvent());
        }
        persistent(runtimeContext);
        return runtimeContext.getProcessInstance();
    }
}
