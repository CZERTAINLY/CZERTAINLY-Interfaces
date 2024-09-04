package com.czertainly.api.model.connector.notification.data;

import com.czertainly.api.model.common.attribute.v1.content.ZonedDateTimeDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

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

    @Schema(description = "Certificate validity start date in \"yyyy-MM-dd'T'HH:mm:ssXXX\" format")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    @JsonDeserialize(using = ZonedDateTimeDeserializer.class)
    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    private ZonedDateTime notBefore;

    @Schema(description = "Certificate expiration date in \"yyyy-MM-dd'T'HH:mm:ssXXX\" format")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    @JsonDeserialize(using = ZonedDateTimeDeserializer.class)
    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    private ZonedDateTime expiresAt;

    @Schema(description = "RA profile UUID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String raProfileUuid;

    @Schema(description = "RA profile name", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String raProfileName;


    public NotificationDataCertificateStatusChanged(String oldStatus, String newStatus, String certificateUuid, String fingerprint, String serialNumber, String subjectDn, String issuerDn, String authorityInstanceUuid, String raProfileUuid, String raProfileName,
                                                    ZonedDateTime notBefore, ZonedDateTime expiresAt) {
        this(oldStatus, newStatus, certificateUuid, fingerprint, serialNumber, subjectDn, issuerDn, notBefore, expiresAt);
        this.authorityInstanceUuid = authorityInstanceUuid;
        this.raProfileUuid = raProfileUuid;
        this.raProfileName = raProfileName;
    }

    public NotificationDataCertificateStatusChanged(String oldStatus, String newStatus, String certificateUuid, String fingerprint, String serialNumber, String subjectDn, String issuerDn,
                                                     ZonedDateTime notBefore, ZonedDateTime expiresAt) {
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
