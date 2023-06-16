package com.repins.infinite.engine.behavior;

import com.repins.infinite.engine.context.RuntimeContext;
import com.repins.infinite.engine.element.ext.TaskAssigneeExtensionElement;
import com.repins.infinite.engine.element.task.UserTask;
import com.repins.infinite.engine.model.TaskAssignee;

import java.util.Collections;
import java.util.List;

public class NormalTaskAssigneeBehavior implements TaskAssigneeBehavior {
    @Override
    public List<TaskAssignee> findTaskAssignees(RuntimeContext context) {
        UserTask userTask = (UserTask) context.getCurElement();
        TaskAssigneeExtensionElement ext = userTask.getTaskAssigneeExtensionElement();
        return Collections.singletonList(new TaskAssignee(ext.getAssignee(), ext.getAssigneeType()));
    }

    @Override
    public boolean completed(RuntimeContext context) {
        return true;
    }


}
