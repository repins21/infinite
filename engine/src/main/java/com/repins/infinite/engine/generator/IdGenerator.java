package com.repins.infinite.engine.generator;

public interface IdGenerator {
    String nextId();

    String formatVersionId(String id, Integer version);
}
