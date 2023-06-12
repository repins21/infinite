package com.repins.infinite.engine.db.repository;

import com.repins.infinite.engine.model.Execution;

import java.util.List;

public interface ExecutionRepository extends ProcessRepository {

    void insertBatchExecution(List<Execution> executions);
}
