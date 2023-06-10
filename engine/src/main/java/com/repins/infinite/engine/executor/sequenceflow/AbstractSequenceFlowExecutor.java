package com.repins.infinite.engine.executor.sequenceflow;

import com.repins.infinite.engine.context.RuntimeContext;
import com.repins.infinite.engine.element.base.BaseElement;
import com.repins.infinite.engine.model.TaskInstance;

import java.util.List;

public abstract class AbstractSequenceFlowExecutor {

    public abstract List<TaskInstance> findAtLeastOneOrElseThrow(List<BaseElement> sequenceFlows, RuntimeContext runtimeContext);
}
