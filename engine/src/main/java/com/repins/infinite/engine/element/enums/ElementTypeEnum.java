package com.repins.infinite.engine.element.enums;

import com.repins.infinite.engine.element.base.BaseElement;
import com.repins.infinite.engine.element.base.SequenceFlow;
import com.repins.infinite.engine.element.event.EndEvent;
import com.repins.infinite.engine.element.event.StartEvent;
import com.repins.infinite.engine.element.gatway.Gateway;
import com.repins.infinite.engine.element.task.UserTask;

public enum ElementTypeEnum {
    //   ====== event =====
    START_EVENT("START_EVENT", StartEvent.class),
    END_EVENT("END_EVENT", EndEvent.class),


    //   ====== task =====
    USER_TASK("USER_TASK", UserTask.class),


    //   ====== sequence_flow ====
    SEQUENCE_FLOW("SEQUENCE_FLOW", SequenceFlow.class),

    GATEWAY("GATEWAY", Gateway.class),

    ;

    private final String type;
    private final Class<? extends BaseElement> clazz;

    public String getType() {
        return type;
    }

    public Class<? extends BaseElement> getClazz() {
        return clazz;
    }

    ElementTypeEnum(String type, Class<? extends BaseElement> clazz) {
        this.type = type;
        this.clazz = clazz;
    }
}
