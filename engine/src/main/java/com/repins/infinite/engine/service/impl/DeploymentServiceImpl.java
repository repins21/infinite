package com.repins.infinite.engine.service.impl;

import com.repins.infinite.engine.command.CreateDeploymentCmd;
import com.repins.infinite.engine.command.DeployDeploymentCmd;
import com.repins.infinite.engine.configuration.ProcessEngineConfiguration;
import com.repins.infinite.engine.constant.ProcessConstant;
import com.repins.infinite.engine.db.repository.DeploymentRepository;
import com.repins.infinite.engine.db.repository.ProcessRepository;
import com.repins.infinite.engine.element.base.BaseElement;
import com.repins.infinite.engine.exception.InfiniteEngineException;
import com.repins.infinite.engine.exception.InfiniteIllegalArgumentException;
import com.repins.infinite.engine.generator.IdGenerator;
import com.repins.infinite.engine.loader.InfiniteService;
import com.repins.infinite.engine.model.ProcessDeployment;
import com.repins.infinite.engine.service.DeploymentService;

import java.time.LocalDateTime;
import java.util.List;

@InfiniteService
public class DeploymentServiceImpl implements DeploymentService {

    private ProcessEngineConfiguration processEngineConfiguration;

    private DeploymentRepository deploymentRepository;

    @Override
    public String createDeployment(CreateDeploymentCmd cmd) {
        //todo validate required params
        if (null == cmd) {
            throw new InfiniteIllegalArgumentException("CreateProcessDefinitionCmd can not be null");
        }
        processEngineConfiguration.getProcessValidator().validate(cmd.getMetaInfo());
        ProcessDeployment processDeployment = buildCreateProcessDefinition(cmd);
        deploymentRepository.insertProcessDefinition(processDeployment);
        return processDeployment.getDeploymentId();
    }

    @Override
    public String deploy(DeployDeploymentCmd cmd) {
        //todo validate required params
        ProcessDeployment deployment = deploymentRepository.findByDeploymentId(cmd.getDeploymentId());

        updateDeployment(cmd, deployment);

        loadProcessElementsInCache(deployment);

        return deployment.getDeploymentId();
    }

    @Override
    public ProcessDeployment findDeployedByDeploymentVersionId(String deploymentVersionId) {
        return deploymentRepository.findByDeploymentVersionId(deploymentVersionId,true);
    }

    private void loadProcessElementsInCache(ProcessDeployment deployment) {
        List<BaseElement> elements = processEngineConfiguration.getElementParser().parse(deployment.getMetaInfo());
        processEngineConfiguration.getGlobalContext().putProcessElements(deployment.getDeploymentVersionId(),elements);
    }


    @Override
    public void setProcessEngineConfiguration(ProcessEngineConfiguration processEngineConfiguration) {
        this.processEngineConfiguration = processEngineConfiguration;
    }

    @Override
    public ProcessEngineConfiguration getProcessEngineConfiguration() {
        return this.processEngineConfiguration;
    }

    @Override
    public void setProcessRepository(ProcessRepository processRepository) {
        this.deploymentRepository = (DeploymentRepository) processRepository;
    }

    @Override
    public Class<? extends ProcessRepository> allowAcceptRepositoryClass() {
        return DeploymentRepository.class;
    }

    private void updateDeployment(DeployDeploymentCmd cmd, ProcessDeployment deployment) {
        deployment.setDeployStatus(true);
        deployment.setDeployTime(LocalDateTime.now());
        deployment.setDeployBy(cmd.getDeployBy());
        deployment.setRev(ProcessConstant.VERSION_COUNTER);
        int count = deploymentRepository.updateDeploymentById(deployment);
        if (count != 1){
            throw new InfiniteEngineException(String.format("deploy failed,this deployment may be modifying or not find,id:%s", deployment.getId()));
        }
    }


    private ProcessDeployment buildCreateProcessDefinition(CreateDeploymentCmd cmd) {
        ProcessDeployment processDeployment = new ProcessDeployment();
        IdGenerator idGenerator = processEngineConfiguration.getIdGenerator();
        String deploymentId = idGenerator.nextId();
        processDeployment.setDeploymentId(deploymentId);
        processDeployment.setVersion(ProcessConstant.VERSION_COUNTER);
        processDeployment.setDeploymentVersionId(idGenerator.formatVersionId(deploymentId, ProcessConstant.VERSION_COUNTER));
        processDeployment.setName(cmd.getName());
        processDeployment.setMetaInfo(cmd.getMetaInfo());
        processDeployment.setRemark(cmd.getRemark());
        processDeployment.setCategory(cmd.getCategory());
        processDeployment.setCreateBy(cmd.getCreateBy());
        processDeployment.setUpdateBy(cmd.getCreateBy());
        processDeployment.setDeployStatus(false);
        LocalDateTime now = LocalDateTime.now();
        processDeployment.setCreateTime(now);
        processDeployment.setUpdateTime(now);
        return processDeployment;
    }


}
