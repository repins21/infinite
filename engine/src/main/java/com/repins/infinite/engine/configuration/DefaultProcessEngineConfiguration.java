package com.repins.infinite.engine.configuration;

import com.repins.infinite.engine.ProcessEngine;
import com.repins.infinite.engine.behavior.DefaultTaskBehaviorFactory;
import com.repins.infinite.engine.behavior.TaskAssigneeBehavior;
import com.repins.infinite.engine.behavior.TaskBehaviorFactory;
import com.repins.infinite.engine.cache.local.LocalDeploymentRuntimeCacheStrategy;
import com.repins.infinite.engine.context.GlobalContext;
import com.repins.infinite.engine.db.DatabaseType;
import com.repins.infinite.engine.executor.factory.AbstractActivityExecutorFactory;
import com.repins.infinite.engine.executor.factory.DefaultActivityExecutorFactory;
import com.repins.infinite.engine.generator.DefaultUuidGenerator;
import com.repins.infinite.engine.generator.IdGenerator;
import com.repins.infinite.engine.loader.ProcessEngineRepositoryFinder;
import com.repins.infinite.engine.paser.DefaultJacksonElementParser;
import com.repins.infinite.engine.paser.ElementParser;
import com.repins.infinite.engine.loader.DefaultProcessEngineServiceFinder;
import com.repins.infinite.engine.loader.DefaultProcessEngineServiceRegister;
import com.repins.infinite.engine.loader.ProcessEngineServiceFinder;
import com.repins.infinite.engine.loader.ProcessEngineServiceRegister;
import com.repins.infinite.engine.validator.DefaultProcessValidator;
import com.repins.infinite.engine.validator.ProcessValidator;

public class DefaultProcessEngineConfiguration implements ProcessEngineConfiguration {

    private DatabaseType databaseType;
    private ElementParser elementParser;
    private ProcessEngineRepositoryFinder processEngineRepositoryFinder;
    private ProcessEngineServiceRegister processEngineServiceRegister;
    private ProcessEngineServiceFinder processEngineServiceFinder;
    private ProcessValidator processValidator;
    private IdGenerator idGenerator;
    private ProcessEngine processEngine;
    private GlobalContext globalContext;
    private AbstractActivityExecutorFactory activityFactory;
    private TaskBehaviorFactory taskBehaviorFactory;


    public DefaultProcessEngineConfiguration() {

    }

    public static DefaultProcessEngineConfiguration defaultConfig() {
        DefaultProcessEngineConfiguration processEngineConfiguration = new DefaultProcessEngineConfiguration();
        processEngineConfiguration.setDatabaseType(DatabaseType.MYSQL);
        processEngineConfiguration.setElementParser(new DefaultJacksonElementParser());
        processEngineConfiguration.setProcessEngineServiceRegister(new DefaultProcessEngineServiceRegister());
        processEngineConfiguration.setProcessEngineServiceFinder(new DefaultProcessEngineServiceFinder());
        processEngineConfiguration.setProcessValidator(new DefaultProcessValidator());
        processEngineConfiguration.setIdGenerator(new DefaultUuidGenerator());
        processEngineConfiguration.setGlobalContext(new GlobalContext(new LocalDeploymentRuntimeCacheStrategy()));
        processEngineConfiguration.setAbstractActivityFactory(new DefaultActivityExecutorFactory());
        processEngineConfiguration.setTaskBehaviorFactory(new DefaultTaskBehaviorFactory());
        return processEngineConfiguration;
    }


    @Override
    public ProcessEngine getProcessEngine() {
        return this.processEngine;
    }

    @Override
    public void setProcessEngine(ProcessEngine processEngine) {
        this.processEngine = processEngine;
    }

    @Override
    public void setElementParser(ElementParser elementParser) {
        this.elementParser = elementParser;
    }

    @Override
    public ElementParser getElementParser() {
        return this.elementParser;
    }

    @Override
    public DatabaseType getDatabaseType() {

        return this.databaseType;
    }

    @Override
    public void setDatabaseType(DatabaseType databaseType) {
        this.databaseType = databaseType;
    }

    @Override
    public ProcessEngineRepositoryFinder getProcessEngineRepositoryFinder() {
        return this.processEngineRepositoryFinder;
    }

    @Override
    public void setProcessEngineRepositoryFinder(ProcessEngineRepositoryFinder register) {
        this.processEngineRepositoryFinder = register;
    }


    @Override
    public ProcessEngineServiceRegister getProcessEngineServiceLoader() {
        return this.processEngineServiceRegister;
    }

    @Override
    public void setProcessEngineServiceRegister(ProcessEngineServiceRegister loader) {
        this.processEngineServiceRegister = loader;
    }

    @Override
    public ProcessEngineServiceFinder getProcessEngineServiceFinder() {
        return this.processEngineServiceFinder;
    }

    @Override
    public void setProcessEngineServiceFinder(ProcessEngineServiceFinder finder) {
        this.processEngineServiceFinder = finder;
    }

    @Override
    public ProcessValidator getProcessValidator() {
        return this.processValidator;
    }

    @Override
    public void setProcessValidator(ProcessValidator validator) {
        this.processValidator = validator;
    }

    @Override
    public IdGenerator getIdGenerator() {
        return this.idGenerator;
    }

    @Override
    public void setIdGenerator(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public GlobalContext getGlobalContext() {
        return globalContext;
    }

    @Override
    public AbstractActivityExecutorFactory getAbstractActivityFactory() {
        return this.activityFactory;
    }

    @Override
    public void setAbstractActivityFactory(AbstractActivityExecutorFactory abstractActivityFactory) {
        this.activityFactory = abstractActivityFactory;
    }


    @Override
    public void setGlobalContext(GlobalContext globalContext) {
        this.globalContext = globalContext;
    }

    @Override
    public TaskBehaviorFactory getTaskBehaviorFactory() {
        return this.taskBehaviorFactory;
    }

    @Override
    public void setTaskBehaviorFactory(TaskBehaviorFactory taskBehaviorFactory) {
        this.taskBehaviorFactory = taskBehaviorFactory;
    }
}
