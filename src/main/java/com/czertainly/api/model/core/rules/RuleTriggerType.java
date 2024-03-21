package com.czertainly.api.model.core.rules;

import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true)
public enum RuleTriggerType implements IPlatformEnum {

    EVENT("event", "Event", null),
    MANUAL("manual", "Manual", null),
    ;

    private final String code;

    private final String label;

    private final String description;

    RuleTriggerType(String code, String label, String description) {
        this.code = code;
        this.label = label;
        this.description = description;
    }


    @Override
    @JsonValue
    public String getCode() {
        return this.code;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}
