package com.repins.infinite.engine;

import com.repins.infinite.engine.configuration.ProcessEngineConfiguration;

public abstract class AbstractProcessEngine implements ProcessEngine {
    protected ProcessEngineConfiguration processEngineConfiguration;


    @Override
    public void init(ProcessEngineConfiguration processEngineConfiguration) {
        this.processEngineConfiguration = processEngineConfiguration;
        this.processEngineConfiguration.setProcessEngine(this);
        loadServices();
    }

    protected void loadServices(){
        processEngineConfiguration.getProcessEngineServiceLoader().loadServices(this.processEngineConfiguration);
    }


    @Override
    public ProcessEngineConfiguration getProcessEngineConfiguration() {
        return processEngineConfiguration;
    }

    @Override
    public void setProcessEngineConfiguration(ProcessEngineConfiguration processEngineConfiguration) {
        this.processEngineConfiguration = processEngineConfiguration;
    }


}
