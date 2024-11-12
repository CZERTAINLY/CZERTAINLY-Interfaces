
package com.czertainly.api.model.core.settings;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OAuth2SettingsDto {

    @Schema(
            description = "Name of OAuth2 Provider",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull
    private String providerName;

    @NotNull
    @Schema(description = "OAuth2 Provider settings", requiredMode = Schema.RequiredMode.REQUIRED)
    private OAuth2ProviderSettings oAuth2ProviderSettings;

}

