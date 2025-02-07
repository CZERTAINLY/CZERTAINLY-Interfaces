package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.*;
import com.czertainly.api.model.client.certificate.SearchRequestDto;
import com.czertainly.api.model.client.cryptography.CryptographicKeyResponseDto;
import com.czertainly.api.model.client.cryptography.key.*;
import com.czertainly.api.model.common.AuthenticationServiceExceptionDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.attribute.v2.BaseAttribute;
import com.czertainly.api.model.core.cryptography.key.KeyDetailDto;
import com.czertainly.api.model.core.cryptography.key.KeyDto;
import com.czertainly.api.model.core.cryptography.key.KeyEventHistoryDto;
import com.czertainly.api.model.core.cryptography.key.KeyItemDetailDto;
import com.czertainly.api.model.core.search.SearchFieldDataByGroupDto;
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

    @Operation(summary = "Get CryptographicKey searchable fields information")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "CryptographicKey searchable field information retrieved")})
    @GetMapping(path = "/keys/search", produces = {"application/json"})
    List<SearchFieldDataByGroupDto> getSearchableFieldInformation();


    @Operation(summary = "List cryptographic keys")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of all the cryptographic keys")})
    @PostMapping(path = "/keys", produces = MediaType.APPLICATION_JSON_VALUE)
    CryptographicKeyResponseDto listCryptographicKeys(@RequestBody SearchRequestDto request) throws ValidationException;


    // -----------------------------------------------------------------------------------------------------------------

    @Operation(
            summary = "List Cryptographic Keys with full Key Pairs",
            description = "This API contains the logic to get the keys that contains the full key pair (private and public Key)"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cryptographic Keys retrieved")
            })
    @GetMapping(
            path = "/keys/pairs",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<KeyDto> listKeyPairs(@RequestParam(required = false) Optional<String> tokenProfileUuid);

    // -----------------------------------------------------------------------------------------------------------------

    @Operation(
            summary = "Get Cryptographic Key Detail with Token Instance"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cryptographic Key Detail retrieved")
            })
    @GetMapping(
            path = "/tokens/{tokenInstanceUuid}/keys/{uuid}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    KeyDetailDto getKey(
            @Parameter(description = "UUID of the Token Instance") @PathVariable String tokenInstanceUuid,
            @Parameter(description = "UUID of the Key") @PathVariable String uuid
    ) throws NotFoundException;


    @Operation(
            summary = "Get Cryptographic Key Detail without Token Instance"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cryptographic Key Detail retrieved")
            })
    @GetMapping(
            path = "/keys/{uuid}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    KeyDetailDto getKey(
            @Parameter(description = "UUID of the Key") @PathVariable String uuid
    ) throws NotFoundException;


    @Operation(
            summary = "Get Cryptographic Key Detail without Token Instance"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cryptographic Key Detail retrieved")
            })
    @GetMapping(
            path = "/keys/{uuid}/items/{keyItemUuid}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    KeyItemDetailDto getKeyItem(
            @Parameter(description = "UUID of the Key") @PathVariable String uuid,
            @Parameter(description = "UUID of the Key Item") @PathVariable String keyItemUuid
    ) throws NotFoundException;

    @Operation(
            summary = "Get Cryptographic Key Detail with Token Instance"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cryptographic Key Detail retrieved")
            })
    @GetMapping(
            path = "/tokens/{tokenInstanceUuid}/keys/{uuid}/items/{keyItemUuid}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    KeyItemDetailDto getKeyItem(
            @Parameter(description = "UUID of the Token Instance") @PathVariable String tokenInstanceUuid,
            @Parameter(description = "UUID of the Key") @PathVariable String uuid,
            @Parameter(description = "UUID of the Key Item") @PathVariable String keyItemUuid
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
        @PostMapping(
            path = "/tokens/{tokenInstanceUuid}/tokenProfiles/{tokenProfileUuid}/keys/{type}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    KeyDetailDto createKey(@Parameter(description = "UUID of the Token Instance") @PathVariable String tokenInstanceUuid,
                           @Parameter(description = "UUID of the Token Profile") @PathVariable String tokenProfileUuid,
                           @Parameter(description = "Type of the key to be created") @PathVariable KeyRequestType type,
                           @RequestBody KeyRequestDto request
    ) throws AlreadyExistException, ValidationException, ConnectorException, AttributeException;

    // -----------------------------------------------------------------------------------------------------------------

    @Operation(
            summary = "Edit Key"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Key updated"),
                    @ApiResponse(responseCode = "422", description = "Unprocessible Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                            examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @PutMapping(
            path = "/keys/{uuid}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    KeyDetailDto editKey(
            @Parameter(description = "Key UUID") @PathVariable String uuid,
            @RequestBody EditKeyRequestDto request)
            throws ConnectorException, AttributeException;

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------
    // Sync Keys
    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @Operation(
            summary = "Sync Keys from connector"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Key sync completed")
            }
    )
    @PatchMapping(
            path = "/tokens/{tokenInstanceUuid}/sync"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void syncKeys(
            @Parameter(description = "Token Instance UUID") @PathVariable String tokenInstanceUuid
    )
            throws ConnectorException, AttributeException;

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
    @PatchMapping(
            path = "/tokens/{tokenInstanceUuid}/keys/{uuid}/compromise",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void compromiseKey(
            @Parameter(description = "Token Instance UUID") @PathVariable String tokenInstanceUuid,
            @Parameter(description = "Key UUID") @PathVariable String uuid,
            @RequestBody CompromiseKeyRequestDto request)
            throws NotFoundException;

    // -----------------------------------------------------------------------------------------------------------------

    @Operation(
            summary = "Mark Multiple Key and all its Items as Compromised",
            description = "This API can be used to mark multiple keys and its sub items to be marked as compromised." +
                    "Specific part of the key cannot be mentioned in this API"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Key marked as compromised")
            }
    )
    @PatchMapping(
            path = "/keys/compromise",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void compromiseKeys(@RequestBody BulkCompromiseKeyRequestDto request);


    // -----------------------------------------------------------------------------------------------------------------

    @Operation(
            summary = "Mark Multiple Key Items as Compromised",
            description = "This API can be used to mark multiple keys items to be marked as compromised."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Key Items marked as compromised")
            }
    )
    @PatchMapping(
            path = "/keys/items/compromise",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void compromiseKeyItems(@RequestBody BulkCompromiseKeyItemRequestDto request);

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
    @PatchMapping(
            path = "/tokens/{tokenInstanceUuid}/keys/{uuid}/destroy",
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
            @RequestBody(required = false) List<String> keyItemUuids)
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
    @PatchMapping(
            path = "/keys/destroy",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void destroyKeys(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Key UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
            @RequestBody List<String> keyUuids)
            throws ConnectorException;

    // -----------------------------------------------------------------------------------------------------------------

    @Operation(
            summary = "Destroy Multiple Cryptographic Key items"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Keys Items destroyed")
            }
    )
    @PatchMapping(
            path = "/keys/items/destroy",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void destroyKeyItems(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Key Item UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
            @RequestBody List<String> keyItemUuids)
            throws ConnectorException;


    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------
    // Delete
    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @Operation(
            summary = "Delete Cryptographic Key without Token Instance",
            description = "If the request body provided, only those key items will be deleted. If the request body is " +
                    "not provided or given empty, then the entire key will be destroyed"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Key deleted")
            }
    )
    @DeleteMapping(
            path = "/keys/{uuid}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteKey(
            @Parameter(description = "Key UUID") @PathVariable String uuid,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Key Item UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
            @RequestBody(required = false) List<String> keyItemUuids)
            throws ConnectorException;

    @Operation(
            summary = "Delete Cryptographic Key with Token Instance",
            description = "If the request body provided, only those key items will be deleted. If the request body is " +
                    "not provided or given empty, then the entire key will be destroyed"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Key deleted")
            }
    )
    @DeleteMapping(
            path = "/tokens/{tokenInstanceUuid}/keys/{uuid}",
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
            @RequestBody(required = false) List<String> keyItemUuids)
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
    @DeleteMapping(
            path = "/keys",
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

    @Operation(
            summary = "Delete Multiple Cryptographic Key Items"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Key Items deleted")
            }
    )
    @DeleteMapping(
            path = "/keys/items",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteKeyItems(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Key Items UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
            @RequestBody List<String> keyItemUuids)
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
    @PatchMapping(
            path = "/tokens/{tokenInstanceUuid}/keys/{uuid}/enable",
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
            @RequestBody(required = false) List<String> keyItemUuids)
            throws NotFoundException;

    // -----------------------------------------------------------------------------------------------------------------

    @Operation(
            summary = "Enable multiple Keys"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Keys enabled")
            })
    @PatchMapping(path = "/keys/enable",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void enableKeys(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Key UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
                    @RequestBody List<String> uuids);


    // -----------------------------------------------------------------------------------------------------------------

    @Operation(
            summary = "Enable multiple Key Items"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Key Items enabled")
            })
    @PatchMapping(path = "/keys/items/enable",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void enableKeyItems(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Key Item UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
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
    @PatchMapping(
            path = "/tokens/{tokenInstanceUuid}/keys/{uuid}/disable",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void disableKey(
            @Parameter(description = "Token Instance UUID") @PathVariable String tokenInstanceUuid,
            @Parameter(description = "Key UUID") @PathVariable String uuid,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Key Item UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
            @RequestBody(required = false) List<String> keyItemUuids)
            throws NotFoundException;

    // -----------------------------------------------------------------------------------------------------------------

    @Operation(
            summary = "Disable multiple Keys"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Keys disabled")
            })
    @PatchMapping(
            path = "/keys/disable",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void disableKeys(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Key UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
                     @RequestBody List<String> uuids);

    // -----------------------------------------------------------------------------------------------------------------

    @Operation(
            summary = "Disable multiple Key Items"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Key Items disabled")
            })
    @PatchMapping(
            path = "/keys/items/disable",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void disableKeyItems(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Key Item UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
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
                    @ApiResponse(responseCode = "204", description = "Keys Usages Updates")
            }
    )
    @PutMapping(
            path = "/tokens/{tokenInstanceUuid}/keys/{uuid}/usages",
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
                    @ApiResponse(responseCode = "204", description = "Keys Usages Updated")
            }
    )
    @PutMapping(
            path = "/keys/usages",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateKeysUsages(@RequestBody BulkKeyUsageRequestDto request);


    // -----------------------------------------------------------------------------------------------------------------

    @Operation(
            summary = "Update Key Usages for Multiple Key Items",
            description = "Update the key usages for multiple keys Items"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Key Items Usages Updated")
            }
    )
    @PutMapping(
            path = "/keys/items/usages",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateKeyItemUsages(@RequestBody BulkKeyItemUsageRequestDto request);

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
    @GetMapping(
            path = "/tokens/{tokenInstanceUuid}/tokenProfiles/{tokenProfileUuid}/keys/{type}/attributes",
            produces = {"application/json"}
    )
    List<BaseAttribute> listCreateKeyAttributes(
            @Parameter(description = "Token Instance UUID") @PathVariable String tokenInstanceUuid,
            @Parameter(description = "Token Profile UUID") @PathVariable String tokenProfileUuid,
            @Parameter(description = "Type of the key to be created") @PathVariable KeyRequestType type
    ) throws ConnectorException;

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------
    // History API
    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @Operation(
            summary = "Get Key Item event history"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Certificate event history retrieved")
            })
    @GetMapping(
            path = "/tokens/{tokenInstanceUuid}/keys/{uuid}/items/{keyItemUuid}/history",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<KeyEventHistoryDto> getEventHistory(
            @Parameter(description = "Token Instance UUID") @PathVariable String tokenInstanceUuid,
            @Parameter(description = "Key UUID") @PathVariable String uuid,
            @Parameter(description = "Key Item UUID") @PathVariable String keyItemUuid
    ) throws NotFoundException;
}
