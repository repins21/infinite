package com.repins.infinite.engine.behavior;


import com.repins.infinite.engine.context.RuntimeContext;
import com.repins.infinite.engine.model.TaskAssignee;

import java.util.List;

public interface TaskAssigneeBehavior {

    List<TaskAssignee> findTaskAssignees(RuntimeContext context);
}
