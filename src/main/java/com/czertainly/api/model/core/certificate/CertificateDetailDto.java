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
            description = "Extended key usages",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private List<String> extendedKeyUsage;

    @Schema(
            description = "Key usages",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private List<CertificateKeyUsage> keyUsage;

    @Schema(
            description = "Certificate subject type",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private CertificateSubjectType subjectType;

    @Schema(
            description = "Certificate metadata",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private List<MetadataResponseDto> metadata;

    @Schema(
            description = "Base64 encoded Certificate content",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String certificateContent;

    @Schema(
            description = "Subject alternative names",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private Map<String, List<String>> subjectAlternativeNames;

    @Schema(
            description = "Locations associated to the Certificate",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private Set<LocationDto> locations;

    @Schema(
            description = "Certificate compliance check result",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private List<CertificateComplianceResultDto> nonCompliantRules;

    @Schema(
            description = "List of Custom Attributes",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private List<ResponseAttributeDto> customAttributes;

    @Schema(
            description = "Key Pair of the certificate",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private KeyDto key;

    @Schema(
            description = "Alternative Key Pair of the certificate",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private KeyDto altKey;

    @Schema(
            description = "Certificate request data",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private CertificateRequestDto certificateRequest;

    @Schema(
            description = "Source certificate UUID"
    )
    @Deprecated(since = "2.16.0")
    /**
     * @deprecated source certificates can be retrieved by calling {@link com.czertainly.api.interfaces.core.web.CertificateController#getCertificateRelations(UUID)}},
     * returning in {@link CertificateRelationsDto}
     */
    private UUID sourceCertificateUuid;

    @Schema(description = "List of issue attributes")
    private List<ResponseAttributeDto> issueAttributes = new ArrayList<>();

    @Schema(description = "List of revoke attributes")
    private List<ResponseAttributeDto> revokeAttributes = new ArrayList<>();

    @Schema(description = "List of related certificates")
    @Deprecated(since = "2.16.0")
    /**
     * @deprecated related certificates can be retrieved by calling {@link com.czertainly.api.interfaces.core.web.CertificateController#getCertificateRelations(UUID)}},
     * returning in {@link CertificateRelationsDto}
     */
    private List<CertificateDto> relatedCertificates = new ArrayList<>();

    @Schema(description = "Information about protocol used to issue the certificate")
    private  CertificateProtocolDto protocolInfo;

}
