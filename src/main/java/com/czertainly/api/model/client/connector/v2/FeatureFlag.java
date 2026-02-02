package com.czertainly.api.model.client.connector.v2;

import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.fasterxml.jackson.annotation.JsonValue;

public interface FeatureFlag extends IPlatformEnum {

    @Override
    @JsonValue
    String getCode();

}
