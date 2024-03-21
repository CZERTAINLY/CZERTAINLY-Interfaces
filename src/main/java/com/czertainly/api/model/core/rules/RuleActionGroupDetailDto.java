package com.czertainly.api.model.core.rules;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class RuleActionGroupDetailDto extends RuleActionGroupDto {

    @Schema(
            description = "List of Rule Actions in the Rule Actions Group",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<RuleActionDto> actions;

}
