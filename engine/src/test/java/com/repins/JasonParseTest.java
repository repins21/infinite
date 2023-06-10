package com.repins;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.repins.infinite.engine.paser.DefaultJacksonElementParser;
import org.junit.jupiter.api.Test;

public class JasonParseTest {

    @Test
    public void parse() throws JsonProcessingException {
        String json = "[\n" +
                "    {\n" +
                "        \"key\":\"START_EVENT1\",\n" +
                "        \"name\":\"开始\",\n" +
                "        \"type\":\"START_EVENT\",\n" +
                "        \"outgoing\":[\"SEQUENCE_FLOW1\"]\n" +
                "    },\n" +
                "    {\n" +
                "        \"key\":\"SEQUENCE_FLOW1\",\n" +
                "        \"name\":\"条件1\",\n" +
                "        \"type\":\"SEQUENCE_FLOW\",\n" +
                "        \"outgoing\":[\"TASK1\"],\n" +
                "        \"incoming\":[\"START_EVENT1\"]\n" +
                "    },\n" +
                "    {\n" +
                "        \"key\":\"TASK1\",\n" +
                "        \"name\":\"节点1\",\n" +
                "        \"type\":\"USER_TASK\",\n" +
                "        \"outgoing\":[\"SEQUENCE_FLOW2\"],\n" +
                "        \"incoming\":[\"SEQUENCE_FLOW1\"],\n" +
                "        \"extensionElement\":{\n" +
                "            \"key\":\"1111\",\n" +
                "            \"value\":\"111\",\n" +
                "            \"type\":\"test\"\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"key\":\"SEQUENCE_FLOW2\",\n" +
                "        \"name\":\"条件2\",\n" +
                "        \"type\":\"SEQUENCE_FLOW\",\n" +
                "        \"outgoing\":[\"END1\"],\n" +
                "        \"incoming\":[\"TASK1\"]\n" +
                "    },\n" +
                "    {\n" +
                "        \"key\":\"END1\",\n" +
                "        \"name\":\"结束\",\n" +
                "        \"type\":\"END_EVENT\",\n" +
                "        \"incoming\":[\"SEQUENCE_FLOW1\"]\n" +
                "    }\n" +
                "]";

        Object elements = new DefaultJacksonElementParser().parse(json);
        System.out.println(elements);
    }
}
