package com.repins.infinite.engine.command;

public class CompleteTaskCmd {

    private String assignee;

    private String assigneeType;

    private String taskId;

    private String processInstanceId;

    private String comment;

    private Boolean stopProcess = false;



    public String getAssigneeType() {
        return assigneeType;
    }

    public void setAssigneeType(String assigneeType) {
        this.assigneeType = assigneeType;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getStopProcess() {
        return stopProcess;
    }

    public void setStopProcess(Boolean stopProcess) {
        this.stopProcess = stopProcess;
    }
}
