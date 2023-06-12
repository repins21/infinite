package com.repins.infinite.engine.state;

public enum ExecutionState {
    ACTIVE(0),
    COMPLETE(1),
    SUSPEND(2);
    private final Integer state;

    public Integer getState() {
        return state;
    }

    ExecutionState(Integer state) {
        this.state = state;
    }
}
