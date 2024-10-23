package com.czertainly.api.model.core.settings;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OAuth2ProviderSettings {

    @Schema(description = "URL of issuer issuing authentication tokens. If provided, authentication via JWT token is enabled for this provider.")
    private String issuerUrl;

    @Schema(description = "The client ID used to identify the client application during the authorization process.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String clientId;

    @Schema(description = "The client secret used by the client application to authenticate with the authorization server.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String clientSecret;

    @Schema(description = "The URL where the authorization server redirects the user for login and authorization.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String authorizationUrl;

    @Schema(description = "The URl used to exchange the authorization code or credentials for an access token.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String tokenUrl;

    @Schema(description = "The URL where the JSON Web Key Set (JWKS) containing the public keys used to verify JWT tokens can be retrieved.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String jwkSetUrl;

    @Schema(description = " The list of scopes that define the access levels and permissions requested by the client application.", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> scope;

    @Schema(description = " The list of scopes that define the access levels and permissions requested by the client application.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String logoutUrl;

    @Schema(description = "URL that user will be redirected after logout from application.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String postLogoutUrl;

    @Schema(description = "A list of expected audiences for validating the issued tokens, used to match the intended recipients of the tokens.")
    private List<String> audiences = new ArrayList<>();

    @Schema(description = "The allowed time skew, in seconds, for token validation. This accounts for clock differences between systems. Default value is 30 seconds.")
    private int skew = 30;

}
