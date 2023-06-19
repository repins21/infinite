package com.repins.infinite.engine.context;

import com.repins.infinite.engine.configuration.ProcessEngineConfiguration;
import com.repins.infinite.engine.element.base.BaseElement;
import com.repins.infinite.engine.element.event.StartEvent;
import com.repins.infinite.engine.executor.factory.AbstractActivityExecutorFactory;
import com.repins.infinite.engine.model.Execution;
import com.repins.infinite.engine.model.ProcessInstance;
import com.repins.infinite.engine.model.TaskInstance;

import java.util.ArrayList;
import java.util.List;

public class RuntimeContext {

    private StartEvent startEvent;

    private ProcessInstance processInstance;

    private AbstractActivityExecutorFactory activityExecutorFactory;

    private GlobalContext globalContext;

    private List<TaskInstance> persistentTasks;

    private List<TaskInstance> updateTasks;

    private List<Execution> persistentExecutions;

    private List<Execution> updateExecutions;

    private BaseElement preElement;

    private BaseElement curElement;

    private TaskInstance curTaskInstance;

    private TaskInstance preTaskInstance;


    private ProcessEngineConfiguration processEngineConfiguration;


    public List<Execution> getUpdateExecutions() {
        if (this.updateExecutions == null){
            this.updateExecutions = new ArrayList<>();
        }
        return this.updateExecutions;
    }

    public void setUpdateExecutions(List<Execution> updateExecutions) {
        this.updateExecutions = updateExecutions;
    }

    public List<TaskInstance> getUpdateTasks() {
        if (this.updateTasks == null){
            this.updateTasks = new ArrayList<>();
        }
        return this.updateTasks;
    }

    public void setUpdateTasks(List<TaskInstance> updateTasks) {
        this.updateTasks = updateTasks;
    }

    public List<Execution> getPersistentExecutions() {
        if (persistentExecutions == null){
            persistentExecutions = new ArrayList<>();
        }
        return persistentExecutions;
    }

    public void setPersistentExecutions(List<Execution> persistentExecutions) {
        this.persistentExecutions = persistentExecutions;
    }

    public TaskInstance getPreTaskInstance() {
        return preTaskInstance;
    }

    public void setPreTaskInstance(TaskInstance preTaskInstance) {
        this.preTaskInstance = preTaskInstance;
    }

    public BaseElement getCurElement() {
        return curElement;
    }

    public void setCurElement(BaseElement curElement) {
        this.curElement = curElement;
    }

    public TaskInstance getCurTaskInstance() {
        return curTaskInstance;
    }

    public void setCurTaskInstance(TaskInstance curTaskInstance) {
        this.curTaskInstance = curTaskInstance;
    }

    public ProcessEngineConfiguration getProcessEngineConfiguration() {
        return processEngineConfiguration;
    }

    public void setProcessEngineConfiguration(ProcessEngineConfiguration processEngineConfiguration) {
        this.processEngineConfiguration = processEngineConfiguration;
    }

    public BaseElement getPreElement() {
        return preElement;
    }

    public void setPreElement(BaseElement preElement) {
        this.preElement = preElement;
    }

    public List<TaskInstance> getPersistentTasks() {
        if (persistentTasks == null){
            persistentTasks =  new ArrayList<>();
        }
        return persistentTasks;
    }

    public void setPersistentTasks(List<TaskInstance> persistentTasks) {
        this.persistentTasks = persistentTasks;
    }

    public GlobalContext getGlobalContext() {
        return globalContext;
    }

    public void setGlobalContext(GlobalContext globalContext) {
        this.globalContext = globalContext;
    }

    public StartEvent getStartEvent() {
        return startEvent;
    }

    public AbstractActivityExecutorFactory getActivityExecutorFactory() {
        return activityExecutorFactory;
    }

    public void setActivityExecutorFactory(AbstractActivityExecutorFactory activityExecutorFactory) {
        this.activityExecutorFactory = activityExecutorFactory;
    }

    public void setStartEvent(StartEvent startEvent) {
        this.startEvent = startEvent;
    }

    public ProcessInstance getProcessInstance() {
        return processInstance;
    }

    public void setProcessInstance(ProcessInstance processInstance) {
        this.processInstance = processInstance;
    }
}
