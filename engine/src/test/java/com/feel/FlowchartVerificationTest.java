package com.feel;

import com.repins.infinite.engine.element.base.BaseElement;
import com.repins.infinite.engine.element.enums.ElementTypeEnum;
import com.repins.infinite.engine.exception.InfiniteIllegalArgumentException;
import com.repins.infinite.engine.paser.DefaultJacksonElementParser;
import org.junit.jupiter.api.Test;
import org.springframework.util.ObjectUtils;

import java.util.List;
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
                "        \"outgoing\":[\"SEQUENCE_FLOW3\"],\n" +
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

        List<BaseElement> baseElementList = new DefaultJacksonElementParser().parse(json);
        verify(baseElementList);

    }

    public void verify(List<BaseElement> baseElementList) {
        // 校验只能有一个开始节点
        verifyStartNode(baseElementList);

        // 校验每个节点的后一个元素必须是箭头sequenceFlow
        verifyNodeAfter(baseElementList);

        // 校验sequenceFlow的后一个元素必须是节点
        verifySequenceFlowAfter(baseElementList);

        // 结束节点后不能有任何元素
        verifyEndNode(baseElementList);
    }

    private static void verifyStartNode(List<BaseElement> baseElementList) {
        List<BaseElement> startNodeList = baseElementList.stream()
                .filter(baseElement -> baseElement.getType().equals(ElementTypeEnum.START_EVENT.getType()))
                .distinct()
                .collect(Collectors.toList());
        if (startNodeList.size() > 1) {
            throw new InfiniteIllegalArgumentException("开始节点存在多个");
        }
        if (!ObjectUtils.isEmpty(startNodeList.get(0).getIncoming())) {
            throw new InfiniteIllegalArgumentException("开始节点前不能有元素");
        }
    }

    private static void verifyNodeAfter(List<BaseElement> baseElementList) {
        List<BaseElement> NodeList = baseElementList.stream()
                .filter(baseElement -> !baseElement.getType().equals(ElementTypeEnum.SEQUENCE_FLOW.getType()))
                .filter(baseElement -> !baseElement.getType().equals(ElementTypeEnum.END_EVENT.getType()))
                .collect(Collectors.toList());
        // 查出node后面的元素
        List<BaseElement> nodeAfterList = baseElementList.stream()
                .filter(baseElement -> {
                    for (BaseElement data : NodeList) {
                        // 开始或者任务节点后可能会有多个箭头
                        List<String> outgoingList = data.getOutgoing();
                        for (String s : outgoingList) {
                            if (baseElement.getKey().equals(s)) {
                                return true;
                            }
                        }
                    }
                    return false;
                })
                .collect(Collectors.toList());
        nodeAfterList.forEach(data -> {
            if (!data.getType().equals(ElementTypeEnum.SEQUENCE_FLOW.getType())) {
                throw new InfiniteIllegalArgumentException("节点后的元素不是sequenceflow");
            }
        });
    }

    private static void verifySequenceFlowAfter(List<BaseElement> baseElementList) {
        List<BaseElement> sequenceFlowList = baseElementList.stream()
                .filter(baseElement -> baseElement.getType().equals(ElementTypeEnum.SEQUENCE_FLOW.getType()))
                .collect(Collectors.toList());
        // 查出sequenceFlow后面的元素
        List<BaseElement> flowAfterList = baseElementList.stream()
                .filter(baseElement -> {
                    for (BaseElement data : sequenceFlowList) {
                        if (baseElement.getKey().equals(data.getOutgoing().get(0))) {
                            return true;
                        }
                    }
                    return false;
                })
                .collect(Collectors.toList());
        // 如果sequenceFlow后面的元素是sequenceFlow，说明不是一个节点元素，其他情况都视为它后面是一个节点元素
        flowAfterList.forEach(data -> {
            if (data.getType().equals(ElementTypeEnum.SEQUENCE_FLOW.getType())) {
                throw new InfiniteIllegalArgumentException("sequenceFlow的后一个元素不是节点");
            }
        });
    }

    private static void verifyEndNode(List<BaseElement> baseElementList) {
        List<BaseElement> endNodeList = baseElementList.stream()
                .filter(baseElement -> baseElement.getType().equals(ElementTypeEnum.END_EVENT.getType()))
                .collect(Collectors.toList());
        if (endNodeList.size() > 1) {
            throw new InfiniteIllegalArgumentException("结束节点存在多个");
        }
        if (!ObjectUtils.isEmpty(endNodeList.get(0).getOutgoing())) {
            throw new InfiniteIllegalArgumentException("结束节点后不能有元素");
        }
    }
}
