package com.czertainly.api.interfaces.connector;

import java.util.List;

import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.commons.AttributeDefinition;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1/{functionalGroup}/{kind}/attributes")
@Tag(
        name = "Attributes API",
        description = "Connector Attributes API. " +
                "Provides information about supported attributes of the connector. " +
                "Attributes are specific to implementation and gives information about the " +
                "data that can be exchanged and properly parsed by the connector. " +
                "Part of this API is validation of the attributes."
)
public interface AttributesController {

    @GetMapping(
            produces = {"application/json"}
    )
    @Operation(
            summary = "List available attributes",
            description = "Returns attribute definition used for the connector"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Operation successful"
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
            summary = "Validate attributes",
            description = "Returns true if the validation of attributes is successful"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Operation successful"
                    )
            }
    )
    boolean validateAttributes(@PathVariable String kind, @RequestBody List<AttributeDefinition> attributes) throws ValidationException;

}