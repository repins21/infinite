package com.repins.infinite.engine.validator;

import com.repins.infinite.engine.element.base.BaseElement;
import com.repins.infinite.engine.element.enums.ElementTypeEnum;
import com.repins.infinite.engine.exception.InfiniteIllegalArgumentException;
import com.repins.infinite.engine.paser.DefaultJacksonElementParser;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DefaultProcessValidator implements ProcessValidator {
    @Override
    public Boolean validate(String metaInfo) {
        if (metaInfo == null) {
            throw new InfiniteIllegalArgumentException("metaInfo can not be null");
        }

        List<BaseElement> baseElementList = new DefaultJacksonElementParser().parse(metaInfo);
        List<BaseElement> startNodeList = baseElementList.stream()
                .filter(baseElement -> baseElement.getType().equals(ElementTypeEnum.START_EVENT.getType()))
                .collect(Collectors.toList());

        validateStartNode(startNodeList);

        Map<String, BaseElement> baseElementMap = baseElementList.stream()
                .collect(Collectors.toMap(BaseElement::getKey, Function.identity()));

        List<String> outgoing = startNodeList.get(0).getOutgoing();
        for (String out : outgoing) {
            if (!baseElementMap.get(out).getType().equals(ElementTypeEnum.SEQUENCE_FLOW.getType())) {
                throw new InfiniteIllegalArgumentException("the element after the start node is not sequenceFlow");
            }
            validateRecursion(baseElementMap.get(out), baseElementMap);
        }
        return true;
    }

    public void validateRecursion(BaseElement baseElement, Map<String, BaseElement> baseElementMap) {
        if (baseElement.getType().equals(ElementTypeEnum.END_EVENT.getType())) {
            if (Objects.isNull(baseElement.getOutgoing())) {
                return;
            }
            throw new InfiniteIllegalArgumentException("cannot have elements after ending a node");
        }

        BaseElement nextElement = baseElementMap.get(baseElement.getOutgoing().get(0));

        if (baseElement.getType().equals(ElementTypeEnum.SEQUENCE_FLOW.getType())) {
            if (nextElement.getType().equals(ElementTypeEnum.SEQUENCE_FLOW.getType())) {
                throw new InfiniteIllegalArgumentException("the next element of sequenceFlow is not a node");
            }
        }
        if (baseElement.getType().equals(ElementTypeEnum.USER_TASK.getType())) {
            for (String out : baseElement.getOutgoing()) {
                if (!baseElementMap.get(out).getType().equals(ElementTypeEnum.SEQUENCE_FLOW.getType())) {
                    throw new InfiniteIllegalArgumentException("the element after the task node is not sequenceFlow");
                }
                validateRecursion(baseElementMap.get(out), baseElementMap);
            }
        }
        validateRecursion(nextElement, baseElementMap);
    }

    private static void validateStartNode(List<BaseElement> startNodeList) {
        if (startNodeList.size() > 1) {
            throw new InfiniteIllegalArgumentException("multiple start nodes present");
        }
        if (!ObjectUtils.isEmpty(startNodeList.get(0).getIncoming())) {
            throw new InfiniteIllegalArgumentException("cannot have elements before starting a node");
        }
    }
}
