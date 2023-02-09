package com.czertainly.api.model.core.certificate;

import com.czertainly.api.model.client.attribute.ResponseAttributeDto;
import com.czertainly.api.model.client.metadata.MetadataResponseDto;
import com.czertainly.api.model.client.raprofile.SimplifiedRaProfileDto;
import com.czertainly.api.model.core.certificate.group.GroupDto;
import com.czertainly.api.model.core.compliance.ComplianceStatus;
import com.czertainly.api.model.core.cryptography.key.KeyDto;
import com.czertainly.api.model.core.location.LocationDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
            description = "CSR for the certificate"
    )
    private String csr;

    @Schema(
            description = "CSR Attributes"
    )
    private List<ResponseAttributeDto> csrAttributes;

    @Schema(
            description = "Signature Attributes"
    )
    private List<ResponseAttributeDto> signatureAttributes;

    @Schema(
            description = "Key Pair of the certificate"
    )
    private KeyDto key;
}
