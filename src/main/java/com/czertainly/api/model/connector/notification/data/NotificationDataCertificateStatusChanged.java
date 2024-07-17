package com.czertainly.api.model.connector.notification.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class NotificationDataCertificateStatusChanged extends NotificationDataStatusChanged {

    @Schema(description = "Certificate UUID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String certificateUuid;

    @Schema(description = "SHA256 fingerprint of the Certificate", requiredMode = Schema.RequiredMode.REQUIRED)
    private String fingerprint;

    @Schema(description = "Certificate serial number", requiredMode = Schema.RequiredMode.REQUIRED)
    private String serialNumber;

    @Schema(description = "Subject DN of the Certificate", requiredMode = Schema.RequiredMode.REQUIRED)
    private String subjectDn;

    @Schema(description = "Issuer DN of the Certificate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String issuerDn;

    @Schema(description = "Authority instance reference UUID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String authorityInstanceUuid;

    @Schema(description = "Certificate validity start date")
    private String notBefore;

    @Schema(description = "Certificate expiration date")
    private String expiresAt;

    @Schema(description = "RA profile UUID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String raProfileUuid;

    @Schema(description = "RA profile name", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String raProfileName;


    public NotificationDataCertificateStatusChanged(String oldStatus, String newStatus, String certificateUuid, String fingerprint, String serialNumber, String subjectDn, String issuerDn, String authorityInstanceUuid, String raProfileUuid, String raProfileName,
                                                    String notBefore, String expiresAt) {
        this(oldStatus, newStatus, certificateUuid, fingerprint, serialNumber, subjectDn, issuerDn, notBefore, expiresAt);
        this.authorityInstanceUuid = authorityInstanceUuid;
        this.raProfileUuid = raProfileUuid;
        this.raProfileName = raProfileName;
    }

    public NotificationDataCertificateStatusChanged(String oldStatus, String newStatus, String certificateUuid, String fingerprint, String serialNumber, String subjectDn, String issuerDn,
                                                     String notBefore, String expiresAt) {
        super(oldStatus, newStatus);
        this.certificateUuid = certificateUuid;
        this.fingerprint = fingerprint;
        this.serialNumber = serialNumber;
        this.subjectDn = subjectDn;
        this.issuerDn = issuerDn;
        this.expiresAt = expiresAt;
        this.notBefore = notBefore;
    }
}
