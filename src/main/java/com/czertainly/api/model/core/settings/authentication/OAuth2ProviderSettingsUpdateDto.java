package com.czertainly.api.model.core.settings.authentication;

import com.czertainly.api.model.core.logging.Sensitive;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class OAuth2ProviderSettingsUpdateDto implements Serializable {

    @URL
    @Schema(description = "URL of issuer issuing authentication tokens. If provided, authentication via JWT token is enabled for this provider.")
    private String issuerUrl;

    @Schema(description = "The client ID used to identify the client application during the authorization process.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String clientId;

    @Sensitive
    @Schema(description = "The client secret used by the client application to authenticate with the authorization server.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String clientSecret;

    @URL
    @Schema(description = "The URL where the authorization server redirects the user for login and authorization.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String authorizationUrl;

    @URL
    @Schema(description = "The URl used to exchange the authorization code or credentials for an access token.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String tokenUrl;

    @URL
    @Schema(description = "The URL where the JSON Web Key Set (JWKS) containing the public keys used to verify JWT tokens can be retrieved.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String jwkSetUrl;

    @Schema(description = " The list of scopes that define the access levels and permissions requested by the client application.")
    private List<String> scope = new ArrayList<>();

    @URL
    @Schema(description = "URL to end session on provider side.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String logoutUrl;

    @URL
    @Schema(description = "URL that user will be redirected after logout from application.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String postLogoutUrl;

    @URL
    @Schema(description = "The URL containing information about user.")
    private String userInfoUrl;

    @Schema(description = "A list of expected audiences for validating the issued tokens, used to match the intended recipients of the tokens.")
    private List<String> audiences = new ArrayList<>();

    @Schema(description = "The allowed time skew, in seconds, for token validation. This accounts for clock differences between systems. Default value is 30 seconds.")
    private int skew = 30;

    @Schema(description = "Duration in seconds after which will inactive user's session be terminated.", defaultValue = "15m")
    private int sessionMaxInactiveInterval = 15 * 60;

    @AssertTrue(message = "OAuth2 Provider must be either configured for authenticating with JWT token, browser login using client or both. For JWT token, \"issuerUrl\" must not be null, for client login, " +
            "\"clientId\", \"clientSecret\", \"authorizationUrl\", \"tokenUrl\", \"jwkSetUrl\", \"logoutUrl\", \"postLogoutUrl\" all must be not null.")
    @JsonIgnore
    public boolean isValidProviderConfiguration() {
        boolean validClientConfiguration = (clientId != null) && (clientSecret != null) && (authorizationUrl != null) && (tokenUrl != null) && (jwkSetUrl != null) && (logoutUrl != null) && (postLogoutUrl != null);
        boolean hasClientConfiguration = (clientId != null) || (clientSecret != null) || (authorizationUrl != null) || (tokenUrl != null) || (jwkSetUrl != null) || (logoutUrl != null) || (postLogoutUrl != null);
        // If issuer is null, the rest of configuration must be valid for client login
        // Or issuer is not null, but if any of the client related properties is not null - indicating use for login, all of them must be not nul
        if (issuerUrl == null || hasClientConfiguration) return validClientConfiguration;
        return true;
    }
}
