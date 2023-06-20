package com.repins.infinite.engine.db.repository;

import com.repins.infinite.engine.model.ProcessInstance;

public interface ProcessInstanceRepository extends ProcessRepository {
    void insertProcessInstance(ProcessInstance processInstance);

    void updateProcessInstance(ProcessInstance completedProcessInstance);

    ProcessInstance selectByProcessInstanceIdAndState(String processInstanceId,Integer state);
}
