package com.repins.infinite.db.mysql.instance;

import com.repins.infinite.engine.db.repository.ProcessInstanceRepository;
import com.repins.infinite.engine.model.ProcessInstance;
import com.repins.infinite.engine.state.ProcessInstanceState;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProcessInstanceMapper extends ProcessInstanceRepository {
    void insertProcessInstance(ProcessInstance processInstance);


    void updateProcessInstance(ProcessInstance completedProcessInstance);

    ProcessInstance selectByProcessInstanceIdAndState(@Param("processInstanceId") String processInstanceId,
                                                      @Param("state") Integer state);

}
