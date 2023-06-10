package com.repins.infinite.engine.command;

public class DeployDeploymentCmd {

    private String deploymentId;

    private String deployBy;

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public String getDeployBy() {
        return deployBy;
    }

    public void setDeployBy(String deployBy) {
        this.deployBy = deployBy;
    }
}
