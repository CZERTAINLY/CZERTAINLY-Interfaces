package com.czertainly.api.interfaces;

import com.czertainly.api.model.HealthDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/health")
@Tag(
        name = "Health check API",
        description = "Connector health check API. " +
                "Connector returns own status and in some cases " +
                "can return status of services on which it depends like database, HSM and so on."
)
public interface HealthController {

    @GetMapping(
            produces = {"application/json"}
    )
    @Operation(
            summary = "Checks the health of the connector",
            description = "Returns current health overall status of the connector and it's arbitrary parts"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Health checked successfully"
                    )
    })
    HealthDto checkHealth();

}