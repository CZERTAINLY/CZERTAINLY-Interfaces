package com.czertainly.api.model.core.settings;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.io.Serializable;

@Data
public class CertificateSettingsDto implements Serializable {

    @NotNull
    @Schema(description = "Indicator whether validation of certificates should be enabled", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean validationEnabled;

    @Schema(description = "Frequency of validation of certificates in days", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Positive
    private Integer validationFrequency;

    @Schema(description = "How many days before expiration should certificate validation status change to Expiring", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Positive
    private Integer expiringThreshold;
}
