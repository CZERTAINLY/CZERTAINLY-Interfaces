package com.czertainly.api.interfaces.connector.common.v2;

import com.czertainly.api.config.OpenApiConfig;
import com.czertainly.api.model.common.AuthenticationServiceExceptionDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.RestController;

@OpenAPIDefinition(
        servers = {
                @Server(
                        url = "https://demo.czertainly.online",
                        description = "CZERTAINLY Demo server"
                )
        }
)
@RestController
@SecurityRequirements(value = {
        @SecurityRequirement(name = OpenApiConfig.BASIC_SECURITY_SCHEME_NAME),
        @SecurityRequirement(name = OpenApiConfig.CERTIFICATE_TLS_SECURITY_SCHEME_NAME),
        @SecurityRequirement(name = OpenApiConfig.CONNECTOR_API_KEY_SECURITY_SCHEME_NAME),
        @SecurityRequirement(name = OpenApiConfig.NO_SECURITY_SCHEME_NAME),
})@ApiResponses(
        value = {
                @ApiResponse(
                        responseCode = "401",
                        description = "Unauthorized",
                        content = @Content(
                                mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                                schema = @Schema(implementation = ProblemDetail.class)
                        )
                )
        })
public interface AuthProtectedConnectorController {
}
