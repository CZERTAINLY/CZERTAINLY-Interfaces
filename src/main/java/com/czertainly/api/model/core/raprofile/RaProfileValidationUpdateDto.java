package com.czertainly.api.model.core.raprofile;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class RaProfileValidationUpdateDto {

    @NotNull
    @Schema(description = "Indicator whether validation of certificates associated with RA profile should be enabled", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean validationEnabled;

    @Schema(description = "Frequency of validation of certificates in days, when not set, value in platform settings is used", requiredMode = Schema.RequiredMode.NOT_REQUIRED, minimum = "1")
    @Positive
    private Integer validationFrequency;

    @Schema(description = "How many days before expiration should certificate validation status change to Expiring, when not set, value in platform settings is used", requiredMode = Schema.RequiredMode.NOT_REQUIRED, minimum = "1")
    @Positive
    private Integer expiringThreshold;

}
