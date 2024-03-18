package com.czertainly.api.model.core.rules;

import com.czertainly.api.model.core.auth.Resource;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class UpdateActionGroupRequestDto {


    @Schema(
            description = "Description of the Rule Action Group"
    )
    private String description;


    @Schema(
            description = "List of new Rule Actions to add in the Rule Actions Group",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<RuleActionRequestDto> actions;

}
