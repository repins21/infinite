package com.repins.infinite.engine.service;

import com.repins.infinite.engine.command.StartProcessInstanceCmd;

public interface ProcessInstanceService extends ProcessService {
    String startProcessInstance(StartProcessInstanceCmd cmd);
}
