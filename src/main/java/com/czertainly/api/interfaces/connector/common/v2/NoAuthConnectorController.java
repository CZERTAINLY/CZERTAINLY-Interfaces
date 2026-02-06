package com.czertainly.api.interfaces.connector.common.v2;

import com.czertainly.api.model.common.error.ProblemDetailExtended;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SecurityRequirements
@ApiResponses(
        value = {
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal Server Error",
                        content = @Content(
                                mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                                schema = @Schema(implementation = ProblemDetailExtended.class)
                        )
                )
        })
public interface NoAuthConnectorController {
}
