package com.repins.infinite.engine.db.repository;

import com.repins.infinite.engine.model.ProcessDeployment;

public interface DeploymentRepository extends ProcessRepository {

    void insertProcessDefinition(ProcessDeployment processDeployment);

    ProcessDeployment findByDeploymentId(String deploymentId);

    int updateDeploymentById(ProcessDeployment deployment);

    ProcessDeployment findByDeploymentVersionId(String deploymentVersionId, boolean deployed);
}
