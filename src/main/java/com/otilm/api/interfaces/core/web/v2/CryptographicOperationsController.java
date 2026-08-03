package com.otilm.api.interfaces.core.web.v2;

import com.otilm.api.exception.AttributeException;
import com.otilm.api.exception.ConnectorException;
import com.otilm.api.exception.NotFoundException;
import com.otilm.api.interfaces.AuthProtectedController;
import com.otilm.api.model.client.cryptography.operations.*;
import com.otilm.api.model.common.attribute.common.BaseAttribute;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/v2/operations/tokens/{tokenUuid}/tokenProfiles/{profileUuid}")
@Tag(name = "Cryptographic Operations v2")
public interface CryptographicOperationsController extends AuthProtectedController {

    @Operation(summary = "List encryption attributes for a v2 key item")
    @GetMapping(path = "/keys/{keyUuid}/items/{itemUuid}/encryption/attributes",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<BaseAttribute> listEncryptionAttributes(@PathVariable UUID tokenUuid,
                                                 @PathVariable UUID profileUuid,
                                                 @PathVariable UUID keyUuid,
                                                 @PathVariable UUID itemUuid)
            throws ConnectorException, AttributeException, NotFoundException;

    @Operation(summary = "Encrypt data with a v2 key item")
    @PostMapping(path = "/keys/{keyUuid}/items/{itemUuid}/encrypt", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    EncryptDataResponseDto encryptData(@PathVariable UUID tokenUuid,
                                       @PathVariable UUID profileUuid,
                                       @PathVariable UUID keyUuid,
                                       @PathVariable UUID itemUuid,
                                       @RequestBody @Valid CipherDataRequestDto request)
            throws ConnectorException, AttributeException, NotFoundException;

    @Operation(summary = "List decryption attributes for a v2 key item")
    @GetMapping(path = "/keys/{keyUuid}/items/{itemUuid}/decryption/attributes",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<BaseAttribute> listDecryptionAttributes(@PathVariable UUID tokenUuid,
                                                 @PathVariable UUID profileUuid,
                                                 @PathVariable UUID keyUuid,
                                                 @PathVariable UUID itemUuid)
            throws ConnectorException, AttributeException, NotFoundException;

    @Operation(summary = "Decrypt data with a v2 key item")
    @PostMapping(path = "/keys/{keyUuid}/items/{itemUuid}/decrypt", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    DecryptDataResponseDto decryptData(@PathVariable UUID tokenUuid,
                                       @PathVariable UUID profileUuid,
                                       @PathVariable UUID keyUuid,
                                       @PathVariable UUID itemUuid,
                                       @RequestBody @Valid CipherDataRequestDto request)
            throws ConnectorException, AttributeException, NotFoundException;

    @Operation(summary = "List signing attributes for a v2 key item")
    @GetMapping(path = "/keys/{keyUuid}/items/{itemUuid}/signature/attributes",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<BaseAttribute> listSignatureAttributes(@PathVariable UUID tokenUuid,
                                                @PathVariable UUID profileUuid,
                                                @PathVariable UUID keyUuid,
                                                @PathVariable UUID itemUuid)
            throws ConnectorException, AttributeException, NotFoundException;

    @Operation(summary = "Sign data with a v2 key item")
    @PostMapping(path = "/keys/{keyUuid}/items/{itemUuid}/sign", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    SignDataResponseDto signData(@PathVariable UUID tokenUuid,
                                 @PathVariable UUID profileUuid,
                                 @PathVariable UUID keyUuid,
                                 @PathVariable UUID itemUuid,
                                 @RequestBody @Valid SignDataRequestDto request)
            throws ConnectorException, AttributeException, NotFoundException;

    @Operation(summary = "List verification attributes for a v2 key item")
    @GetMapping(path = "/keys/{keyUuid}/items/{itemUuid}/verification/attributes",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<BaseAttribute> listVerificationAttributes(@PathVariable UUID tokenUuid,
                                                   @PathVariable UUID profileUuid,
                                                   @PathVariable UUID keyUuid,
                                                   @PathVariable UUID itemUuid)
            throws ConnectorException, AttributeException, NotFoundException;

    @Operation(summary = "Verify data with a v2 key item")
    @PostMapping(path = "/keys/{keyUuid}/items/{itemUuid}/verify", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    VerifyDataResponseDto verifyData(@PathVariable UUID tokenUuid,
                                     @PathVariable UUID profileUuid,
                                     @PathVariable UUID keyUuid,
                                     @PathVariable UUID itemUuid,
                                     @RequestBody @Valid VerifyDataRequestDto request)
            throws ConnectorException, AttributeException, NotFoundException;

    @Operation(summary = "List random generator attributes for a v2 token profile")
    @GetMapping(path = "/random/attributes", produces = MediaType.APPLICATION_JSON_VALUE)
    List<BaseAttribute> listRandomAttributes(@PathVariable UUID tokenUuid,
                                             @PathVariable UUID profileUuid)
            throws ConnectorException, AttributeException, NotFoundException;

    @Operation(summary = "Generate random data with a v2 token profile")
    @PostMapping(path = "/random", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    RandomDataResponseDto randomData(@PathVariable UUID tokenUuid,
                                     @PathVariable UUID profileUuid,
                                     @RequestBody @Valid RandomDataRequestDto request)
            throws ConnectorException, AttributeException, NotFoundException;
}
