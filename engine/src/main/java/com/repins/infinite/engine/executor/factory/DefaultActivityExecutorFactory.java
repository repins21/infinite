package com.repins.infinite.engine.executor.factory;

import com.repins.infinite.engine.element.base.BaseElement;
import com.repins.infinite.engine.element.enums.ElementTypeEnum;
import com.repins.infinite.engine.exception.InfiniteEngineException;
import com.repins.infinite.engine.executor.*;
import com.repins.infinite.engine.executor.process.AbstractProcessInstanceExecutor;
import com.repins.infinite.engine.executor.process.DefaultProcessInstanceExecutor;
import com.repins.infinite.engine.executor.sequenceflow.AbstractSequenceFlowExecutor;
import com.repins.infinite.engine.executor.sequenceflow.DefaultSequenceFlowExecutor;

import java.util.HashMap;
import java.util.Map;

public class DefaultActivityExecutorFactory extends AbstractActivityExecutorFactory {

    protected final Map<Class<? extends BaseElement>, AbstractActivityExecutor> executorCache = new HashMap<>();
    protected final AbstractProcessInstanceExecutor processInstanceExecutor = new DefaultProcessInstanceExecutor();
    protected final AbstractSequenceFlowExecutor sequenceFlowExecutor = new DefaultSequenceFlowExecutor();

    @Override
    public AbstractActivityExecutor getExecutor(Class<? extends BaseElement> clazz) {
        return getExecutorByElementClass(clazz);
    }

    @Override
    public AbstractProcessInstanceExecutor getProcessInstanceExecutor() {
        return processInstanceExecutor;
    }

    @Override
    public AbstractSequenceFlowExecutor getSequenceFlowExecutor() {
        return sequenceFlowExecutor;
    }

    protected AbstractActivityExecutor getExecutorByElementClass(Class<? extends BaseElement> clazz) {
        if (clazz == ElementTypeEnum.START_EVENT.getClazz()) {
            return executorCache.computeIfAbsent(clazz, key -> new StartEventExecutor());
        }

        if (clazz == ElementTypeEnum.END_EVENT.getClazz()) {
            return executorCache.computeIfAbsent(clazz, key -> new EndEventExecutor());
        }

        if (clazz == ElementTypeEnum.USER_TASK.getClazz()) {
            return executorCache.computeIfAbsent(clazz, key -> new UserTaskExecutor());
        }

        throw new InfiniteEngineException("Other executors such as GATEWAY are not currently supported");

    }


}
