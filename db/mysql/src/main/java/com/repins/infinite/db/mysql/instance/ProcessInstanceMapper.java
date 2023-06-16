package com.repins.infinite.db.mysql.instance;

import com.repins.infinite.engine.db.repository.ProcessInstanceRepository;
import com.repins.infinite.engine.model.ProcessInstance;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProcessInstanceMapper extends ProcessInstanceRepository {
    void insertProcessInstance(ProcessInstance processInstance);


     void updateProcessInstance(ProcessInstance completedProcessInstance);

}
