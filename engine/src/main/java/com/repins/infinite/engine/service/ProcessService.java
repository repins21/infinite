package com.repins.infinite.engine.service;

import com.repins.infinite.engine.configuration.ProcessEngineConfiguration;
import com.repins.infinite.engine.db.repository.ProcessRepository;

public interface ProcessService {

    default void autoConfiguration(ProcessEngineConfiguration processEngineConfiguration){
        setProcessEngineConfiguration(processEngineConfiguration);
        setProcessRepository(
            processEngineConfiguration
                .getProcessEngineRepositoryFinder().findRepository(allowAcceptRepositoryClass()));
    }

    void setProcessEngineConfiguration(ProcessEngineConfiguration processEngineConfiguration);

    ProcessEngineConfiguration getProcessEngineConfiguration();

    void setProcessRepository(ProcessRepository processRepository);

    Class<? extends ProcessRepository> allowAcceptRepositoryClass();
}
