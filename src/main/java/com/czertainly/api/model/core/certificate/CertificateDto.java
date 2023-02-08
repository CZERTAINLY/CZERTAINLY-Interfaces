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
public class CertificateDto {

    @Schema(
            description = "UUID of the Certificate",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String uuid;

    @Schema(
            description = "Certificate common name",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String commonName;

    @Schema(
            description = "Certificate serial number",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String serialNumber;

    @Schema(
            description = "Certificate issuer common name",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String issuerCommonName;

    @Schema(
            description = "Base64 encoded Certificate content",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String certificateContent;

    @Schema(
            description = "Issuer DN of the Certificate",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String issuerDn;

    @Schema(
            description = "Subject DN of the Certificate",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String subjectDn;

    @Schema(
            description = "Certificate validity start date",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Date notBefore;

    @Schema(
            description = "Certificate expiration date",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Date notAfter;

    @Schema(
            description = "Public key algorithm",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String publicKeyAlgorithm;

    @Schema(
            description = "Certificate signature algorithm",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String signatureAlgorithm;

    @Schema(
            description = "Certificate key size",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Integer keySize;

    @Schema(
            description = "Status of the Certificate",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private CertificateStatus status;

    @Schema(
            description = "RA Profile associated to the Certificate"
    )
    private SimplifiedRaProfileDto raProfile;

    @Schema(
            description = "SHA256 fingerprint of the Certificate",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String fingerprint;

    @Schema(
            description = "Group associated to the Certificate"
    )
    private GroupDto group;

    @Schema(
            description = "Certificate Owner"
    )
    private String owner;

    @Schema(
            description = "Certificate type"
    )
    private CertificateType certificateType;

    @Schema(
            description = "Serial number of the issuer"
    )
    private String issuerSerialNumber;

    @Schema(
            description = "Certificate compliance status"
    )
    private ComplianceStatus complianceStatus;

    @Schema(
            description = "Private Key Availability",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private boolean privateKeyAvailability;
}
