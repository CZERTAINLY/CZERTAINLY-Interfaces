package com.czertainly.api.model.common.attribute.common;

import io.swagger.v3.oas.annotations.media.Schema;

public interface Attribute {

    String getUuid();

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    AttributeVersion getSchemaVersion();

    String getName();
    AttributeType getType();
}
