package com.repins.infinite.engine.service;

import com.repins.infinite.engine.command.CompleteTaskCmd;

public interface TaskInstanceService extends ProcessService{
    void complete(CompleteTaskCmd cmd);
}
