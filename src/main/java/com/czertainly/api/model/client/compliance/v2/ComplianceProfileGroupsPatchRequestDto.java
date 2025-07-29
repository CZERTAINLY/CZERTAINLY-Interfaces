package com.czertainly.api.model.client.compliance.v2;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class ComplianceProfileGroupsPatchRequestDto {

    @Schema(description = "Indicates if removing or adding group with UUID specified in request", requiredMode = Schema.RequiredMode.REQUIRED, examples = {"1212a-34dddf34-4334f-34ddfvfdg1y3"})
    private boolean removal;

    @Schema(description = "UUID of the group", requiredMode = Schema.RequiredMode.REQUIRED, examples = {"1212a-34dddf34-4334f-34ddfvfdg1y3"})
    private UUID ruleUuid;

    @Schema(description = "UUID of the Compliance Provider", requiredMode = Schema.RequiredMode.REQUIRED, examples = {"1212a-34dddf34-4334f-34ddfvfdg1y3"})
    private UUID connectorUuid;

    @Schema(description = "Kind of the Compliance Provider", requiredMode = Schema.RequiredMode.REQUIRED, examples = {"default"})
    private String kind;

}
