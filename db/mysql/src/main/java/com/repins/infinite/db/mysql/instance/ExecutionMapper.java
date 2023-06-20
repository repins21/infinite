package com.repins.infinite.db.mysql.instance;

import com.repins.infinite.engine.db.repository.ExecutionRepository;
import com.repins.infinite.engine.model.Execution;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ExecutionMapper extends ExecutionRepository {
    void insertBatchExecution(@Param("executions") List<Execution> executions);

    @Override
    default void updateBatchExecutions(List<Execution> updateExecutions){
        if (updateExecutions.isEmpty()){
            return;
        }
        updateExecutions.forEach(this::updateById);
    }

    void updateById(Execution execution);
}
