package com.czertainly.api.model.core.v2;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.UUID;

/**
 * Class representing a request to regenerate certificate
 */
@Data
public class ClientCertificateRegenerationRequestDto {

    @Schema(
            description = "True to replace renewed certificate in the associated locations",
            defaultValue = "false"
    )
    public boolean replaceInLocations;
    @Schema(
            description = "Certificate sign request (PKCS#10) encoded as Base64 string. If not provided, CSR attributes will be used"
    )
    private String pkcs10;
    @Schema(
            description = "Key UUID",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private UUID keyUuid;
    @Schema(
            description = "Token Profile UUID",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private UUID tokenProfileUuid;
    @Schema(
            description = "CSR Attributes. If not provided, existing attributes will be used to generate the new CSR"
    )
    private List<RequestAttributeDto> csrAttributes;
    @Schema(
            description = "Signature Attributes. If not provided, existing attributes will be used to generate the new CSR"
    )
    private List<RequestAttributeDto> signatureAttributes;
}
