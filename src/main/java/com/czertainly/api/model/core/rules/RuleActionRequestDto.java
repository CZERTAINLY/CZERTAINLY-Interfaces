package com.czertainly.api.model.core.rules;

import com.czertainly.api.model.core.search.FilterFieldSource;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class RuleActionRequestDto {

    @Schema(
            description = "Action Type of the Rule Action,",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private RuleActionType actionType;

    @Schema(
            description = "Source of the field in the Rule Action"
    )
    private FilterFieldSource fieldSource;

    @Schema(
            description = "Field identifier of the Rule Action"
    )
    private String fieldIdentifier;

    @Schema(
            description = "Data for the Rule Action"
    )
    private Serializable actionData;

}
