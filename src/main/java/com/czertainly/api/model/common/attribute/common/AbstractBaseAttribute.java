package com.czertainly.api.model.common.attribute.common;

public abstract class AbstractBaseAttribute {
    public abstract <T> T getContent();

    public abstract String getUuid();
    public abstract String getName();
    public abstract String getDescription();
    public abstract AttributeType getType();

    public abstract int getVersion();

}
