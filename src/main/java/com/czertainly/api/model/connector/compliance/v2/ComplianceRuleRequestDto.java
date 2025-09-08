package com.czertainly.api.model.connector.compliance.v2;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ComplianceRuleRequestDto {
    @Schema(description = "UUID of the rule",
            requiredMode = Schema.RequiredMode.REQUIRED,
            examples = {"166b5cf52-63f2-11ec-90d6-0242ac120003"})
    private UUID uuid;

    @Schema(description = "Attributes for the rule", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<RequestAttributeDto> attributes;

}
