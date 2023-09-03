package com.czertainly.api.model.connector.notification.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NotificationDataCertificateActionPerformed {

    @Schema(description = "Certificate action", requiredMode = Schema.RequiredMode.REQUIRED)
    private String action;

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

    @Schema(description = "RA profile UUID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String raProfileUuid;

    @Schema(description = "RA profile name", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String raProfileName;

    @Schema(description = "Error message. Filled when action failed", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String errorMessage;
}
