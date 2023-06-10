package com.repins.infinite.engine.command;

import com.repins.infinite.engine.constant.ProcessConstant;

public class StartProcessInstanceCmd {

    private String deploymentId;

    /**
     * default value 1
     */
    private Integer version = ProcessConstant.VERSION_COUNTER;

    private String startBy;

    private String processName;

    // todo flow variables


    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getStartBy() {
        return startBy;
    }

    public void setStartBy(String startBy) {
        this.startBy = startBy;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }




    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }
}

