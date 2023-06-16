package com.repins.infinite.engine.executor.process;

import com.repins.infinite.engine.context.RuntimeContext;
import com.repins.infinite.engine.element.base.BaseElement;
import com.repins.infinite.engine.model.ProcessInstance;
import com.repins.infinite.engine.state.ProcessInstanceState;

import java.time.LocalDateTime;

public abstract class AbstractProcessInstanceExecutor {

    protected abstract Boolean beforeExecute(RuntimeContext runtimeContext);

    protected abstract void execute(RuntimeContext runtimeContext,boolean activeAtFirst);
    /**
     * persistent in db
     */
    protected abstract void persistent(RuntimeContext runtimeContext);


    public void completeTask(RuntimeContext runtimeContext){
        execute(runtimeContext,false);
        persistent(runtimeContext);
    }


    public ProcessInstance start(RuntimeContext runtimeContext) {
        Boolean executeImmediately = beforeExecute(runtimeContext);
        if (executeImmediately) {
            runtimeContext.getProcessInstance().setProcessState(ProcessInstanceState.RUNNING.getState());
            runtimeContext.getProcessInstance().setStartTime(LocalDateTime.now());
            runtimeContext.setCurElement(runtimeContext.getStartEvent());
            execute(runtimeContext,true);
        }
        persistent(runtimeContext);
        return runtimeContext.getProcessInstance();
    }
}
