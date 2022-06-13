package com.czertainly.core.interfaces;

import com.czertainly.api.model.common.attribute.AttributeDefinition;

import java.util.List;

public interface Connector {

    List<AttributeDefinition> getAttributes();
}
