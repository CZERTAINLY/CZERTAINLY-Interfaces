package com.czertainly.core.interfaces;

import com.czertainly.api.model.commons.AttributeDefinition;

import java.util.List;

public interface Connector {

    List<AttributeDefinition> getAttributes();
}
