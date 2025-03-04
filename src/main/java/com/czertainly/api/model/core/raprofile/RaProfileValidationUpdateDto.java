package com.czertainly.api.model.core.raprofile;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RaProfileValidationUpdateDto {

    @NotNull
    @Schema(description = "Indicator whether validation of certificates associated with RA profile should be enabled", requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean validationEnabled;

    @Schema(description = "Frequency of validation of certificates in days", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer validationFrequency;

    @Schema(description = "How many days before expiration should certificate validation status change to Expiring", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer expiringThreshold;

}
