package com.repins.infinite.engine.validator;

import com.repins.infinite.engine.exception.InfiniteIllegalArgumentException;

public class DefaultProcessValidator implements ProcessValidator {
    @Override
    public Boolean validate(String metaInfo) {
        //todo implements validate
        if (metaInfo == null){
            throw  new InfiniteIllegalArgumentException("metaInfo can not be null");
        }
        return true;
    }
}
