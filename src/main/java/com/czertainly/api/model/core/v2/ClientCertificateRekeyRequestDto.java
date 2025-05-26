package com.czertainly.api.model.core.v2;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import com.czertainly.api.model.core.enums.CertificateRequestFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 * Class representing a request to regenerate certificate
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClientCertificateRekeyRequestDto {

    @Schema(
            description = "True to replace renewed certificate in the associated locations",
            defaultValue = "false"
    )
    public boolean replaceInLocations;

    //------------------------------------------------------------------------------------------------------------------
    // Request Related Parameters
    //------------------------------------------------------------------------------------------------------------------

    @Schema(
            description = "Certificate signing request encoded as Base64 string. If not provided, CSR attributes will be used"
    )
    private String request;

    @Schema(
            description = "Certificate signing request format",
            defaultValue = "pkcs10"
    )
    @Builder.Default
    private CertificateRequestFormat format = CertificateRequestFormat.PKCS10;

    //------------------------------------------------------------------------------------------------------------------
    // Key Related Parameters
    //------------------------------------------------------------------------------------------------------------------

    @Schema(
            description = "Key UUID"
    )
    private UUID keyUuid;

    @Schema(
            description = "Token Profile UUID"
    )
    private UUID tokenProfileUuid;

    @Schema(
            description = "Alternative Key UUID"
    )
    private UUID altKeyUuid;

    @Schema(
            description = "Alternative Token Profile UUID"
    )
    private UUID altTokenProfileUuid;

    //------------------------------------------------------------------------------------------------------------------
    // Attributes
    //------------------------------------------------------------------------------------------------------------------

    @Schema(
            description = "Signature Attributes. If not provided, existing attributes will be used to generate the new CSR"
    )
    private List<RequestAttributeDto> signatureAttributes;

    @Schema(
            description = "Alternative Signature Attributes. If not provided, existing alternative attributes will be used to generate the new CSR"
    )
    private List<RequestAttributeDto> altSignatureAttributes;
}
