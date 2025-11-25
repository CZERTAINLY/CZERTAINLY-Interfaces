package com.czertainly.api.model.common.attribute.common;

import com.czertainly.api.model.common.attribute.v2.AttributeType;

public interface Attribute {

    String getUuid();

    int getVersion();

    String getName();

    AttributeType getType();
}
