package com.czertainly.api.model.core.certificate;

import com.czertainly.api.model.client.attribute.ResponseAttributeDto;
import com.czertainly.api.model.client.metadata.MetadataResponseDto;
import com.czertainly.api.model.core.cryptography.key.KeyDto;
import com.czertainly.api.model.core.location.LocationDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class CertificateDetailDto extends CertificateDto {

    @Schema(
            description = "Extended key usages"
    )
    private List<String> extendedKeyUsage;

    @Schema(
            description = "Key usages",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<String> keyUsage;

    @Schema(
            description = "Basic Constraints",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String basicConstraints;

    @Schema(
            description = "Certificate metadata"
    )
    private List<MetadataResponseDto> metadata;

    @Schema(
            description = "Base64 encoded Certificate content",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String certificateContent;

    @Schema(
            description = "Subject alternative names"
    )
    private Map<String, Object> subjectAlternativeNames;

    @Schema(
            description = "Locations associated to the Certificate"
    )
    private Set<LocationDto> locations;

    @Schema(
            description = "Certificate compliance check result"
    )
    private List<CertificateComplianceResultDto> nonCompliantRules;

    @Schema(
            description = "List of Custom Attributes"
    )
    private List<ResponseAttributeDto> customAttributes;

    @Schema(
            description = "Key Pair of the certificate"
    )
    private KeyDto key;

    @Schema(
            description = "Certificate request data"
    )
    private CertificateRequestDto certificateRequest;

    @Schema(
            description = "Source certificate UUID"
    )
    private UUID sourceCertificateUuid;

    @Schema(description = "List of issue attributes")
    private List<ResponseAttributeDto> issueAttributes = new ArrayList<>();

    @Schema(description = "List of revoke attributes")
    private List<ResponseAttributeDto> revokeAttributes = new ArrayList<>();

    @Schema(description = "List of related certificates")
    private List<CertificateDto> relatedCertificates = new ArrayList<>();
}
