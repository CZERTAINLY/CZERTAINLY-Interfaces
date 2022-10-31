package com.czertainly.api.interfaces.connector;

import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.attribute.BaseAttribute;
import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    List<BaseAttribute> listAttributeDefinitions(@Parameter(description = "Kind") @PathVariable String kind);

    @PostMapping(
            path = "/validate",
            consumes = {"application/json"},
            produces = { "application/json" }

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
                            description = "Attribute validation failed",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class, example = "Attribute Validation error message")),
                                    examples={@ExampleObject(value="[\"Error Message 1\",\"Error Message 2\"]")})

                    )
            }
    )
    void validateAttributes(@Parameter(required = true, description = "Kind") @PathVariable String kind, @RequestBody List<RequestAttributeDto> attributes) throws ValidationException;

}