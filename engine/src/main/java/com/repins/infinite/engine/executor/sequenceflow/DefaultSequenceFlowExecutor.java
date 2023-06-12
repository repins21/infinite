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
import java.util.stream.Collectors;

public class DefaultSequenceFlowExecutor extends AbstractSequenceFlowExecutor {

    @Override
    public List<SequenceFlow> findAtLeastOneOrElseThrow(List<BaseElement> sequenceFlows, RuntimeContext runtimeContext) {
        List<SequenceFlow> flows = sequenceFlows
                .stream()
                .map(e -> (SequenceFlow)e)
                .filter(e -> calculateExpression(e.getExpression(),runtimeContext))
                .collect(Collectors.toList());
        if (flows.isEmpty()){
            throw new InfiniteEngineException("non executable sequenceFLow");
        }
        return flows;
    }

    protected boolean calculateExpression(String expression, RuntimeContext runtimeContext) {
        // todo calculate
        return true;
    }

}
