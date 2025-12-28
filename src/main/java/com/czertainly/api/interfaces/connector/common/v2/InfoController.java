package com.czertainly.api.interfaces.connector.common.v2;

import com.czertainly.api.interfaces.AuthProtectedConnectorController;
import com.czertainly.api.model.client.connector.v2.InfoResponse;
import com.czertainly.api.model.common.ErrorMessageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v2")
@Tag(
        name = "Connector Info v2",
        description = "Connector Information API. " +
                "Each connector may have multiple functions represented by FunctionGroupCode. " +
                "For each FunctionGroupCode there is a list of implemented end points. " +
                "These endpoints must be according the specified interface, this is validated by the core. " +
                "You can also implement helper end points that are used for callbacks and other relevant operations " +
                "specific to implementation."
)
public interface InfoController extends AuthProtectedConnectorController {

    @GetMapping(
            produces = {"application/json"}
    )
    @Operation(
            summary = "Get Connector Info",
            description = "Returns information about the connector and implemented interfaces."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Connector info retrieved successfully"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Connector info failed to retrieve",
                            content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))
                    )
    })
    InfoResponse getConnectorInfo();

}