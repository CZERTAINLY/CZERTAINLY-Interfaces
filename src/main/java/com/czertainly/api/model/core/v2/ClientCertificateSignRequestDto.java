package com.czertainly.api.model.core.v2;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;
import java.util.UUID;

/**
 * Class representing a request to sign CSR
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ClientCertificateSignRequestDto {

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
            description = "Boolean representing if the CSR is Uploaded",
            requiredMode = Schema.RequiredMode.REQUIRED,
            defaultValue = "false"
    )
    private boolean uploadCsr;
    @Schema(
            description = "Certificate sign request (PKCS#10) encoded as Base64 string",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String pkcs10;

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
    private List<RequestAttributeDto> attributes;

    @Schema(
            description = "List of Custom Attributes"
    )
    private List<RequestAttributeDto> customAttributes;

}
