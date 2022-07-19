package com.czertainly.api.interfaces.connector.entity;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.common.attribute.AttributeDefinition;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.attribute.RequestAttributeDto;
import com.czertainly.api.model.connector.entity.EntityInstanceDto;
import com.czertainly.api.model.connector.entity.EntityInstanceRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/entityProvider/entities")
@Tag(
        name = "Entity Management API",
        description = "Management interfaces to control Entities in the platform. " +
                "Entities can be created, edited, removed. Support for the bulk operation and listing of available " +
                "Entities for the automation. Location attributes and validation."
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
public interface EntityController {

    @Operation(
            summary = "List available Entity instances"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Entity instances retrieved"
                    )
            })
    @RequestMapping(
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    List<EntityInstanceDto> listEntityInstances();

    @Operation(
            summary = "Get Entity instance"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Entity instance retrieved"
                    )
            })
    @RequestMapping(
            path = "/{entityUuid}",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    EntityInstanceDto getEntityInstance(@Parameter(description = "Entity instance UUID") @PathVariable String entityUuid) throws NotFoundException;

    @Operation(
            summary = "Create Entity instance"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Entity instance created"
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Attribute validation failed",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class, example = "Attribute validation error message")),
                                    examples={@ExampleObject(value="[\"Error Message 1\",\"Error Message 2\"]")})

                    )
            })
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    EntityInstanceDto createEntityInstance(@RequestBody EntityInstanceRequestDto request) throws AlreadyExistException;

    @Operation(
            summary = "Update Entity instance"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Entity instance updated"
                    )
            })
    @RequestMapping(
            path = "/{entityUuid}",
            method = RequestMethod.PUT,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    EntityInstanceDto updateEntityInstance(@Parameter(description = "Entity instance UUID") @PathVariable String entityUuid, @RequestBody EntityInstanceRequestDto request) throws NotFoundException;

    @Operation(
            summary = "Remove Entity instance"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Entity instance removed"
                    )
            })
    @RequestMapping(
            path = "/{entityUuid}",
            method = RequestMethod.DELETE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void removeEntityInstance(@Parameter(description = "Entity instance UUID") @PathVariable String entityUuid) throws NotFoundException;

    @Operation(
            summary = "List Entity Location Attributes"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Entity Location Attributes retrieved"
                    )
            })
    @RequestMapping(
            path = "/{entityUuid}/location/attributes",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    List<AttributeDefinition> listLocationAttributes(
            @Parameter(description = "Entity instance UUID") @PathVariable String entityUuid) throws NotFoundException;

    @Operation(
            summary = "Validate Location Attributes"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Entity Location Attributes validation completed"
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Attribute validation failed",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class, example = "Attribute validation error message")),
                                    examples={@ExampleObject(value="[\"Error Message 1\",\"Error Message 2\"]")})

                    )
            })
    @RequestMapping(
            path = "/{entityUuid}/location/attributes/validate",
            method = RequestMethod.POST,
            consumes = {"application/json"}
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void validateLocationAttributes(
            @Parameter(description = "Entity instance UUID") @PathVariable String entityUuid,
            @RequestBody List<RequestAttributeDto> attributes) throws ValidationException, NotFoundException;
}
