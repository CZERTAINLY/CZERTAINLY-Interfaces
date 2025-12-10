package com.czertainly.api.model.common.attribute.common;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;

public abstract class AbstractBaseAttribute {
    public abstract <T> T getContent();

    @Schema(hidden = true)
    @Hidden
    public abstract AttributeVersion getSchemaVersion();

    public abstract String getUuid();
    public abstract String getName();
    public abstract String getDescription();
    public abstract AttributeType getType();

    @Schema(hidden = true)
    @Hidden
    public int version;

}
