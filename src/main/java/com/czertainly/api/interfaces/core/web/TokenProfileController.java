package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.*;
import com.czertainly.api.model.client.cryptography.tokenprofile.AddTokenProfileRequestDto;
import com.czertainly.api.model.client.cryptography.tokenprofile.BulkTokenProfileKeyUsageRequestDto;
import com.czertainly.api.model.client.cryptography.tokenprofile.EditTokenProfileRequestDto;
import com.czertainly.api.model.client.cryptography.tokenprofile.TokenProfileKeyUsageRequestDto;
import com.czertainly.api.model.common.AuthenticationServiceExceptionDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.core.cryptography.tokenprofile.TokenProfileDetailDto;
import com.czertainly.api.model.core.cryptography.tokenprofile.TokenProfileDto;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1")
@Tag(name = "Token Profile Management", description = "Token Profile Management API")
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

public interface TokenProfileController {
    @Operation(
            summary = "List of available Token Profiles"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Token Profiles retrieved")
            })
    @RequestMapping(
            path = "/tokenProfiles",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<TokenProfileDto> listTokenProfiles(Optional<Boolean> enabled);

    @Operation(
            summary = "Details of Token Profile"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Token Profile details retrieved")
            })
    @RequestMapping(
            path = "/tokens/{tokenInstanceUuid}/tokenProfiles/{uuid}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    TokenProfileDetailDto getTokenProfile(
            @Parameter(description = "Token Instance UUID") @PathVariable String tokenInstanceUuid,
            @Parameter(description = "Token Profile UUID") @PathVariable String uuid)
            throws NotFoundException;

    @Operation(
            summary = "Create Token Profile"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Token Profile added"),
                    @ApiResponse(responseCode = "422", description = "Unprocessible Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                            examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))
            })
    @RequestMapping(
            path = "/tokens/{tokenInstanceUuid}/tokenProfiles",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<TokenProfileDetailDto> createTokenProfile(
            @Parameter(description = "Token Instance UUID") @PathVariable String tokenInstanceUuid,
            @RequestBody AddTokenProfileRequestDto request)
            throws AlreadyExistException, ValidationException, ConnectorException, AttributeException;

    @Operation(
            summary = "Edit Token Profile"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Token Profile updated"),
                    @ApiResponse(responseCode = "422", description = "Unprocessible Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                            examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @RequestMapping(
            path = "/tokens/{tokenInstanceUuid}/tokenProfiles/{uuid}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    TokenProfileDetailDto editTokenProfile(
            @Parameter(description = "Token Instance UUID") @PathVariable String tokenInstanceUuid,
            @Parameter(description = "Token Profile UUID") @PathVariable String uuid,
            @RequestBody EditTokenProfileRequestDto request)
            throws ConnectorException, AttributeException;

    @Operation(
            summary = "Delete Token Profile"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Token Profile deleted")
            })
    @RequestMapping(
            path = "/tokens/{tokenInstanceUuid}/tokenProfiles/{uuid}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteTokenProfile(
            @Parameter(description = "Token Instance UUID") @PathVariable String tokenInstanceUuid,
            @Parameter(description = "Token Profile UUID") @PathVariable String uuid)
            throws NotFoundException;

    @Operation(
            summary = "Delete Token Profile",
            operationId = "deleteRaProfileWithoutTokenInstance"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Token Profile deleted")
            })
    @RequestMapping(
            path = "/tokenProfiles/{uuid}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteTokenProfile(
            @Parameter(description = "Token Profile UUID") @PathVariable String uuid)
            throws NotFoundException;

    @Operation(
            summary = "Disable Token Profile"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Token Profile disabled")
            })
    @RequestMapping(
            path = "/tokens/{tokenInstanceUuid}/tokenProfiles/{uuid}/disable",
            method = RequestMethod.PATCH,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void disableTokenProfile(
            @Parameter(description = "Token Instance UUID") @PathVariable String tokenInstanceUuid,
            @Parameter(description = "Token Profile UUID") @PathVariable String uuid)
            throws NotFoundException;

    @Operation(
            summary = "Enable Token Profile"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Token Profile enabled")
            })
    @RequestMapping(
            path = "/tokens/{tokenInstanceUuid}/tokenProfiles/{uuid}/enable",
            method = RequestMethod.PATCH,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void enableTokenProfile(
            @Parameter(description = "Token Instance UUID") @PathVariable String tokenInstanceUuid,
            @Parameter(description = "Token Profile UUID") @PathVariable String uuid)
            throws NotFoundException;

    @Operation(
            summary = "Delete multiple Token Profiles"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Token Profiles deleted"),
                    @ApiResponse(responseCode = "422", description = "Unprocessible Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                            examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @RequestMapping(path = "/tokenProfiles",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteTokenProfiles(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Token Profile UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
                                @RequestBody List<String> uuids);

    @Operation(
            summary = "Disable multiple Token Profiles"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Token Profiles disabled")
            })
    @RequestMapping(
            path = "/tokenProfiles/disable",
            method = RequestMethod.PATCH,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void disableTokenProfiles(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Token Profile UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
                              @RequestBody List<String> uuids);

    @Operation(
            summary = "Enable multiple Token Profiles"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Token Profiles enabled")
            })
    @RequestMapping(path = "/tokenProfiles/enable",
            method = RequestMethod.PATCH,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void enableTokenProfiles(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Token Profile UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
                             @RequestBody List<String> uuids);

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------
    // Usages
    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @Operation(
            summary = "Update Key Usage"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Key Usages Updated")
            }
    )
    @RequestMapping(
            path = "/tokens/{tokenInstanceUuid}/tokenProfiles/{tokenProfileUuid}/usages",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateKeyUsages(
            @Parameter(description = "Token Instance UUID") @PathVariable String tokenInstanceUuid,
            @Parameter(description = "Token Profile UUID") @PathVariable String tokenProfileUuid,
            @RequestBody TokenProfileKeyUsageRequestDto request)
            throws NotFoundException, ValidationException;

    // -----------------------------------------------------------------------------------------------------------------

    @Operation(
            summary = "Update Key Usages for Multiple Token Profiles"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Key Usages Updated")
            }
    )
    @RequestMapping(
            path = "/tokens/usages",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateKeysUsages(@RequestBody BulkTokenProfileKeyUsageRequestDto request);
}
