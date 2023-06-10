package com.repins.infinite.db.mysql.deployment;

import com.repins.infinite.engine.db.repository.DeploymentRepository;
import com.repins.infinite.engine.model.ProcessDeployment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DeploymentMapper extends DeploymentRepository {

    void insertProcessDefinition(ProcessDeployment processDeployment);

    ProcessDeployment findByDeploymentId(String deploymentId);

    int updateDeploymentById(ProcessDeployment deployment);

    ProcessDeployment findByDeploymentVersionId(@Param("deploymentVersionId") String deploymentVersionId, @Param("deployed") boolean deployed);


}
