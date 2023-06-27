package com.feel;

import com.repins.infinite.engine.element.base.BaseElement;
import com.repins.infinite.engine.element.enums.ElementTypeEnum;
import com.repins.infinite.engine.exception.InfiniteIllegalArgumentException;
import com.repins.infinite.engine.paser.DefaultJacksonElementParser;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FlowchartVerificationTest {


    @Test
    public void validated() {
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
                "        \"outgoing\":[\"TASK2\"],\n" +
                "        \"incoming\":[\"TASK1\"]\n" +
                "    },\n" +
                "    {\n" +
                "        \"key\":\"TASK2\",\n" +
                "        \"name\":\"节点2\",\n" +
                "        \"type\":\"USER_TASK\",\n" +
                "        \"outgoing\":[\"SEQUENCE_FLOW3\"],\n" +
                "        \"incoming\":[\"SEQUENCE_FLOW2\"],\n" +
                "        \"extensionElement\":{\n" +
                "            \"key\":\"1111\",\n" +
                "            \"value\":\"111\",\n" +
                "            \"type\":\"test\"\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"key\":\"SEQUENCE_FLOW3\",\n" +
                "        \"name\":\"条件3\",\n" +
                "        \"type\":\"SEQUENCE_FLOW\",\n" +
                "        \"outgoing\":[\"END1\"],\n" +
                "        \"incoming\":[\"TASK2\"]\n" +
                "    },\n" +
                "    {\n" +
                "        \"key\":\"END1\",\n" +
                "        \"name\":\"结束\",\n" +
                "        \"type\":\"END_EVENT\",\n" +
                "        \"incoming\":[\"SEQUENCE_FLOW1\"]\n" +
                "    }\n" +
                "]";

//        String json = "[\n" +
//                "    {\n" +
//                "        \"key\":\"START_EVENT1\",\n" +
//                "        \"name\":\"开始\",\n" +
//                "        \"type\":\"START_EVENT\",\n" +
//                "        \"outgoing\":[\"SEQUENCE_FLOW1\"]\n" +
//                "    },\n" +
//                "    {\n" +
//                "        \"key\":\"SEQUENCE_FLOW1\",\n" +
//                "        \"name\":\"条件1\",\n" +
//                "        \"type\":\"SEQUENCE_FLOW\",\n" +
//                "        \"outgoing\":[\"TASK1\"],\n" +
//                "        \"incoming\":[\"START_EVENT1\"]\n" +
//                "    },\n" +
//                "    {\n" +
//                "        \"key\":\"TASK1\",\n" +
//                "        \"name\":\"节点1\",\n" +
//                "        \"type\":\"USER_TASK\",\n" +
//                "        \"outgoing\":[\"SEQUENCE_FLOW2\"],\n" +
//                "        \"incoming\":[\"SEQUENCE_FLOW1\"],\n" +
//                "        \"extensionElement\":{\n" +
//                "            \"key\":\"1111\",\n" +
//                "            \"value\":\"111\",\n" +
//                "            \"type\":\"test\"\n" +
//                "        }\n" +
//                "    },\n" +
//                "    {\n" +
//                "        \"key\":\"SEQUENCE_FLOW2\",\n" +
//                "        \"name\":\"条件2\",\n" +
//                "        \"type\":\"SEQUENCE_FLOW\",\n" +
//                "        \"outgoing\":[\"END1\"],\n" +
//                "        \"incoming\":[\"TASK1\"]\n" +
//                "    },\n" +
//                "    {\n" +
//                "        \"key\":\"END1\",\n" +
//                "        \"name\":\"结束\",\n" +
//                "        \"type\":\"END_EVENT\",\n" +
//                "        \"incoming\":[\"SEQUENCE_FLOW1\"]\n" +
//                "    }\n" +
//                "]";

        List<BaseElement> baseElementList = new DefaultJacksonElementParser().parse(json);
        verify(baseElementList);
    }

    public void verify(List<BaseElement> baseElementList) {
        List<BaseElement> startNodeList = baseElementList.stream()
                .filter(baseElement -> baseElement.getType().equals(ElementTypeEnum.START_EVENT.getType()))
                .collect(Collectors.toList());
        // 校验开始节点
        verifyStartNode(startNodeList);

        // baseElementList转换成Map结构，key为BaseElement中的key
        Map<String, BaseElement> baseElementMap = baseElementList.stream()
                .collect(Collectors.toMap(BaseElement::getKey, Function.identity()));

        // 开始节点后可能会有多个箭头指向多个节点
        List<String> outgoing = startNodeList.get(0).getOutgoing();
        for (String out : outgoing) {
            // 判断开始节点的outgoing中的节点是否为sequenceFlow
            if (!baseElementMap.get(out).getType().equals(ElementTypeEnum.SEQUENCE_FLOW.getType())) {
                throw new InfiniteIllegalArgumentException("开始节点后的元素不是sequenceFlow");
            }
            verifyRecursion(baseElementMap.get(out), baseElementMap);
        }
    }

    public void verifyRecursion(BaseElement baseElement, Map<String, BaseElement> baseElementMap) {
        if (baseElement.getType().equals(ElementTypeEnum.END_EVENT.getType())) {
            if (Objects.isNull(baseElement.getOutgoing())) {
                return;
            }
            throw new InfiniteIllegalArgumentException("结束节点后不能有元素");
        }

        // 获取当前节点的下一个节点元素
        BaseElement nextElement = baseElementMap.get(baseElement.getOutgoing().get(0));

        if (baseElement.getType().equals(ElementTypeEnum.SEQUENCE_FLOW.getType())) {
            // 下一个节点类型是sequenceFlow则抛出异常
            if (nextElement.getType().equals(ElementTypeEnum.SEQUENCE_FLOW.getType())) {
                throw new InfiniteIllegalArgumentException("sequenceFlow的后一个元素不是节点");
            }
        }
        if (baseElement.getType().equals(ElementTypeEnum.USER_TASK.getType())) {
            // 任务节点后可能会有多个箭头指向多个节点
            for (String out : baseElement.getOutgoing()) {
                if (!baseElementMap.get(out).getType().equals(ElementTypeEnum.SEQUENCE_FLOW.getType())) {
                    throw new InfiniteIllegalArgumentException("任务节点后的元素不是sequenceFlow");
                }
                verifyRecursion(baseElementMap.get(out), baseElementMap);
            }
        }
        verifyRecursion(nextElement, baseElementMap);
    }

    private static void verifyStartNode(List<BaseElement> startNodeList) {
        if (startNodeList.size() > 1) {
            throw new InfiniteIllegalArgumentException("开始节点存在多个");
        }
        if (!ObjectUtils.isEmpty(startNodeList.get(0).getIncoming())) {
            throw new InfiniteIllegalArgumentException("开始节点前不能有元素");
        }
    }
}
