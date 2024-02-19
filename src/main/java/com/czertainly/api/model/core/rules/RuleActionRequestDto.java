package com.czertainly.api.model.core.rules;

import com.czertainly.api.model.core.search.SearchGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RuleActionRequestDto {

    @Schema(
            description = "Action Type of the Rule Action,",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private RuleActionType actionType;

    @Schema(
            description = "Group of the Rule Action"
    )
    private SearchGroup search_group;

    @Schema(
            description = "Field identifier of the Rule Action"
    )
    private String fieldIdentifier;

    @Schema(
            description = "Value of the Rule Action"
    )
    private Object value;

}
