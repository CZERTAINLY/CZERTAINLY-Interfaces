package com.czertainly.api.model.core.rules;

import com.czertainly.api.model.core.search.SearchCondition;
import com.czertainly.api.model.core.search.SearchGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RuleConditionDto {

    @Schema(
            description = "UUID of the Rule Condition",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String uuid;

    @Schema(
            description = "UUID of the Rule Condition Group containing the Rule Condition"
    )
    private String ruleConditionGroupUuid;

    @Schema(
            description = "UUID of the Rule containing the Rule Condition"
    )
    private String ruleUuid;

    @Schema(
            description = "Group of the Condition"
    )
    private SearchGroup search_group;

    @Schema(
            description = "Field identifier of the Rule Condition"
    )
    private String fieldIdentifier;

    @Schema(
            description = "Operator of the Rule Condition"
    )
    private SearchCondition operator;

    @Schema(
            description = "Value of the Rule Condition"
    )
    private Object value;
}
