package com.czertainly.api.model.core.oid;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class RdnAttributeTypeOidPropertiesDto implements AdditionalOidPropertiesDto {

    @Schema(description = "Type of value that the OID represents (e.g., BIT STRING, UTF8String)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String valueType;

    @Schema(description = "Code to be displayed in RDN string (e.g., CN for Common Name)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String code;

    @Schema(description = "Alternative codes that can be encountered as code for RDNs with this OID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<@NotBlank String> altCodes;

}
