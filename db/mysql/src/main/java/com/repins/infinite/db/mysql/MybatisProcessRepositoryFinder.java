package com.repins.infinite.db.mysql;

import com.repins.infinite.engine.db.repository.ProcessRepository;
import com.repins.infinite.engine.loader.ProcessEngineRepositoryFinder;
import com.repins.infinite.engine.utils.SpringContextHolder;

public class MybatisProcessRepositoryFinder implements ProcessEngineRepositoryFinder {
    @Override
    public ProcessRepository findRepository(Class<? extends ProcessRepository> clazz) {
        return SpringContextHolder.getBean(clazz);
    }
}
