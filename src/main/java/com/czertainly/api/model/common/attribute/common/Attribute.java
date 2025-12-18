package com.czertainly.api.model.common.attribute.common;


public interface Attribute {

    String getUuid();

    String getName();
    AttributeType getType();

    int getVersion();
}
