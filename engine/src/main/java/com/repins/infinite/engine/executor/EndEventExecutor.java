package com.repins.infinite.engine.executor;

import com.repins.infinite.engine.context.RuntimeContext;
import com.repins.infinite.engine.model.ProcessInstance;
import com.repins.infinite.engine.state.ProcessInstanceState;

import java.time.LocalDateTime;

public class EndEventExecutor extends AbstractActivityExecutor {

    @Override
    protected void active(RuntimeContext runtimeContext) {

    }

    @Override
    protected boolean complete(RuntimeContext runtimeContext) {
        runtimeContext
                .getPersistentExecutions()
                .add(buildExecution(runtimeContext, runtimeContext.getCurElement(), runtimeContext.getProcessInstance()));
        return true;
    }

    @Override
    protected boolean pause(RuntimeContext runtimeContext) {
        return false;
    }

    @Override
    protected void leave(RuntimeContext runtimeContext, boolean completed) {
        ProcessInstance processInstance = runtimeContext.getProcessInstance();
        processInstance.setProcessState(ProcessInstanceState.END.getState());
        processInstance.setEndTime(LocalDateTime.now());
    }
}
