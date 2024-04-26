package com.czertainly.api.model.core.v2;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import com.czertainly.api.model.core.enums.CertificateRequestFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;
import java.util.UUID;

/**
 * Class representing a request to sign CSR
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ClientCertificateRequestDto {

    @Schema(
            description = "RA Profile UUID. Required if CSR is not uploaded",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private UUID raProfileUuid;

    @Schema(
            description = "Source certificate UUID to specify in case of renew/rekey operation"
    )
    private UUID sourceCertificateUuid;

    @Schema(
            description = "List of attributes to create CSR. Required if CSR is not provided"
    )
    List<RequestAttributeDto> csrAttributes;

    @Schema(
            description = "List of attributes to sign the CSR"
    )
    List<RequestAttributeDto> signatureAttributes;

    //------------------------------------------------------------------------------------------------------------------
    // Key Related Parameters
    //------------------------------------------------------------------------------------------------------------------

    @Schema(
            description = "Certificate signing request encoded as Base64 string"
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
            description = "Token Profile UUID. Required if CSR is not uploaded"
    )
    private UUID tokenProfileUuid;
    @Schema(
            description = "Key UUID. Required if CSR is not uploaded"
    )
    private UUID keyUuid;

    //------------------------------------------------------------------------------------------------------------------
    // Attributes
    //------------------------------------------------------------------------------------------------------------------

    @Schema(
            description = "List of RA Profile related Attributes to issue Certificate",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<RequestAttributeDto> issueAttributes;

    @Schema(
            description = "List of Custom Attributes"
    )
    private List<RequestAttributeDto> customAttributes;

}
