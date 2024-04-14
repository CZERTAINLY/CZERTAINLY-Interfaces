package com.czertainly.api.model.client.cmp;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import com.czertainly.api.model.client.cmp.validation.ValidUuid;
import com.czertainly.api.model.core.cmp.ProtectionMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class BaseCmpProfileRequestDto {

    @Schema(
            description = "Description of the CMP Profile",
            example = "Sample text description"
    )
    private String description;

    @ValidUuid
    @Schema(
            description = "RA Profile UUID that the CMP Profile is associated with",
            example = "6b55de1c-844f-11ec-a8a3-0242ac120002"
    )
    private String raProfileUuid;

    @Schema(
            description = "List of Attributes to issue Certificate for the associated RA Profile. Required when raProfileUuid is provided"
    )
    private List<RequestAttributeDto> issueCertificateAttributes;

    @Schema(
            description = "List of Attributes to revoke Certificate for the associated RA Profile. Required when raProfileUuid is provided"
    )
    private List<RequestAttributeDto> revokeCertificateAttributes;

    @Schema(
            description = "List of Custom Attributes for CMP Profile"
    )
    private List<RequestAttributeDto> customAttributes;

    @NotNull
    @Schema(
            description = "Protection Method for the CMP Request",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private ProtectionMethod requestProtectionMethod;

    @NotNull
    @Schema(
            description = "Protection Method for the CMP Response",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private ProtectionMethod responseProtectionMethod;

    @Schema(
            description = "Shared secret for the CMP Request. Required when requestProtectionMethod is sharedSecret"
    )
    private String sharedSecret;

    @ValidUuid
    @Schema(
            description = "UUID of the Certificate to be used as signing certificate for CMP responses. Required when responseProtectionMethod is signature"
    )
    private String signingCertificateUuid;

}
