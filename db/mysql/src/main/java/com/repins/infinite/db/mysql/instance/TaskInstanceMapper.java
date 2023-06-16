package com.repins.infinite.db.mysql.instance;

import com.repins.infinite.engine.db.repository.TaskInstanceRepository;
import com.repins.infinite.engine.model.TaskInstance;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskInstanceMapper extends TaskInstanceRepository {
    void insertBatchTaskInstances(@Param("taskInstances") List<TaskInstance> taskInstances);

    TaskInstance selectByTaskIdAndProcessInstanceId(@Param("taskId") String taskId,
                                                    @Param("processInstanceId") String processInstanceId);

    @Override
    default void updateBatchTaskInstances(List<TaskInstance> updateTasks) {
        if (updateTasks.isEmpty()){
            return;
        }
        updateTasks.forEach(this::updateById);
    }

    void updateById(TaskInstance taskInstance);
}
