package com.czertainly.api.model.core.rules;

import com.czertainly.api.model.core.auth.Resource;
import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class RuleDto {

    @Schema(
            description = "UUID of the Rule",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String uuid;

    @Schema(
            description = "UUID of the Compliance Connector"
    )
    private String connector_uuid;

    @Schema(
            description = "Name of the Rule",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String name;

    @Schema(
            description = "Resource associated with the Rule",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Resource resource;

    @Schema(
            description = "Type of the Resource associated with the Rule"
    )
    private String resourceType;

    @Schema(
            description = "Format of the Resource associated with the Rule"
    )
    private String resourceFormat;

    @Schema(
            description = "Attributes of the Rule"
    )
    private String attributes;

    @Schema(
            description = "List of conditions in the Rule"
    )
    private List<RuleConditionDto> conditions;

    @Schema(
            description = "List of condition groups in the Rule"
    )
    private List<RuleConditionGroupDto> conditionGroups;

}
