package com.repins.infinite.engine.configuration;

import com.repins.infinite.engine.ProcessEngine;
import com.repins.infinite.engine.behavior.TaskAssigneeBehavior;
import com.repins.infinite.engine.context.GlobalContext;
import com.repins.infinite.engine.db.DatabaseType;
import com.repins.infinite.engine.executor.factory.AbstractActivityExecutorFactory;
import com.repins.infinite.engine.generator.IdGenerator;
import com.repins.infinite.engine.loader.ProcessEngineRepositoryFinder;
import com.repins.infinite.engine.paser.ElementParser;
import com.repins.infinite.engine.loader.ProcessEngineServiceFinder;
import com.repins.infinite.engine.loader.ProcessEngineServiceRegister;
import com.repins.infinite.engine.validator.ProcessValidator;

public interface ProcessEngineConfiguration {
    ProcessEngine getProcessEngine();

    void setProcessEngine(ProcessEngine processEngine);

    void setElementParser(ElementParser elementParser);

    ElementParser getElementParser();


    DatabaseType getDatabaseType();

    void setDatabaseType(DatabaseType databaseType);

    ProcessEngineRepositoryFinder getProcessEngineRepositoryFinder();

    void setProcessEngineRepositoryFinder(ProcessEngineRepositoryFinder finder);


    ProcessEngineServiceRegister getProcessEngineServiceLoader();

    void setProcessEngineServiceRegister(ProcessEngineServiceRegister loader);

    ProcessEngineServiceFinder getProcessEngineServiceFinder();

    void setProcessEngineServiceFinder(ProcessEngineServiceFinder finder);

    ProcessValidator getProcessValidator();

    void setProcessValidator(ProcessValidator validator);


    IdGenerator getIdGenerator();

    void setIdGenerator(IdGenerator idGenerator);


    void setGlobalContext(GlobalContext context);

    GlobalContext getGlobalContext();

    AbstractActivityExecutorFactory getAbstractActivityFactory();

    void setAbstractActivityFactory(AbstractActivityExecutorFactory abstractActivityFactory);

    TaskAssigneeBehavior getTaskAssigneeBehavior();

    void setTaskAssigneeBehavior(TaskAssigneeBehavior taskAssigneeBehavior);

}
