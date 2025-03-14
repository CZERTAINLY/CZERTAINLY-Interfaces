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
                name = SwaggerConfig.CERTIFICATE_SECURITY_SCHEME_NAME,
                type = SecuritySchemeType.MUTUALTLS
        ),
        @SecurityScheme(
                name = SwaggerConfig.BEARER_SECURITY_SCHEME_NAME,
                type = SecuritySchemeType.HTTP,
                scheme = "Bearer"
        ),
        @SecurityScheme(
                name = SwaggerConfig.OAUTH2_SECURITY_SCHEME_NAME,
                type = SecuritySchemeType.OAUTH2,
                flows = @OAuthFlows(
                        authorizationCode = @OAuthFlow(
                                authorizationUrl = "https://api.example.com/oauth2/authorize",
                                tokenUrl = "https://api.example.com/oauth2/token"
                        )
                )
        )
//        @SecurityScheme(
//                name = SwaggerConfig.OPENID_CONNECT_SECURITY_SCHEME_NAME,
//                type = SecuritySchemeType.OPENIDCONNECT,
//                openIdConnectUrl = "https://example.com/.well-known/openid-configuration"
//        )
})
public class SwaggerConfig {
        public static final String CERTIFICATE_SECURITY_SCHEME_NAME = "Certificate";
        public static final String BEARER_SECURITY_SCHEME_NAME = "BearerAuth";
        public static final String OAUTH2_SECURITY_SCHEME_NAME = "OAuth2";
        public static final String OPENID_CONNECT_SECURITY_SCHEME_NAME = "OpenID";

        private SwaggerConfig() {}
}
