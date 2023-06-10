package com.repins.infinite.engine.paser;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.repins.infinite.engine.element.base.BaseElement;
import com.repins.infinite.engine.element.enums.ElementTypeEnum;
import com.repins.infinite.engine.exception.InfiniteEngineException;

import java.util.List;

public class DefaultJacksonElementParser implements ElementParser {

    private final ObjectMapper objectMapper;

    private static final String TYPE_PROPERTY = "type";


    public DefaultJacksonElementParser() {
        objectMapper = new ObjectMapper();
        for (ElementTypeEnum elementTypeEnum : ElementTypeEnum.values()) {
            objectMapper.registerSubtypes(new NamedType(elementTypeEnum.getClazz(), elementTypeEnum.getType()));
        }
        objectMapper.addMixIn(BaseElement.class, JacksonPropertyTypeMixIn.class);
    }


    public List<BaseElement> parse(String metaInfo) {
        try {
            return objectMapper.readValue(metaInfo, objectMapper.getTypeFactory().constructCollectionType(List.class, BaseElement.class));
        } catch (JsonProcessingException e) {
            throw new InfiniteEngineException(String.format("parse element failed, exception:%s", e.getCause() + e.getMessage()));
        }
    }


    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME,include = JsonTypeInfo.As.EXISTING_PROPERTY, property = TYPE_PROPERTY,visible = true)
    private abstract static class JacksonPropertyTypeMixIn {

    }

}
