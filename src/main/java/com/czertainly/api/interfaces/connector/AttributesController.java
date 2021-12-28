package com.czertainly.api.interfaces.connector;

import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.common.AttributeDefinition;
import com.czertainly.api.model.common.RequestAttributeDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/{functionalGroup}/{kind}/attributes")
@Tag(
        name = "Attributes API",
        description = "Connector Attributes API. " +
                "Provides information about supported Attributes of the connector. " +
                "Attributes are specific to implementation and gives information about the " +
                "data that can be exchanged and properly parsed by the connector. " +
                "Part of this API is validation of the Attributes."
)
public interface AttributesController {

    @GetMapping(
            produces = {"application/json"}
    )
    @Operation(
            summary = "List available Attributes"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Attributes retrieved"
                    )
            }
    )
    List<AttributeDefinition> listAttributeDefinitions(@PathVariable String kind);

    @PostMapping(
            path = "/validate",
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    @Operation(
            summary = "Validate Attributes"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Attribute validation completed"
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Attribute validation failed"
                    )
            }
    )
    boolean validateAttributes(@PathVariable String kind, @RequestBody List<RequestAttributeDto> attributes) throws ValidationException;

}