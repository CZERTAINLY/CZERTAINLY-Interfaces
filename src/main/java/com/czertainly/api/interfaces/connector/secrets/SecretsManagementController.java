package com.czertainly.api.interfaces.connector.secrets;

import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.interfaces.AuthProtectedConnectorController;
import com.czertainly.api.model.client.attribute.RequestAttribute;
import com.czertainly.api.model.common.attribute.common.BaseAttribute;
import com.czertainly.api.model.connector.secrets.*;
import com.czertainly.api.model.connector.secrets.content.SecretContentResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@RequestMapping("/v1/secretsProvider/secrets")
@Tag(name = "Secrets Management", description = "Secrets Management API")
public interface SecretsManagementController extends AuthProtectedConnectorController {

    @Operation(summary = "List Secrets")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Secrets retrieved")})
    @GetMapping(produces = {"application/json"})
    List<SecretResponseDto> listSecrets();

    @Operation(summary = "Get Secret Attributes")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Secret attributes retrieved")})
    @GetMapping(path = "/{secretType}/attributes", produces = {"application/json"})
    List<BaseAttribute> getSecretAttributes(@Parameter(description = "Secret type") @PathVariable SecretType secretType) throws NotFoundException;

    @Operation(summary = "Get Secret Value")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Secret value retrieved")})
    @PostMapping(path = "/value", consumes = {"application/json"}, produces = {"application/json"})
    SecretContentResponseDto getSecretValue(@Parameter(description = "Secret request") @RequestBody SecretRequestDto request) throws NotFoundException;

    @Operation(summary = "Create Secret")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Secret created")})
    @PostMapping(consumes = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    SecretResponseDto createSecret(@Parameter(description = "Create Secret request") @RequestBody CreateSecretRequestDto request);

    @Operation(summary = "Update Secret")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Secret updated")})
    @PatchMapping(consumes = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    SecretResponseDto updateSecret(@Parameter(description = "Update Secret request") @RequestBody UpdateSecretRequestDto request) throws NotFoundException;

    @Operation(summary = "Delete Secret")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Secret deleted")})
    @DeleteMapping(path = "/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteSecret(@Parameter(description = "Secret request") @RequestBody SecretRequestDto request) throws NotFoundException;

}