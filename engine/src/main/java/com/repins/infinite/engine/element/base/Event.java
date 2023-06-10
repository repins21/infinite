package com.repins.infinite.engine.element.base;

public abstract class Event extends Activity {
    private boolean isStartEvent;
    private boolean isEndEvent;

    public boolean isStartEvent(){
        return false;
    }

    public boolean isEndEvent() {
        return false;
    }
}
