package com.czertainly.api.model.core.settings;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class Oauth2SettingsDto {

    @Schema(
            description = "Registration ID of Oauth2 Provider, the ID must be unique.",
            example = "keycloak, github, facebook, etc.",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String registrationId;

    @Schema(description = "Oauth2 Client settings", requiredMode = Schema.RequiredMode.REQUIRED)
    private Oauth2ClientSettings clientSettings;

}
