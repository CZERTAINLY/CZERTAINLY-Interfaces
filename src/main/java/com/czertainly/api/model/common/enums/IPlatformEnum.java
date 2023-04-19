package com.czertainly.api.model.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public interface IPlatformEnum {

    @JsonValue
    String getCode();

    String getLabel();

    String getDescription();
}
