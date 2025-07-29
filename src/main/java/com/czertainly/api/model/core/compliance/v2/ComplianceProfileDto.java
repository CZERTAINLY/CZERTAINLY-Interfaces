package com.czertainly.api.model.core.compliance.v2;

import com.czertainly.api.model.common.NameAndUuidDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ComplianceProfileDto extends NameAndUuidDto {

    @Schema(description = "Description of the Compliance Profile")
    private String description;

    @Schema(description = "List of internal rules", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<ComplianceRuleDto> internalRules;

    @Schema(description = "List of groups", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<ProviderComplianceRulesDto> providerRules;
}
