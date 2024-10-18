package com.czertainly.api.model.core.settings;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Oauth2ResourceServerSettingsDto {

    @Schema(description = "Issuer URI", requiredMode = Schema.RequiredMode.REQUIRED)
    private String issuerUri;

    @Schema(description = "Audiences")
    private List<String> audiences = new ArrayList<>();

    @Schema(description = "Skew")
    private int skew = 0;

}
