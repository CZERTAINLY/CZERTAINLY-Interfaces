package com.czertainly.api.model.core.rules;

import com.czertainly.api.model.common.attribute.v1.ResponseAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class RuleDetailDto extends RuleDto {

    @Schema(
            description = "List of conditions in the Rule",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<RuleConditionDto> conditions;

    @Schema(
            description = "List of condition groups in the Rule",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<RuleConditionGroupDto> conditionGroups;

}
