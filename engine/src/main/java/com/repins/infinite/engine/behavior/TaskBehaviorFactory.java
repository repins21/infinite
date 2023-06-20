package com.repins.infinite.engine.behavior;

public interface TaskBehaviorFactory {


    TaskAssigneeBehavior getTaskAssigneeBehavior(String assigneeBehavior);

    void aware();
}
