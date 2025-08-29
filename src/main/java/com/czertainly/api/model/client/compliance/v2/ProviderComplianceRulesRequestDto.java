package com.czertainly.api.model.client.compliance.v2;

import com.czertainly.api.model.connector.compliance.v2.ComplianceRuleRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;

@Getter
@Setter
@ToString
public class ProviderComplianceRulesRequestDto {

    @Schema(description = "UUID of the Compliance Provider", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID connectorUuid;

    @Schema(description = "Kind of the Compliance Provider", requiredMode = Schema.RequiredMode.REQUIRED)
    private String kind;

    @Schema(description = "Provider rules associated", requiredMode = Schema.RequiredMode.REQUIRED)
    private Set<ComplianceRuleRequestDto> rules = new HashSet<>();

    @Schema(description = "Provider groups UUIDS to associate profile with", requiredMode = Schema.RequiredMode.REQUIRED)
    private Set<UUID> groups = new HashSet<>();

}
