package com.repins.infinite.engine.model;

public class TaskAssignee {

    private String assignee;

    private String assigneeType;

    public TaskAssignee() {
    }

    public TaskAssignee(String assignee, String assigneeType) {
        this.assignee = assignee;
        this.assigneeType = assigneeType;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getAssigneeType() {
        return assigneeType;
    }

    public void setAssigneeType(String assigneeType) {
        this.assigneeType = assigneeType;
    }
}
