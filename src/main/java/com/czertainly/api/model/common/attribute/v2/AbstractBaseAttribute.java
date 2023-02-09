package com.czertainly.api.model.common.attribute.v2;

public abstract class AbstractBaseAttribute {
    public abstract <T extends Object> T getContent();
    public abstract String getUuid();
    public abstract String getName();
    public abstract String getDescription();
    public abstract AttributeType getType();

}
