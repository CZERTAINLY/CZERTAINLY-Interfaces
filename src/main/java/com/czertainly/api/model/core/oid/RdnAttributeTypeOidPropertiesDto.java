package com.czertainly.api.model.core.oid;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RdnAttributeTypeOidPropertiesDto implements AdditionalOidPropertiesDto {

    @Schema(description = "Code to be displayed in RDN string (e.g., CN for Common Name)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    @Pattern(
            regexp = "^[A-Za-z][A-Za-z0-9-]*$",
            message = "Invalid code format."
    )
    private String code;

    @Schema(description = "Alternative codes that can appear in RDN representing the same OID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<@NotBlank @Pattern(
            regexp = "^[A-Za-z][A-Za-z0-9-]*$",
            message = "Invalid alt code format."
    ) String> altCodes = new ArrayList<>();

}
