package com.repins.infinite.engine.generator;

import java.util.UUID;

public class DefaultUuidGenerator implements IdGenerator {
    @Override
    public String nextId() {
        // todo just for now
        return UUID.randomUUID().toString();
    }

    @Override
    public String formatVersionId(String id, Integer version) {
        return id + ":" + version;
    }
}
