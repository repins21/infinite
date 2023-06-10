package com.repins.infinite.engine.db.repository;

import com.repins.infinite.engine.model.TaskInstance;

import java.util.List;

public interface TaskInstanceRepository extends ProcessRepository {
    void insertBatchTaskInstances(List<TaskInstance> taskInstances);
}
