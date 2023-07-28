package com.czertainly.api.model.connector.notification.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class NotificationDataCertificateStatusChanged extends NotificationDataStatusChanged {
    @Schema(description = "SHA256 fingerprint of the Certificate", requiredMode = Schema.RequiredMode.REQUIRED)
    private String fingerprint;

    @Schema(description = "Certificate serial number", requiredMode = Schema.RequiredMode.REQUIRED)
    private String serialNumber;

    @Schema(description = "Subject DN of the Certificate", requiredMode = Schema.RequiredMode.REQUIRED)
    private String subjectDn;

    @Schema(description = "Issuer DN of the Certificate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String issuerDn;

    @Schema(description = "Authority instance reference UUID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String authorityInstanceUuid;

    @Schema(description = "RA profile UUID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String raProfileUuid;

    @Schema(description = "RA profile name", requiredMode = Schema.RequiredMode.REQUIRED)
    private String raProfileName;

    public NotificationDataCertificateStatusChanged(String oldStatus, String newStatus, String fingerprint, String serialNumber, String subjectDn, String issuerDn, String authorityInstanceUuid, String raProfileUuid, String raProfileName) {
        super(oldStatus, newStatus);
        this.fingerprint = fingerprint;
        this.serialNumber = serialNumber;
        this.subjectDn = subjectDn;
        this.issuerDn = issuerDn;
        this.authorityInstanceUuid = authorityInstanceUuid;
        this.raProfileUuid = raProfileUuid;
        this.raProfileName = raProfileName;
    }
}
