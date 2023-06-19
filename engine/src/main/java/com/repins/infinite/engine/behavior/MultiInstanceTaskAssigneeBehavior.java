package com.repins.infinite.engine.behavior;

import com.repins.infinite.engine.context.GlobalContext;
import com.repins.infinite.engine.context.RuntimeContext;
import com.repins.infinite.engine.element.task.UserTask;
import com.repins.infinite.engine.model.TaskAssignee;

import java.util.Arrays;
import java.util.List;

public class MultiInstanceTaskAssigneeBehavior implements TaskAssigneeBehavior {
    @Override
    public List<TaskAssignee> findTaskAssignees(RuntimeContext context) {
        TaskAssignee taskAssignee1 = new TaskAssignee();
        taskAssignee1.setAssignee("user1");
        taskAssignee1.setAssigneeType("user");
        TaskAssignee taskAssignee2 = new TaskAssignee();
        taskAssignee2.setAssignee("user2");
        taskAssignee2.setAssigneeType("user");
        TaskAssignee taskAssignee3 = new TaskAssignee();
        taskAssignee3.setAssignee("user3");
        taskAssignee3.setAssigneeType("user");
        return Arrays.asList(taskAssignee1, taskAssignee2, taskAssignee3);
    }

    @Override
    public boolean completed(RuntimeContext context) {
        UserTask userTask = (UserTask) context.getCurElement();
        return false;
    }

    @Override
    public void beforeComplete(List<TaskAssignee> taskAssignees, RuntimeContext context) {
        GlobalContext globalContext = context.getGlobalContext();

    }


}
