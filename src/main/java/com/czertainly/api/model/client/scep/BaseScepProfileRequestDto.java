package com.czertainly.api.model.client.scep;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class BaseScepProfileRequestDto {
    @Schema(
            description = "Description of the SCEP Profile",
            example = "Sample description"
    )
    private String description;

    @Schema(
            description = "RA Profile UUID",
            example = "6b55de1c-844f-11ec-a8a3-0242ac120002"
    )
    private String raProfileUuid;

    @Schema(
            description = "List of Attributes to issue Certificate",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<RequestAttributeDto> issueCertificateAttributes;

    @Schema(
            description = "Require manual approval for SCEP requests",
            defaultValue = "false",
            example = "true"
    )
    private Boolean requireManualApproval;

    @Schema(
            description = "UUID of the Certificate to be used as CA Certificate for SCEP Requests",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String caCertificateUuid;

    @Schema(
            description = "List of Custom Attributes"
    )
    private List<RequestAttributeDto> customAttributes;

    @Schema(
            description = "Minimum expiry days to allow renewal of certificate",
            defaultValue = "Half of certificate validity"
    )
    private Integer renewalThreshold;

    @Schema(
            description = "Include CA Certificate in the SCEP Message response",
            defaultValue = "False"
    )
    private boolean includeCaCertificate;

    @Schema(
            description = "Include CA Certificate Chain in the SCEP Message response",
            defaultValue = "False"
    )
    private boolean includeCaCertificateChain;

    @Schema(
            description = "Challenge Password for the SCEP Request",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String challengePassword;
}
