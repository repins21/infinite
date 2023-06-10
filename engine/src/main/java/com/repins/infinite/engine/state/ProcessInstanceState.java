package com.repins.infinite.engine.state;

public enum ProcessInstanceState {
    READY(0),
    ACTIVE(1),
    RUNNING(2),
    SUSPEND(3),
    END(4);

    private final Integer state;


    public Integer getState() {
        return state;
    }

    ProcessInstanceState(Integer state) {
        this.state = state;
    }
}
