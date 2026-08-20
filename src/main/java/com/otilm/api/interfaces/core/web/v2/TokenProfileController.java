package com.otilm.api.interfaces.core.web.v2;

import com.otilm.api.exception.AttributeException;
import com.otilm.api.exception.ConnectorException;
import com.otilm.api.exception.NotFoundException;
import com.otilm.api.interfaces.AuthProtectedController;
import com.otilm.api.model.common.ErrorMessageDto;
import com.otilm.api.model.common.attribute.common.BaseAttribute;
import com.otilm.api.model.core.cryptography.key.KeyUsage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v2")
@Tag(name = "Token Profile Management v2", description = "Token Profile Management API v2")
@ApiResponses(value = {
        @ApiResponse(responseCode = "502", description = "Connector Error",
                content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))),
        @ApiResponse(responseCode = "503", description = "Connector Communication Error",
                content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))),})

public interface TokenProfileController extends AuthProtectedController {
    @Operation(operationId = "listAvailableTokenProfileKeyUsagesV2",
            summary = "List all token-profile key usages supported by this token")
    @GetMapping(path = "/tokens/{uuid}/tokenProfiles/keyUsages", produces = MediaType.APPLICATION_JSON_VALUE)
    List<KeyUsage> listTokenProfileKeyUsages(@Parameter(description = "Token instance UUID") @PathVariable UUID uuid)
            throws ConnectorException, NotFoundException;

    @Operation(operationId = "listTokenProfileAttributesV2",
            summary = "List token-profile attributes supported by this token")
    @GetMapping(path = "/tokens/{uuid}/tokenProfiles/attributes", produces = MediaType.APPLICATION_JSON_VALUE)
    List<BaseAttribute> listTokenProfileAttributes(
            @Parameter(description = "Token instance UUID") @PathVariable UUID uuid)
            throws ConnectorException, AttributeException, NotFoundException;

}
