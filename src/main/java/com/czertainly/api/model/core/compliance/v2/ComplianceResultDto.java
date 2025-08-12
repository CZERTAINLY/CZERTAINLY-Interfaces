package com.czertainly.api.model.core.compliance.v2;

import com.czertainly.api.model.core.compliance.ComplianceStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Getter
@Setter
@ToString
public class ComplianceResultDto implements Serializable {

    @Schema(description = "Overall compliance result status", requiredMode = Schema.RequiredMode.REQUIRED)
    private ComplianceStatus status;

    @Schema(description = "Date of the most recent compliance check", requiredMode = Schema.RequiredMode.REQUIRED)
    private OffsetDateTime timestamp;

    @Schema(description = "List of internal rules", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private ComplianceResultRulesDto internalRules;

    @Schema(description = "List of groups", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private ComplianceResultRulesDto providerRules;

}