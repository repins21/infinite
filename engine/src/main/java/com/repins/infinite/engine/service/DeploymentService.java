package com.repins.infinite.engine.service;

import com.repins.infinite.engine.command.CreateDeploymentCmd;
import com.repins.infinite.engine.command.DeployDeploymentCmd;
import com.repins.infinite.engine.model.ProcessDeployment;

public interface DeploymentService extends ProcessService {

    String createDeployment(CreateDeploymentCmd cmd);

    String deploy(DeployDeploymentCmd cmd);

    ProcessDeployment findDeployedByDeploymentVersionId(String deploymentVersionId);
}
