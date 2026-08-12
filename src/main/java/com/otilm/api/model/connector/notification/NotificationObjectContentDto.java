package com.otilm.api.model.connector.notification;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Content representation of the notification subject, produced by the platform's content exporter for the subject's resource type")
public class NotificationObjectContentDto {

    @Schema(description = "Content format identifier declared by the exporter",
            requiredMode = Schema.RequiredMode.REQUIRED, examples = {"X509_DER_BASE64"})
    private String format;

    @Schema(description = "Content data; encoding is defined by the format",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String data;
}
