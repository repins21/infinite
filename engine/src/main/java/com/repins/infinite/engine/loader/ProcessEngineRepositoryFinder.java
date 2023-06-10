package com.repins.infinite.engine.loader;

import com.repins.infinite.engine.db.repository.ProcessRepository;

public interface ProcessEngineRepositoryFinder {

    ProcessRepository findRepository(Class<? extends ProcessRepository> clazz);

}
