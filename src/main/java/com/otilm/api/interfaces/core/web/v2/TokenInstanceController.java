package com.otilm.api.interfaces.core.web.v2;

import com.otilm.api.exception.*;
import com.otilm.api.interfaces.AuthProtectedController;
import com.otilm.api.model.client.cryptography.token.v2.TokenInstanceRequestDto;
import com.otilm.api.model.common.ErrorMessageDto;
import com.otilm.api.model.common.NameAndUuidDto;
import com.otilm.api.model.common.attribute.common.BaseAttribute;
import com.otilm.api.model.core.cryptography.token.TokenInstanceDetailDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v2/tokens")
@Tag(name = "Token Instance Management v2",
        description = "Core-owned token configuration for cryptography provider v2 connectors")
@ApiResponses({
        @ApiResponse(responseCode = "404", description = "Token instance or connector not found",
                content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))),
        @ApiResponse(responseCode = "502", description = "Connector error",
                content = @Content(schema = @Schema(implementation = ErrorMessageDto.class)))})
public interface TokenInstanceController extends AuthProtectedController {

    @Operation(operationId = "listCryptographyProvidersV2", summary = "List eligible cryptography providers")
    @GetMapping(path = "/providers", produces = MediaType.APPLICATION_JSON_VALUE)
    List<NameAndUuidDto> listCryptographyProviders();

    @Operation(operationId = "listTokenAttributesV2", summary = "List token attributes for a cryptography provider")
    @GetMapping(path = "/providers/{connectorUuid}/attributes", produces = MediaType.APPLICATION_JSON_VALUE)
    List<BaseAttribute> listTokenAttributes(@Parameter(description = "Connector UUID") @PathVariable UUID connectorUuid)
            throws ConnectorException, NotFoundException;

    @Operation(operationId = "createTokenInstanceV2", summary = "Create a Core-owned token instance")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Token instance created"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                            examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    TokenInstanceDetailDto createTokenInstance(@RequestBody @Valid TokenInstanceRequestDto request)
            throws AlreadyExistException, ValidationException, ConnectorException, AttributeException,
            NotFoundException;
}
