package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.*;
import com.czertainly.api.model.client.credential.CredentialRequestDto;
import com.czertainly.api.model.client.credential.CredentialUpdateRequestDto;
import com.czertainly.api.model.common.AuthenticationServiceExceptionDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.UuidDto;
import com.czertainly.api.model.core.credential.CredentialDto;
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

@RestController
@RequestMapping("/v1/credentials")
@Tag(name = "Credential Management", description = "Credential Management API")
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
                ),
                @ApiResponse(
                        responseCode = "502",
                        description = "Connector Error",
                        content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))
                ),
                @ApiResponse(
                        responseCode = "503",
                        description = "Connector Communication Error",
                        content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))
                ),
        })

public interface CredentialController {

    @Operation(summary = "List of All Credentials")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of all Credentials")})
    @RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
    public List<CredentialDto> listCredentials();

    @Operation(summary = "Details of a Credentials")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Credential details retrieved")})
    @RequestMapping(path = "/{uuid}", method = RequestMethod.GET, produces = {"application/json"})
    public CredentialDto getCredential(@Parameter(description = "Credential UUID") @PathVariable String uuid) throws NotFoundException;

    @Operation(summary = "Add Credential")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "New Credential added", content = @Content(schema = @Schema(implementation = UuidDto.class))),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> createCredential(@RequestBody CredentialRequestDto request)
            throws AlreadyExistException, NotFoundException, ConnectorException, AttributeException;

    @Operation(summary = "Edit Credential")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Credential updated"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @RequestMapping(path = "/{uuid}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {
            "application/json"})
    public CredentialDto editCredential(@Parameter(description = "Credential UUID") @PathVariable String uuid, @RequestBody CredentialUpdateRequestDto request)
            throws NotFoundException, ConnectorException, AttributeException;

    @Operation(summary = "Delete Credential")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Credential deleted"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @RequestMapping(path = "/{uuid}", method = RequestMethod.DELETE, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCredential(@Parameter(description = "Credential UUID") @PathVariable String uuid) throws NotFoundException;

    @Operation(summary = "Enable Credential")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Credential enabled")})
    @RequestMapping(path = "/{uuid}/enable", method = RequestMethod.PATCH, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enableCredential(@Parameter(description = "Credential UUID") @PathVariable String uuid) throws NotFoundException;

    @Operation(summary = "Disable Credential")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Credential disabled")})
    @RequestMapping(path = "/{uuid}/disable", method = RequestMethod.PATCH, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disableCredential(@Parameter(description = "Credential UUID") @PathVariable String uuid) throws NotFoundException;

    @Operation(summary = "Delete multiple Credentials")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Credentials deleted"),
            @ApiResponse(responseCode = "422", description = "Unprocessible Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @RequestMapping(method = RequestMethod.DELETE, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void bulkDeleteCredential(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Credential UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
                                                            @RequestBody List<String> uuids) throws NotFoundException, ValidationException;
}
