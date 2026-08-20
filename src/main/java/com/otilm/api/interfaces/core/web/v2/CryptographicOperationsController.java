package com.otilm.api.interfaces.core.web.v2;

import com.otilm.api.exception.AttributeException;
import com.otilm.api.exception.ConnectorException;
import com.otilm.api.exception.NotFoundException;
import com.otilm.api.interfaces.AuthProtectedController;
import com.otilm.api.model.client.cryptography.operations.RandomDataRequestDto;
import com.otilm.api.model.client.cryptography.operations.RandomDataResponseDto;
import com.otilm.api.model.common.attribute.common.BaseAttribute;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v2/operations/tokens/{tokenUuid}/tokenProfiles/{profileUuid}")
@Tag(name = "Cryptographic Operations v2")
public interface CryptographicOperationsController extends AuthProtectedController {

    @Operation(summary = "List encryption attributes for a v2 key item")
    @GetMapping(path = "/keys/{keyUuid}/items/{itemUuid}/encryption/attributes",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<BaseAttribute> listEncryptionAttributes(@PathVariable UUID tokenUuid, @PathVariable UUID profileUuid,
            @PathVariable UUID keyUuid, @PathVariable UUID itemUuid)
            throws ConnectorException, AttributeException, NotFoundException;

    @Operation(summary = "List decryption attributes for a v2 key item")
    @GetMapping(path = "/keys/{keyUuid}/items/{itemUuid}/decryption/attributes",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<BaseAttribute> listDecryptionAttributes(@PathVariable UUID tokenUuid, @PathVariable UUID profileUuid,
            @PathVariable UUID keyUuid, @PathVariable UUID itemUuid)
            throws ConnectorException, AttributeException, NotFoundException;

    @Operation(summary = "List signing attributes for a v2 key item")
    @GetMapping(path = "/keys/{keyUuid}/items/{itemUuid}/signature/attributes",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<BaseAttribute> listSignatureAttributes(@PathVariable UUID tokenUuid, @PathVariable UUID profileUuid,
            @PathVariable UUID keyUuid, @PathVariable UUID itemUuid)
            throws ConnectorException, AttributeException, NotFoundException;

    @Operation(summary = "List verification attributes for a v2 key item")
    @GetMapping(path = "/keys/{keyUuid}/items/{itemUuid}/verification/attributes",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<BaseAttribute> listVerificationAttributes(@PathVariable UUID tokenUuid, @PathVariable UUID profileUuid,
            @PathVariable UUID keyUuid, @PathVariable UUID itemUuid)
            throws ConnectorException, AttributeException, NotFoundException;

    @Operation(summary = "List random generator attributes for a v2 token profile")
    @GetMapping(path = "/random/attributes", produces = MediaType.APPLICATION_JSON_VALUE)
    List<BaseAttribute> listRandomAttributes(@PathVariable UUID tokenUuid, @PathVariable UUID profileUuid)
            throws ConnectorException, AttributeException, NotFoundException;

    @Operation(summary = "Generate random data with a v2 token profile")
    @PostMapping(path = "/random", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    RandomDataResponseDto randomData(@PathVariable UUID tokenUuid, @PathVariable UUID profileUuid,
            @RequestBody @Valid RandomDataRequestDto request)
            throws ConnectorException, AttributeException, NotFoundException;
}
