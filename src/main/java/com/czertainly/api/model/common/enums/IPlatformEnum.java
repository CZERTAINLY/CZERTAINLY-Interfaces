package com.czertainly.api.model.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public interface IPlatformEnum {

    String getCode();

    String getLabel();

    String getDescription();
}
