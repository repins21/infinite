package com.repins.infinite.engine.behavior;

import com.repins.infinite.engine.exception.InfiniteEngineException;

public class DefaultTaskBehaviorFactory implements TaskBehaviorFactory {
    public final String NORMAL_BEHAVIOR = "normal";
    public final String MULTIPLE_INSTANCE_BEHAVIOR = "multiple";
    private NormalTaskAssigneeBehavior normalTaskAssigneeBehavior;
    private MultiInstanceTaskAssigneeBehavior multiInstanceTaskAssigneeBehavior;

    public DefaultTaskBehaviorFactory() {
        this.aware();
    }

    @Override
    public TaskAssigneeBehavior getTaskAssigneeBehavior(String assigneeBehavior) {
        if (NORMAL_BEHAVIOR.equals(assigneeBehavior)) {
            return this.normalTaskAssigneeBehavior;
        }
        if (MULTIPLE_INSTANCE_BEHAVIOR.equals(assigneeBehavior)){
            return this.multiInstanceTaskAssigneeBehavior;
        }
        throw new InfiniteEngineException("Other task assignee behaviors not currently supported");
    }

    @Override
    public void aware() {
        this.normalTaskAssigneeBehavior = new NormalTaskAssigneeBehavior();
        this.multiInstanceTaskAssigneeBehavior = new MultiInstanceTaskAssigneeBehavior();
    }
}
