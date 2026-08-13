package com.otilm.api.interfaces.connector.cryptography.v2;

import com.otilm.api.interfaces.connector.common.v2.AuthProtectedConnectorController;
import com.otilm.api.model.common.attribute.common.BaseAttribute;
import com.otilm.api.model.common.error.ProblemDetailExtended;
import com.otilm.api.model.connector.cryptography.v2.key.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Connector-facing V2 key-management interface.
 *
 * <p>
 * Requests carry the token and token-profile context. Existing-key operations additionally carry connector-defined key
 * metadata. Key creation and destruction support synchronous and asynchronous execution. Asynchronous polling and
 * cancellation are available when {@link com.otilm.api.model.client.connector.v2.FeatureFlag#ASYNCHRONOUS} is enabled.
 * </p>
 */
@RequestMapping("/v2/cryptographyProvider/keys")
@Tag(name = "Key Management v2",
        description = "Key operations scoped by token, token-profile and key metadata supplied in requests")
@ApiResponses(@ApiResponse(responseCode = "422",
        description = "Request validation failed (errorCode VALIDATION_FAILED)",
        content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                schema = @Schema(implementation = ProblemDetailExtended.class))))
public interface KeyController extends AuthProtectedConnectorController {

    // ---- Create ----

    @Operation(summary = "List key creation attributes",
            description = "List attributes for creating the requested key type. Definitions must not contain resolved credentials or secret values.")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Schema retrieved"))
    @PostMapping(path = "/create/attributes", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<BaseAttribute> listCreateKeyAttributes(@RequestBody @Valid CreateKeyAttributesRequestV2Dto request);

    @Operation(summary = "Create key",
            description = "Create a secret key or key pair using the request discriminator and caller-selected execution mode (synchronous 200 or asynchronous 202)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created synchronously"),
            @ApiResponse(responseCode = "202",
                    description = "Creation accepted asynchronously; body carries operationMeta tracking handle"),
            @ApiResponse(responseCode = "409",
                    description = "Connector detected reuse of keyCreationId with materially different creation data (RESOURCE_ALREADY_EXISTS)")})
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<KeyCreationResponseV2Dto> createKey(@RequestBody @Valid CreateKeyRequestV2Dto request);

    @Operation(summary = "Get async key creation status",
            description = "Get status of an async secret key or key pair creation")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Key creation status retrieved"),
            @ApiResponse(responseCode = "404", description = "Operation is not tracked")})
    @PostMapping(path = "/create/status", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    KeyCreationStatusResponseV2Dto getCreateKeyStatus(@RequestBody @Valid KeyOperationRequestV2Dto request);

    @Operation(summary = "Cancel async key creation",
            description = "Cancel an in-flight async secret key or key pair creation")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Aborted"),
            @ApiResponse(responseCode = "404", description = "Operation not tracked"),
            @ApiResponse(responseCode = "422",
                    description = "Refused — operation is already terminal or past point of no return")})
    @PostMapping(path = "/create/cancel", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> cancelCreateKey(@RequestBody @Valid KeyOperationRequestV2Dto request);

    // ---- Destroy ----

    @Operation(summary = "Destroy key", description = "Destroy a key using the caller-selected execution mode "
            + "(synchronous 200 or asynchronous 202). After accepting asynchronous destruction, the connector must "
            + "reject new cryptographic operations for that key")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Destroyed synchronously; response body is empty"),
            @ApiResponse(responseCode = "202",
                    description = "Destruction accepted asynchronously; body carries "
                            + "operationMeta tracking handle")})
    @PostMapping(path = "/destroy", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<KeyOperationResponseV2Dto> destroyKey(@RequestBody @Valid DestroyKeyRequestV2Dto request);

    @Operation(summary = "Get async key destruction status", description = "Get status of an async key destruction")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Key destruction status retrieved"),
            @ApiResponse(responseCode = "404", description = "Operation is not tracked")})
    @PostMapping(path = "/destroy/status", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    KeyOperationStatusResponseV2Dto getDestroyKeyStatus(@RequestBody @Valid KeyOperationRequestV2Dto request);

    @Operation(summary = "Cancel async key destruction",
            description = "Cancel an in-flight asynchronous key destruction")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Aborted"),
            @ApiResponse(responseCode = "404", description = "Operation not tracked; cancellation outcome is unknown"),
            @ApiResponse(responseCode = "422",
                    description = "Refused — operation is already terminal or past point of no return")})
    @PostMapping(path = "/destroy/cancel", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> cancelDestroyKey(@RequestBody @Valid KeyOperationRequestV2Dto request);
}
