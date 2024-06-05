package com.czertainly.api.model.client.cmp;

import com.czertainly.api.model.client.cmp.validation.ValidCmpProfileName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CmpProfileRequestDto extends BaseCmpProfileRequestDto {

    @NotBlank(
            message = "Name of the CMP Profile is required"
    )
    @ValidCmpProfileName
    @Schema(
            description = "Name of the CMP Profile",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "Example CMP Profile"
    )
    private String name;

}
