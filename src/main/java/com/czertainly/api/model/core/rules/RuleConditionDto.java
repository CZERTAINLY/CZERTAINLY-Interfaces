package com.czertainly.api.model.core.rules;

import com.czertainly.api.model.core.search.FilterConditionOperator;
import com.czertainly.api.model.core.search.FilterFieldSource;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class RuleConditionDto {

    @Schema(
            description = "UUID of the Rule Condition",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String uuid;

    @Schema(
            description = "Source of the field in the Condition",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private FilterFieldSource fieldSource;

    @Schema(
            description = "Field identifier of the Rule Condition",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String fieldIdentifier;

    @Schema(
            description = "Operator of the Rule Condition",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private FilterConditionOperator operator;

    @Schema(
            description = "Value of the Rule Condition"
    )
    private Object value;
}
