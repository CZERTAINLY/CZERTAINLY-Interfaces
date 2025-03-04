package com.czertainly.api.model.core.settings;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CertificateSettingsDto {

    @NotNull
    @Schema(description = "Indicator whether validation of certificates should be enabled", requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean validationEnabled;

    @Schema(description = "Frequency of validation of certificates in days", requiredMode = Schema.RequiredMode.NOT_REQUIRED, defaultValue = "1")
    private Integer validationFrequency = 1;

    @Schema(description = "How many days before expiration should certificate validation status change to Expiring", requiredMode = Schema.RequiredMode.NOT_REQUIRED, defaultValue = "30")
    private Integer expiringThreshold = 30;
}
