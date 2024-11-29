package com.czertainly.api.model.core.settings.authentication;

import com.czertainly.api.model.core.settings.SettingsDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class AuthenticationSettingsUpdateDto implements SettingsDto {

    @NotNull
    @Schema(description = "Disable using localhost user for requests from localhost", requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean disableLocalhostUser = false;

    @Schema(description = "List of OAuth2 providers settings to be registered. If null, keep providers as they are, otherwise replace with providers from list", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<OAuth2ProviderSettingsDto> oAuth2Providers;
}
