package com.czertainly.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.*;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        servers = {
                @Server(
                        url = "https://demo.czertainly.online/api",
                        description = "CZERTAINLY Demo server"
                )
        }
)
@SecuritySchemes(value = {
        @SecurityScheme(
                name = OpenApiConfig.CERTIFICATE_SECURITY_SCHEME_NAME,
                type = SecuritySchemeType.MUTUALTLS
        ),
        @SecurityScheme(
                name = OpenApiConfig.BEARER_SECURITY_SCHEME_NAME,
                type = SecuritySchemeType.HTTP,
                scheme = "Bearer"
        ),
        @SecurityScheme(
                name = OpenApiConfig.OAUTH2_SECURITY_SCHEME_NAME,
                type = SecuritySchemeType.OAUTH2,
                flows = @OAuthFlows(
                        authorizationCode = @OAuthFlow(
                                authorizationUrl = "https://api.example.com/oauth2/authorize",
                                tokenUrl = "https://api.example.com/oauth2/token"
                        )
                )
        )
})
public class OpenApiConfig {
        public static final String CERTIFICATE_SECURITY_SCHEME_NAME = "Certificate";
        public static final String BEARER_SECURITY_SCHEME_NAME = "BearerAuth";
        public static final String OAUTH2_SECURITY_SCHEME_NAME = "OAuth2";

        private OpenApiConfig() {}
}
