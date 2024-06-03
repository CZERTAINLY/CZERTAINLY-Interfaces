package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.AttributeException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.model.client.attribute.AttributeDefinitionDto;
import com.czertainly.api.model.client.attribute.metadata.*;
import com.czertainly.api.model.common.AuthenticationServiceExceptionDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.UuidDto;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/attributes/metadata")
@Tag(name = "Global Metadata", description = "Global Metadata API")
@ApiResponses(
        value = {
                @ApiResponse(
                        responseCode = "400",
                        description = "Bad Request",
                        content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))
                ),
                @ApiResponse(
                        responseCode = "401",
                        description = "Unauthorized",
                        content = @Content(schema = @Schema())
                ),
                @ApiResponse(
                        responseCode = "403",
                        description = "Forbidden",
                        content = @Content(schema = @Schema(implementation = AuthenticationServiceExceptionDto.class))
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

public interface GlobalMetadataController {
    @Operation(summary = "List Global Metadata")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "list of available Global Metadata")})
    @RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
    List<AttributeDefinitionDto> listGlobalMetadata();

    @Operation(summary = "Global Metadata details")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Global Metadata details retrieved")})
    @RequestMapping(path = "/{uuid}", method = RequestMethod.GET, produces = {"application/json"})
    GlobalMetadataDefinitionDetailDto getGlobalMetadata(@PathVariable String uuid) throws NotFoundException;

    @Operation(summary = "Create Global Metadata")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Global Metadata created", content = @Content(schema = @Schema(implementation = UuidDto.class)))})
    @RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    ResponseEntity<GlobalMetadataDefinitionDetailDto> createGlobalMetadata(@RequestBody GlobalMetadataCreateRequestDto request)
            throws AlreadyExistException, NotFoundException, AttributeException;

    @Operation(summary = "Edit Global Metadata")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Global Metadata updated")})
    @RequestMapping(path = "/{uuid}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
    GlobalMetadataDefinitionDetailDto editGlobalMetadata(@Parameter(description = "Global Metadata UUID") @PathVariable String uuid, @RequestBody GlobalMetadataUpdateRequestDto request)
            throws NotFoundException, AttributeException;

    @Operation(summary = "Delete Global Metadata")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Global Metadata deleted")})
    @RequestMapping(path = "/{uuid}", method = RequestMethod.DELETE, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteGlobalMetadata(@Parameter(description = "Global Metadata UUID") @PathVariable String uuid) throws NotFoundException;

    @Operation(summary = "Delete multiple Global Metadata")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Global Metadata deleted")})
    @RequestMapping(method = RequestMethod.DELETE, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void bulkDeleteGlobalMetadata(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Global Metadata UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
                                  @RequestBody List<String> attributeUuids);

    @Operation(summary = "Get Available Connector Metadata")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Connector Metadata retrieved")})
    @RequestMapping(path = "/promote", method = RequestMethod.GET, produces = {"application/json"})
    List<ConnectorMetadataResponseDto> getConnectorMetadata(@RequestParam Optional<String> connectorUuid) throws NotFoundException;

    @Operation(summary = "Promote Connector Metadata to Global Metadata")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Connector Metadata promoted to global metadata")})
    @RequestMapping(path = "/promote", method = RequestMethod.POST,consumes = {"application/json"}, produces = {"application/json"})
    GlobalMetadataDefinitionDetailDto promoteConnectorMetadata(@RequestBody ConnectorMetadataPromotionRequestDto request) throws NotFoundException;
}
