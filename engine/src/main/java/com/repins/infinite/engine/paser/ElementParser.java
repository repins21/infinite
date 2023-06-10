package com.repins.infinite.engine.paser;

import com.repins.infinite.engine.element.base.BaseElement;

import java.util.List;

public interface ElementParser {

    List<BaseElement> parse(String metaInfo);
}
