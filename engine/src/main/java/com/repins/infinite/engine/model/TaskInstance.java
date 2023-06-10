package com.repins.infinite.engine.model;

import java.time.LocalDateTime;

public class TaskInstance {

    private Integer id;

    private String taskId;

    private String processInstanceId;

    private String elementName;

    private String elementType;

    private String elementKey;

    private String sourceTaskInstanceId;

    private String assignee;

    private Integer instanceState;

    private LocalDateTime startTime;

    private LocalDateTime endTime;


    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public Integer getInstanceState() {
        return instanceState;
    }

    public void setInstanceState(Integer instanceState) {
        this.instanceState = instanceState;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getElementType() {
        return elementType;
    }

    public void setElementType(String elementType) {
        this.elementType = elementType;
    }

    public String getElementKey() {
        return elementKey;
    }

    public void setElementKey(String elementKey) {
        this.elementKey = elementKey;
    }

    public String getSourceTaskInstanceId() {
        return sourceTaskInstanceId;
    }

    public void setSourceTaskInstanceId(String sourceTaskInstanceId) {
        this.sourceTaskInstanceId = sourceTaskInstanceId;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
