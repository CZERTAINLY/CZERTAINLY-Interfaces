package com.czertainly.api.model.core.scep;

import com.czertainly.api.model.client.raprofile.SimplifiedRaProfileDto;
import com.czertainly.api.model.common.NameAndUuidDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.logging.SimpleFormatter;

@EqualsAndHashCode(callSuper = true)
@Data
public class ScepProfileDto extends NameAndUuidDto {
    @Schema(description = "Enabled flag - true = enabled; false = disabled",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean enabled;
    @Schema(description = "SCEP Profile description", example = "Sample description")
    private String description;
    @Schema(description = "RA Profile")
    private SimplifiedRaProfileDto raProfile;
    @Schema(description = "Include CA certificate in the SCEP response", requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean includeCaCertificate;
    @Schema(description = "Include CA certificate chain in the SCEP response", requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean includeCaCertificateChain;
    @Schema(description = "Renewal time threshold in days", example = "30")
    private Integer renewThreshold;
    @Schema(description = "SCEP URL", example = "https://some-server.com/api/v1/protocols/scep/profile/pkiclient.exe")
    private String scepUrl;

    @Schema(description = "Status of Intune")
    private boolean enableIntune;
}
