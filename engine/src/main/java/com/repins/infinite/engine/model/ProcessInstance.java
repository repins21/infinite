package com.repins.infinite.engine.model;

import java.time.LocalDateTime;

public class ProcessInstance {

    private Integer id;

    private String name;

    private String deploymentId;

    private String deploymentVersionId;

    private String processInstanceId;

    private String startBy;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer processState;

    public ProcessInstance() {
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Integer getProcessState() {
        return processState;
    }

    public void setProcessState(Integer processState) {
        this.processState = processState;
    }

    public String getStartBy() {
        return startBy;
    }

    public void setStartBy(String startBy) {
        this.startBy = startBy;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public String getDeploymentVersionId() {
        return deploymentVersionId;
    }

    public void setDeploymentVersionId(String deploymentVersionId) {
        this.deploymentVersionId = deploymentVersionId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }
}
