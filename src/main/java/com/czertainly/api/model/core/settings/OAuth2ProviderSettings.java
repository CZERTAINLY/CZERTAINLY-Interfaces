package com.czertainly.api.model.core.settings;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.util.ArrayList;
import java.util.List;

@Data
public class OAuth2ProviderSettings {

    @Schema(description = "URL of issuer issuing authentication tokens. If provided, authentication via JWT token is enabled for this provider.")
    @URL
    private String issuerUrl;

    @Schema(description = "The client ID used to identify the client application during the authorization process.", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private String clientId;

    @Schema(description = "The client secret used by the client application to authenticate with the authorization server.", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private String clientSecret;

    @Schema(description = "The URL where the authorization server redirects the user for login and authorization.", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @URL
    private String authorizationUrl;

    @Schema(description = "The URl used to exchange the authorization code or credentials for an access token.", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @URL
    private String tokenUrl;

    @Schema(description = "The URL where the JSON Web Key Set (JWKS) containing the public keys used to verify JWT tokens can be retrieved.", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @URL
    private String jwkSetUrl;

    @Schema(description = " The list of scopes that define the access levels and permissions requested by the client application.")
    private List<String> scope = new ArrayList<>();

    @Schema(description = "URL to end session on provider side.", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @URL
    private String logoutUrl;

    @Schema(description = "URL that user will be redirected after logout from application.", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @URL
    private String postLogoutUrl;

    @Schema(description = "The URL containing information about user.")
    @URL
    private String userInfoUrl;

    @Schema(description = "A list of expected audiences for validating the issued tokens, used to match the intended recipients of the tokens.")
    private List<String> audiences = new ArrayList<>();

    @Schema(description = "The allowed time skew, in seconds, for token validation. This accounts for clock differences between systems. Default value is 30 seconds.")
    private int skew = 30;

    @Schema(description = "Duration in seconds after which will inactive user's session be terminated.", defaultValue = "15m")
    private int sessionMaxInactiveInterval = 15 * 60;

}
