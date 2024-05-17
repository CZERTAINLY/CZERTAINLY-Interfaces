package com.czertainly.api.model.core.certificate;

import com.czertainly.api.model.client.raprofile.SimplifiedRaProfileDto;
import com.czertainly.api.model.core.certificate.group.GroupDto;
import com.czertainly.api.model.core.compliance.ComplianceStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
import java.util.List;

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
            description = "Certificate serial number"
    )
    private String serialNumber;

    @Schema(
            description = "Certificate issuer common name"
    )
    private String issuerCommonName;

    @Schema(
            description = "Issuer DN of the Certificate"
    )
    private String issuerDn;

    @Schema(
            description = "Subject DN of the Certificate",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String subjectDn;

    @Schema(
            description = "Certificate validity start date"
    )
    private Date notBefore;

    @Schema(
            description = "Certificate expiration date"
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
            description = "State of the Certificate",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private CertificateState state;

    @Schema(
            description = "Current validation status of the certificate",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private CertificateValidationStatus validationStatus;

    @Schema(
            description = "RA Profile associated to the Certificate"
    )
    private SimplifiedRaProfileDto raProfile;

    @Schema(
            description = "SHA256 fingerprint of the Certificate"
    )
    private String fingerprint;

    @Schema(
            description = "Groups associated to the Certificate"
    )
    private List<GroupDto> groups;

    @Schema(
            description = "Certificate Owner",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String owner;

    @Schema(
            description = "Certificate Owner UUID",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String ownerUuid;

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
            description = "UUID of the issuer certificate"
    )
    private String issuerCertificateUuid;

    @Schema(
            description = "Private Key Availability",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private boolean privateKeyAvailability;

    @Schema(
            description = "Indicator whether CA is marked as trusted, set to null if certificate is not CA",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Boolean trustedCa;
}
