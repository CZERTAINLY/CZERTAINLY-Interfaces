package com.czertainly.api.model.client.compliance.v2;

import com.czertainly.api.model.connector.compliance.v2.ComplianceRuleRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
public class ProviderComplianceRulesRequestDto {

    @Schema(description = "UUID of the Compliance Provider", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID connectorUuid;

    @Schema(description = "Kind of the Compliance Provider", requiredMode = Schema.RequiredMode.REQUIRED)
    private String kind;

    @Schema(description = "Provider rules associated", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<ComplianceRuleRequestDto> rules = new ArrayList<>();

    @Schema(description = "Provider groups UUIDS to associate profile with", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<UUID> groups = new ArrayList<>();

}
