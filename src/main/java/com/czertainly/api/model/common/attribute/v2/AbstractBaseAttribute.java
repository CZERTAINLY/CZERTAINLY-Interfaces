package com.czertainly.api.model.common.attribute.v2;

import com.czertainly.api.model.common.attribute.common.AttributeVersion;

public abstract class AbstractBaseAttribute {
    public abstract <T> T getContent();
    public abstract AttributeVersion getSchemaVersion();
    public abstract String getUuid();
    public abstract String getName();
    public abstract String getDescription();
    public abstract AttributeType getType();

}
