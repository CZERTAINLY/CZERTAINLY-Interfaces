package com.czertainly.api.model.core.scep;

import com.czertainly.api.model.common.NameAndUuidDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ScepProfileDto extends NameAndUuidDto {
    @Schema(description = "Enabled flag - true = enabled; false = disabled",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean enabled;
    @Schema(description = "SCEP Profile description", example = "Sample description")
    private String description;
    @Schema(description = "Enforce manual approval for all requests", requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean requireManualApproval;
    @Schema(description = "Name of the RA Profile", example = "RA Profile 1")
    private String raProfileName;
    @Schema(description = "UUID of RA Profile", example = "6b55de1c-844f-11ec-a8a3-0242ac120002")
    private String raProfileUuid;
    @Schema(description = "Include CA certificate in the SCEP response", requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean includeCaCertificate;
    @Schema(description = "Include CA certificate chain in the SCEP response", requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean includeCaCertificateChain;
    @Schema(description = "Renewal time threshold in days", example = "30")
    private Integer renewThreshold;

    @Schema(description = "Status of Intune")
    private boolean enableIntune;
}
