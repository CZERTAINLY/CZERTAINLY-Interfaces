package com.czertainly.api.model.core.certificate;

import com.czertainly.api.model.client.raprofile.SimplifiedRaProfileDto;
import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.core.certificate.group.GroupDto;
import com.czertainly.api.model.core.compliance.ComplianceStatus;
import com.czertainly.api.model.core.logging.Loggable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class CertificateDto implements Loggable {

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
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String serialNumber;

    @Schema(
            description = "Certificate issuer common name",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String issuerCommonName;

    @Schema(
            description = "Issuer DN of the Certificate",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String issuerDn;

    @Schema(
            description = "Subject DN of the Certificate",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String subjectDn;

    @Schema(
            description = "Certificate validity start date",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private Date notBefore;

    @Schema(
            description = "Certificate expiration date",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
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
            description = "RA Profile associated to the Certificate",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private SimplifiedRaProfileDto raProfile;

    @Schema(
            description = "SHA256 fingerprint of the Certificate",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String fingerprint;

    @Schema(
            description = "Groups associated to the Certificate",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
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
            description = "Certificate type",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private CertificateType certificateType;

    @Schema(
            description = "Serial number of the issuer",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String issuerSerialNumber;

    @Schema(
            description = "Certificate compliance status",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private ComplianceStatus complianceStatus;

    @Schema(
            description = "UUID of the issuer certificate",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String issuerCertificateUuid;

    @Schema(
            description = "Private Key Availability",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private boolean privateKeyAvailability;

    @Schema(
            description = "Indicator whether CA is marked as trusted, set to null if certificate is not CA",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private Boolean trustedCa;

    @Override
    public Serializable toLogData() {
        return new NameAndUuidDto(this.uuid, this.subjectDn);
    }
}
