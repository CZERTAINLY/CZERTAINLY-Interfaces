package com.czertainly.api.model.core.settings;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.io.Serializable;

@Data
public class CertificateValidationSettingsDto implements Serializable {
    @NotNull
    @Schema(description = "Indicator whether validation of certificates should be enabled", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean validationEnabled;

    @Schema(description = "Frequency of validation of certificates in days", requiredMode = Schema.RequiredMode.NOT_REQUIRED, defaultValue = "1 day", minimum = "1")
    @Positive
    private Integer validationFrequency = 1;

    @Schema(description = "How many days before expiration should certificate validation status change to Expiring", requiredMode = Schema.RequiredMode.NOT_REQUIRED, defaultValue = "30 days", minimum = "1")
    @Positive
    private Integer expiringThreshold = 30;
}
