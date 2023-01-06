package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.client.cryptography.key.BulkKeyUsageRequestDto;
import com.czertainly.api.model.client.cryptography.key.EditKeyRequestDto;
import com.czertainly.api.model.client.cryptography.key.KeyRequestDto;
import com.czertainly.api.model.client.cryptography.key.KeyRequestType;
import com.czertainly.api.model.client.cryptography.key.UpdateKeyUsageRequestDto;
import com.czertainly.api.model.common.AuthenticationServiceExceptionDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.attribute.v2.BaseAttribute;
import com.czertainly.api.model.core.cryptography.key.KeyDetailDto;
import com.czertainly.api.model.core.cryptography.key.KeyDto;
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
    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------
    // List and Detail Operation
    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------
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

    // -----------------------------------------------------------------------------------------------------------------

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

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------
    // Create and Update Operation
    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------
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

    // -----------------------------------------------------------------------------------------------------------------

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

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------
    // Compromise
    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @Operation(
            summary = "Mark Key and its Items as Compromised",
            description = "If the request body is provided with the UUID of the items of Key, then only those items" +
                    "will be compromised. Else all the sub items of the key will be compromised"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Key marked as compromised")
            }
    )
    @RequestMapping(
            path = "/tokens/{tokenInstanceUuid}/keys/{uuid}/compromise",
            method = RequestMethod.PATCH,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void compromiseKey(
            @Parameter(description = "Token Instance UUID") @PathVariable String tokenInstanceUuid,
            @Parameter(description = "Key UUID") @PathVariable String uuid,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Key Item UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
            @RequestBody(required = false) List<String> uuids)
            throws NotFoundException;

    // -----------------------------------------------------------------------------------------------------------------

    @Operation(
            summary = "Mark Multiple Key and its Items as Compromised",
            description = "This API can be used to mark multiple keys and its sub items to be marked as compromised." +
                    "Specific part of the key cannot be mentioned in this API"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Key marked as compromised")
            }
    )
    @RequestMapping(
            path = "/keys/compromise",
            method = RequestMethod.PATCH,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void compromiseKeys(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Key UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
            @RequestBody List<String> uuids);

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------
    // Destroy
    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @Operation(
            summary = "Destroy Cryptographic Key",
            description = "If the request body provided, only those key items will be destroyed. If the request body is " +
                    "not provided or given empty, then the entire key will be destroyed"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Keys destroyed")
            }
    )
    @RequestMapping(
            path = "/tokens/{tokenInstanceUuid}/keys/{uuid}/destroy",
            method = RequestMethod.PATCH,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void destroyKey(
            @Parameter(description = "Token Instance UUID") @PathVariable String tokenInstanceUuid,
            @Parameter(description = "Key UUID") @PathVariable String uuid,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Key UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
            @RequestBody(required = false) List<String> keyUuids)
            throws ConnectorException;

    // -----------------------------------------------------------------------------------------------------------------

    @Operation(
            summary = "Destroy Multiple Cryptographic Key and its items"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Keys destroyed")
            }
    )
    @RequestMapping(
            path = "/keys/destroy",
            method = RequestMethod.PATCH,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void destroyKey(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Key UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
            @RequestBody List<String> keyUuids)
            throws ConnectorException;


    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------
    // Delete
    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @Operation(
            summary = "Delete Cryptographic Key",
            description = "If the request body provided, only those key items will be deleted. If the request body is " +
                    "not provided or given empty, then the entire key will be destroyed"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Keys deleted")
            }
    )
    @RequestMapping(
            path = "/tokens/{tokenInstanceUuid}/keys/{uuid}",
            method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteKey(
            @Parameter(description = "Token Instance UUID") @PathVariable String tokenInstanceUuid,
            @Parameter(description = "Key UUID") @PathVariable String uuid,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Key Item UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
            @RequestBody(required = false) List<String> keyUuids)
            throws ConnectorException;

    // -----------------------------------------------------------------------------------------------------------------

    @Operation(
            summary = "Delete Multiple Cryptographic Key"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Keys deleted")
            }
    )
    @RequestMapping(
            path = "/keys",
            method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteKeys(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Key UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
            @RequestBody List<String> keyUuids)
            throws ConnectorException;

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------
    // Enable
    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @Operation(
            summary = "Enable Key",
            description = "If the request body provided, only those key items will be enabled. If the request body is " +
                    "not provided or given empty, then the entire key will be enabled"
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
    void enableKey(
            @Parameter(description = "Token Instance UUID") @PathVariable String tokenInstanceUuid,
            @Parameter(description = "Key UUID") @PathVariable String uuid,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Key Item UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
            @RequestBody(required = false) List<String> keyUuids)
            throws NotFoundException;

    // -----------------------------------------------------------------------------------------------------------------

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
    void bulkEnableKeys(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Key UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
                             @RequestBody List<String> uuids);

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------
    // Disable
    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @Operation(
            summary = "Disable Key",
            description = "If the request body provided, only those key items will be disabled. If the request body is " +
                    "not provided or given empty, then the entire key will be disabled"
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
            @Parameter(description = "Key UUID") @PathVariable String uuid,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Key Item UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
            @RequestBody(required = false) List<String> keyUuids)
            throws NotFoundException;

    // -----------------------------------------------------------------------------------------------------------------

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
    void bulkDisableKeys(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Key UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
                              @RequestBody List<String> uuids);

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------
    // Usages
    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @Operation(
            summary = "Update Key Usage",
            description = "If the request body provided, only those key items will be updated. If the request body is " +
                    "not provided or given empty, then the entire key will be updated"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Keys destroyed")
            }
    )
    @RequestMapping(
            path = "/tokens/{tokenInstanceUuid}/keys/{uuid}/usages",
            method = RequestMethod.PATCH,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateKeyUsages(
            @Parameter(description = "Token Instance UUID") @PathVariable String tokenInstanceUuid,
            @Parameter(description = "Key UUID") @PathVariable String uuid,
            @RequestBody UpdateKeyUsageRequestDto request)
            throws NotFoundException, ValidationException;

    // -----------------------------------------------------------------------------------------------------------------

    @Operation(
            summary = "Update Key Usages for Multiple Keys",
            description = "Update the key usages for multiple keys and all the items inside it"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Keys destroyed")
            }
    )
    @RequestMapping(
            path = "/keys/usages",
            method = RequestMethod.PATCH,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateKeysUsages(@RequestBody BulkKeyUsageRequestDto request);

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------
    // Attribute related API
    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

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
