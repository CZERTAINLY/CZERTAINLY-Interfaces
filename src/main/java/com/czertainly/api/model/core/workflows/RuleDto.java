package com.czertainly.api.model.core.workflows;

import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.core.auth.Resource;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RuleDto extends NameAndUuidDto {

    @Schema(
            description = "Description of the Rule"
    )
    private String description;

    @Schema(
            description = "Resource associated with the Rule",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Resource resource;
}
