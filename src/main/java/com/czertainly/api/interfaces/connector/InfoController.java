package com.czertainly.api.interfaces.connector;

import com.czertainly.api.model.client.connector.InfoResponse;
import com.czertainly.api.model.common.ErrorMessageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
@Tag(
        name = "Info API",
        description = "Connector Information API. " +
                "Each connector may have multiple functions represented by FunctionGroupCode. " +
                "For each FunctionGroupCode there is a list of implemented end points. " +
                "These endpoints must be according the specified interface, this is validated by the core. " +
                "You can also implement helper end points that are used for callbacks and other relevant operations " +
                "specific to implementation."
)
@ApiResponses(
        value = {
                @ApiResponse(
                        responseCode = "400",
                        description = "Bad Request",
                        content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "Not Found",
                        content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal Server Error",
                        content = @Content
                )
        })
public interface InfoController {

    @GetMapping(
            produces = {"application/json"}
            , consumes = {"application/json"}
    )
    @Operation(
            summary = "List supported functions of the connector",
            description = "Returns map of functional code and implemented end points"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Functions found"
                    )
    })
    List<InfoResponse> listSupportedFunctions();

}