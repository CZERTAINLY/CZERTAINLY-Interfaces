package com.czertainly.api.model.core.settings;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class Oauth2ClientSettings {

    @Schema(description = "Issuer URI", requiredMode = Schema.RequiredMode.REQUIRED)
    private String issuerUri;

    @Schema(description = "Client ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String clientId;

    @Schema(description = "Client Secret", requiredMode = Schema.RequiredMode.REQUIRED)
    private String clientSecret;

    @Schema(description = "Authorization Grant Type", requiredMode = Schema.RequiredMode.REQUIRED)
    private String authorizationGrantType;

    @Schema(description = "Redirect URI", requiredMode = Schema.RequiredMode.REQUIRED)
    private String redirectUri;

    @Schema(description = "Authorization URI", requiredMode = Schema.RequiredMode.REQUIRED)
    private String authorizationUri;

    @Schema(description = "Token URI", requiredMode = Schema.RequiredMode.REQUIRED)
    private String tokenUri;

    @Schema(description = "JWK Set URI", requiredMode = Schema.RequiredMode.REQUIRED)
    private String jwkSetUri;

    @Schema(description = "Scope", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> scope;

    @Schema(description = "End Session Endpoint", requiredMode = Schema.RequiredMode.REQUIRED)
    private String endSessionEndpoint;
}
