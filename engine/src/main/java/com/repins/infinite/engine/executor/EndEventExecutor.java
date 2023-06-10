package com.repins.infinite.engine.executor;

import com.repins.infinite.engine.context.RuntimeContext;

public class EndEventExecutor extends AbstractActivityExecutor{

    @Override
    protected void active(RuntimeContext runtimeContext) {

    }

    @Override
    protected void complete(RuntimeContext runtimeContext) {

    }

    @Override
    protected boolean pause(RuntimeContext runtimeContext) {
        return true;
    }
}
