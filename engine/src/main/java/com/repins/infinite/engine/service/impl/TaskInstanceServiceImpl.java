package com.repins.infinite.engine.service.impl;

import com.repins.infinite.engine.configuration.ProcessEngineConfiguration;
import com.repins.infinite.engine.db.repository.ProcessRepository;
import com.repins.infinite.engine.db.repository.TaskInstanceRepository;
import com.repins.infinite.engine.loader.InfiniteService;
import com.repins.infinite.engine.service.TaskInstanceService;

@InfiniteService
public class TaskInstanceServiceImpl implements TaskInstanceService {

    private ProcessEngineConfiguration processEngineConfiguration;
    private ProcessRepository processRepository;

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
        this.processRepository = processRepository;
    }

    @Override
    public Class<? extends ProcessRepository> allowAcceptRepositoryClass() {
        return TaskInstanceRepository.class;
    }
}
