package com.czertainly.api.interfaces.connector.common.v2;

import com.czertainly.api.model.client.connector.v2.HealthInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v2/health")
@Tag(
        name = "Health check v2",
        description = "Connector Health check API. " +
                "Connector returns own status and in some cases " +
                "can return status of components on which it depends like database, HSM and so on."
)
public interface HealthController extends NoAuthConnectorController {

    @GetMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(
            summary = "Health check"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Health check retrieved successfully"
                    )
            })
    HealthInfo checkHealth();

}