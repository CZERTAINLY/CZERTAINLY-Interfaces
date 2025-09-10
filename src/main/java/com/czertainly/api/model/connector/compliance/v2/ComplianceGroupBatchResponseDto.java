package com.czertainly.api.model.connector.compliance.v2;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class ComplianceGroupBatchResponseDto extends ComplianceGroupResponseDto {
    @Schema(description = "List of the group rules",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            examples = {"166b5cf52-63f2-11ec-90d6-0242ac120003"})
    private List<ComplianceRuleResponseDto> rules = new ArrayList<>();

}
