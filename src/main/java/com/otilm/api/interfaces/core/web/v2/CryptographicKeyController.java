package com.otilm.api.interfaces.core.web.v2;

import com.otilm.api.exception.AttributeException;
import com.otilm.api.exception.ConnectorException;
import com.otilm.api.exception.NotFoundException;
import com.otilm.api.interfaces.AuthProtectedController;
import com.otilm.api.model.client.cryptography.key.KeyRequestType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v2/tokens/{tokenInstanceUuid}/tokenProfiles/{tokenProfileUuid}/keys")
@Tag(name = "Cryptographic Key Management v2")
public interface CryptographicKeyController extends AuthProtectedController {

    @Operation(summary = "List supported key request types")
    @GetMapping(path = "/types", produces = MediaType.APPLICATION_JSON_VALUE)
    List<@NotNull KeyRequestType> listSupportedKeyTypes(@PathVariable UUID tokenInstanceUuid,
            @PathVariable UUID tokenProfileUuid) throws ConnectorException, AttributeException, NotFoundException;

}
