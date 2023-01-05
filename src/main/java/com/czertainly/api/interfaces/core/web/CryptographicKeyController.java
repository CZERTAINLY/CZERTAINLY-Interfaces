package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.client.cryptography.key.EditKeyRequestDto;
import com.czertainly.api.model.client.cryptography.key.KeyRequestDto;
import com.czertainly.api.model.client.cryptography.key.KeyRequestType;
import com.czertainly.api.model.common.AuthenticationServiceExceptionDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.attribute.v2.BaseAttribute;
import com.czertainly.api.model.core.cryptography.key.KeyDetailDto;
import com.czertainly.api.model.core.cryptography.key.KeyDto;
import com.czertainly.api.model.core.cryptography.tokenprofile.TokenProfileDetailDto;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1")
@Tag(name = "Cryptographic Key Controller", description = "Cryptographic Key Controller API")
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
public interface CryptographicKeyController {

    // Token Instance Operation APIs
    @Operation(
            summary = "List Cryptographic Keys"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cryptographic Keys retrieved")
            })
    @RequestMapping(
            path = "/keys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<KeyDto> listKeys(@RequestParam(required = false) Optional<String> tokenProfileUuid);

    @Operation(
            summary = "Get Cryptographic Key Detail"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cryptographic Key Detail retrieved")
            })
    @RequestMapping(
            path = "/tokens/{tokenInstanceUuid}/keys/{uuid}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    KeyDetailDto getKey(
            @Parameter(description = "UUID of the Token Instance") @PathVariable String tokenInstanceUuid,
            @Parameter(description = "UUID of the Key") @PathVariable String uuid
    ) throws NotFoundException;

    @Operation(
            summary = "Create a new Cryptographic Key"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Cryptographic Key Created Successfully"),
                    @ApiResponse(responseCode = "422",
                            description = "Unprocessible Entity",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                                    examples = {
                                            @ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")
                                    })
                    )
            })
    @RequestMapping(
            path = "/tokens/{tokenInstanceUuid}/keys/{type}",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    KeyDetailDto createKey(@Parameter(description = "UUID of the Token Instance") @PathVariable String tokenInstanceUuid,
                           @Parameter(description = "Type of the key to be created") @PathVariable KeyRequestType type,
                           @RequestBody KeyRequestDto request
    ) throws AlreadyExistException, ValidationException, ConnectorException;

    @Operation(
            summary = "Edit Key"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Key updated"),
                    @ApiResponse(responseCode = "422", description = "Unprocessible Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                            examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @RequestMapping(
            path = "/tokens/{tokenInstanceUuid}/keys/{uuid}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    KeyDetailDto editKey(
            @Parameter(description = "Token Instance UUID") @PathVariable String tokenInstanceUuid,
            @Parameter(description = "Key UUID") @PathVariable String uuid,
            @RequestBody EditKeyRequestDto request)
            throws ConnectorException;

    @Operation(
            description = "Mark Key as Compromised"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Key status updated")
            }
    )
    @RequestMapping(
            path = "/tokens/{tokenInstanceUuid}/keys/{uuid}/compromise",
            method = RequestMethod.PATCH,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void compromiseKey(
            @Parameter(description = "Token Instance UUID") @PathVariable String tokenInstanceUuid,
            @Parameter(description = "Key UUID") @PathVariable String uuid,
            @Parameter(description = "List of UUIDs of key to be marked as compromised. " +
                    "If Empty list is provided, all the key items will be marked as compromised")
            @RequestBody List<String> keyUuids)
            throws NotFoundException;

    @Operation(
            description = "Destroy Cryptographic Key"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Keys destroyed")
            }
    )
    @RequestMapping(
            path = "/tokens/{tokenInstanceUuid}/keys/{uuid}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void destroyKey(
            @Parameter(description = "Token Instance UUID") @PathVariable String tokenInstanceUuid,
            @Parameter(description = "Key UUID") @PathVariable String uuid,
            @Parameter(description = "List of UUIDs of keys available in the key pair." +
                    "If empty list is provided, all the key items will be destroyed")
            @RequestBody List<String> keyUuids)
            throws ConnectorException;

    @Operation(
            description = "Delete Cryptographic Key"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Keys deleted")
            }
    )
    @RequestMapping(
            path = "/tokens/{tokenInstanceUuid}/keys/{uuid}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteKey(
            @Parameter(description = "Token Instance UUID") @PathVariable String tokenInstanceUuid,
            @Parameter(description = "Key UUID") @PathVariable String uuid,
            @Parameter(description = "List of UUIDs of keys available in the key pair." +
                    "If empty list is provided, all the key items will be deleted")
            @RequestBody List<String> keyUuids)
            throws ConnectorException;

    @Operation(
            summary = "Disable Key"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Key disabled")
            })
    @RequestMapping(
            path = "/tokens/{tokenInstanceUuid}/key/{uuid}/disable",
            method = RequestMethod.PATCH,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void disableKey(
            @Parameter(description = "Token Instance UUID") @PathVariable String tokenInstanceUuid,
            @Parameter(description = "Key UUID") @PathVariable String uuid)
            throws NotFoundException;

    @Operation(
            summary = "Enable Key"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Key enabled")
            })
    @RequestMapping(
            path = "/tokens/{tokenInstanceUuid}/keys/{uuid}/enable",
            method = RequestMethod.PATCH,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void enableTokenProfile(
            @Parameter(description = "Token Instance UUID") @PathVariable String tokenInstanceUuid,
            @Parameter(description = "Key UUID") @PathVariable String uuid)
            throws NotFoundException;


    @Operation(
            summary = "Disable multiple Keys"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Keys disabled")
            })
    @RequestMapping(
            path = "/keys/disable",
            method = RequestMethod.PATCH,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void bulkDisableRaProfile(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Key UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
                              @RequestBody List<String> uuids);

    @Operation(
            summary = "Enable multiple Keys"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Keys enabled")
            })
    @RequestMapping(path = "/keys/enable",
            method = RequestMethod.PATCH,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void bulkEnableRaProfile(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Key UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
                             @RequestBody List<String> uuids);

    // Attribute related API

    @Operation(
            summary = "List of Attributes to create a Key"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of Attributes retrieved"
                    )
            })
    @RequestMapping(
            path = "/tokens/{tokenInstanceUuid}/keys/{type}/attributes",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    List<BaseAttribute> listCreateKeyAttributes(
            @Parameter(description = "Token instance UUID") @PathVariable String tokenInstanceUuid,
            @Parameter(description = "Type of the key to be created") @PathVariable KeyRequestType type
    ) throws ConnectorException;
}
