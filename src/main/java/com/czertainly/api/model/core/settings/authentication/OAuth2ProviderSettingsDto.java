package com.czertainly.api.model.core.settings.authentication;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OAuth2ProviderSettingsDto extends OAuth2ProviderSettingsUpdateDto {

    @NotNull
    @Schema(description = "Name of OAuth2 Provider", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

}

