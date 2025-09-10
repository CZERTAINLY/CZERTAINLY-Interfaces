package com.czertainly.api.model.client.compliance.v2;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
public class ComplianceProfileRulesPatchRequestDto {

    @Schema(description = "Indicates if removing or adding rule with UUID specified in request", requiredMode = Schema.RequiredMode.REQUIRED, examples = {"1212a-34dddf34-4334f-34ddfvfdg1y3"})
    private boolean removal;

    @Schema(description = "UUID of the rule", requiredMode = Schema.RequiredMode.REQUIRED, examples = {"1212a-34dddf34-4334f-34ddfvfdg1y3"})
    private UUID ruleUuid;

    @Schema(description = "UUID of the Compliance Provider", requiredMode = Schema.RequiredMode.NOT_REQUIRED, examples = {"1212a-34dddf34-4334f-34ddfvfdg1y3"})
    private UUID connectorUuid;

    @Schema(description = "Kind of the Compliance Provider", requiredMode = Schema.RequiredMode.NOT_REQUIRED, examples = {"default"})
    private String kind;

    @Schema(description = "Attributes for the rule", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<RequestAttributeDto> attributes;

    @JsonIgnore
    @AssertTrue(message = "If connector UUID is specified, kind is also required and vice versa")
    public boolean isValid() {
        return (connectorUuid == null && kind == null) || (connectorUuid != null && kind != null);
    }
}
