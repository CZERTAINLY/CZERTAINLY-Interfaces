package com.otilm.api.model.connector.compliance.v2;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ComplianceGroupBatchResponseDto extends ComplianceGroupResponseDto {
    @Schema(description = "List of the group rules", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<ComplianceRuleResponseDto> rules = new ArrayList<>();

}
