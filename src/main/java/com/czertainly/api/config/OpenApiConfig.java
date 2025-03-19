package com.czertainly.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
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
                name = OpenApiConfig.CERTIFICATE_TLS_SECURITY_SCHEME_NAME,
                type = SecuritySchemeType.MUTUALTLS,
                description = "Client certificate authentication"
        ),
        @SecurityScheme(
                name = OpenApiConfig.CERTIFICATE_SECURITY_SCHEME_NAME,
                type = SecuritySchemeType.APIKEY,
                in = SecuritySchemeIn.HEADER,
                paramName = "X-APP-CERTIFICATE",
                description = "Base64 encoded X.509 certificate passed in header"
        ),
        @SecurityScheme(
                name = OpenApiConfig.CONNECTOR_API_KEY_SECURITY_SCHEME_NAME,
                type = SecuritySchemeType.APIKEY,
                in = SecuritySchemeIn.HEADER,
                paramName = "ApiKeyHeaderAttribute",
                description = "API Key in header configured for connector"
        ),
        @SecurityScheme(
                name = OpenApiConfig.BASIC_SECURITY_SCHEME_NAME,
                type = SecuritySchemeType.HTTP,
                scheme = "Basic"
        ),
        @SecurityScheme(
                name = OpenApiConfig.BEARER_JWT_SECURITY_SCHEME_NAME,
                type = SecuritySchemeType.HTTP,
                scheme = "Bearer",
                bearerFormat = "JWT"
        ),
        @SecurityScheme(
                name = OpenApiConfig.SESSION_SECURITY_SCHEME_NAME,
                type = SecuritySchemeType.APIKEY,
                in = SecuritySchemeIn.COOKIE,
                paramName = "czertainly-session", // Name of the cookie containing the session ID
                description = "Session-based authentication with session ID stored in 'czertainly-session' cookie"
        ),
        @SecurityScheme(
                name = OpenApiConfig.NO_SECURITY_SCHEME_NAME,
                type = SecuritySchemeType.HTTP,
                scheme = "none",
                description = "No authentication"
        )
})
public class OpenApiConfig {
        public static final String NO_SECURITY_SCHEME_NAME = "NoAuth";
        public static final String CERTIFICATE_SECURITY_SCHEME_NAME = "CertificateAuth";
        public static final String CERTIFICATE_TLS_SECURITY_SCHEME_NAME = "CertificateTLSAuth";
        public static final String CONNECTOR_API_KEY_SECURITY_SCHEME_NAME = "ConnectorAPIKeyAuth";
        public static final String BASIC_SECURITY_SCHEME_NAME = "BasicAuth";
        public static final String BEARER_JWT_SECURITY_SCHEME_NAME = "BearerJWTAuth";
        public static final String SESSION_SECURITY_SCHEME_NAME = "SessionAuth";

        private OpenApiConfig() {}
}
