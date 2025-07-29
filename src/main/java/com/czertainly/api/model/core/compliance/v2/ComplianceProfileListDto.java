package com.czertainly.api.model.core.compliance.v2;

import com.czertainly.api.model.common.NameAndUuidDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ComplianceProfileListDto extends NameAndUuidDto {

    @Schema(description = "Compliance Profile description")
    private String description;

    @Schema(description = "Provider (external) rules count", requiredMode = Schema.RequiredMode.REQUIRED)
    private int providerRulesCount;

    @Schema(description = "Provider (external) groups count", requiredMode = Schema.RequiredMode.REQUIRED)
    private int providerGroupsCount;

    @Schema(description = "Workflow (internal) rules count", requiredMode = Schema.RequiredMode.REQUIRED)
    private int internalRulesCount;

    @Schema(description = "Number of associated objects", requiredMode = Schema.RequiredMode.REQUIRED)
    private int associations;
}
