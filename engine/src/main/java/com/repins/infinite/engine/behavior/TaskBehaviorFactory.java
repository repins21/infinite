package com.repins.infinite.engine.behavior;

public class TaskBehaviorFactory {

    private TaskAssigneeBehavior taskAssigneeBehavior;

    public TaskAssigneeBehavior getTaskAssigneeBehavior() {
        return new NormalTaskAssigneeBehavior();
    }
}
