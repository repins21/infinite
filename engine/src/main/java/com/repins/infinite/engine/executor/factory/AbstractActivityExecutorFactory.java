package com.repins.infinite.engine.executor.factory;

import com.repins.infinite.engine.element.base.BaseElement;
import com.repins.infinite.engine.executor.AbstractActivityExecutor;
import com.repins.infinite.engine.executor.process.AbstractProcessInstanceExecutor;
import com.repins.infinite.engine.executor.sequenceflow.AbstractSequenceFlowExecutor;

public abstract class AbstractActivityExecutorFactory {

    public abstract AbstractActivityExecutor getExecutor(Class<? extends BaseElement> clazz);

    public abstract AbstractProcessInstanceExecutor getProcessInstanceExecutor();

    public abstract AbstractSequenceFlowExecutor getSequenceFlowExecutor();

}
