package com.czertainly.api.model.common.events.data;

import com.czertainly.api.model.common.attribute.v1.content.ZonedDateTimeDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.ZonedDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class CertificateExpiringEventData extends CertificateEventData {

    @Schema(description = "Authority instance reference UUID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private UUID authorityInstanceUuid;

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
    private UUID raProfileUuid;

    @Schema(description = "RA profile name", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String raProfileName;
}
