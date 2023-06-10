package com.repins.infinite.engine.state;

public enum TaskInstanceState {
    ACTIVE(0),
    COMPLETE(1),
    SUSPEND(2);
    private final Integer state;

    public Integer getState() {
        return state;
    }

    TaskInstanceState(Integer state) {
        this.state = state;
    }
}
