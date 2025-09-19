package com.czertainly.api.model.client.compliance.v2;

import com.czertainly.api.model.core.auth.Resource;
import com.czertainly.api.model.core.workflows.ConditionItemRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class ComplianceInternalRuleRequestDto {

    @NotNull
    @NotBlank
    @Schema(description = "Name of the compliance internal rule", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Description of the compliance internal rule")
    private String description;

    @NotNull
    @Schema(description = "Resource associated with the compliance internal rule", requiredMode = Schema.RequiredMode.REQUIRED)
    private Resource resource;

    @NotNull
    @NotEmpty
    @Schema(description = "List of the condition items to add to compliance internal rule", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<ConditionItemRequestDto> items = new ArrayList<>();

    @JsonIgnore
    @AssertTrue(message = "Resource must be a valid compliance subject")
    public boolean isResourceValid() {
        return resource.complianceSubject();
    }

}
