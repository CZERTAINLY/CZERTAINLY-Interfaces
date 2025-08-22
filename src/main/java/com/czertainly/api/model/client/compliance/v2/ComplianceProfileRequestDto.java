package com.czertainly.api.model.client.compliance.v2;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString(callSuper = true)
public class ComplianceProfileRequestDto extends ComplianceProfileUpdateRequestDto {

    @Schema(description = "Name of the Compliance Profile", requiredMode = Schema.RequiredMode.REQUIRED, examples = {"Profile 1"})
    private String name;

}
