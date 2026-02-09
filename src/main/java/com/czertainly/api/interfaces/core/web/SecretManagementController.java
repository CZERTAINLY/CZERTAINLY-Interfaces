package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.interfaces.AuthProtectedController;
import com.czertainly.api.model.client.certificate.SearchRequestDto;
import com.czertainly.api.model.core.search.SearchFieldDataByGroupDto;
import com.czertainly.api.model.core.secret.*;
import com.czertainly.api.model.core.secret.content.SecretContentDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/v1")
@Tag(name = "Secret Management", description = "APIs for managing secrets, including creation, retrieval, updating, and deletion of secrets.")
public interface SecretManagementController extends AuthProtectedController {

    @Operation(summary = "List search filters for secrets")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of search filters retrieved")})
    @GetMapping(path = "/secrets/search", produces = {MediaType.APPLICATION_JSON_VALUE})
    List<SearchFieldDataByGroupDto> getSearchableFieldInformation();

    @Operation(summary = "List secrets")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of secrets retrieved")})
    @PostMapping(path = "/secrets", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    List<SecretListResponseDto> listSecrets(@RequestBody SearchRequestDto searchRequest);

    @Operation(summary = "Get secret details")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Secret details retrieved")})
    @GetMapping(path = "/secrets/{uuid}", produces = {MediaType.APPLICATION_JSON_VALUE})
    SecretDetailDto getSecretDetails(@Parameter(description = "UUID of the secret") @PathVariable UUID uuid);

    @Operation(summary = "Get secret versions")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Secret versions retrieved")})
    @GetMapping(path = "/secrets/{uuid}/versions", produces = {MediaType.APPLICATION_JSON_VALUE})
    List<SecretVersionDto> getSecretVersions(@Parameter(description = "UUID of the secret") @PathVariable UUID uuid);

    @Operation(summary = "Get secret content")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Secret content retrieved")})
    @GetMapping(path = "/secrets/{uuid}/content", produces = {MediaType.APPLICATION_JSON_VALUE})
    SecretContentDto getSecretContent(@Parameter(description = "UUID of the secret") @PathVariable UUID uuid);

    @Operation(summary = "Create a new secret")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Secret created successfully")})
    @PostMapping(path = "/secrets", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    SecretDetailDto createSecret(@RequestBody SecretRequestDto secretRequest);

    @Operation(summary = "Update an existing secret")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Secret updated successfully")})
    @PutMapping(path = "/secrets/{uuid}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    SecretDetailDto updateSecret(@Parameter(description = "UUID of the secret") @PathVariable UUID uuid, @RequestBody SecretUpdateRequestDto secretRequest);

    @Operation(summary = "Delete a secret")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Secret deleted successfully")})
    @DeleteMapping(path = "/secrets/{uuid}")
    void deleteSecret(@Parameter(description = "UUID of the secret") @PathVariable UUID uuid);

    @Operation(summary = "Enable a secret")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Secret enabled successfully")})
    @PostMapping(path = "/secrets/{uuid}/enable")
    void enableSecret(@Parameter(description = "UUID of the secret") @PathVariable UUID uuid);

    @Operation(summary = "Disable a secret")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Secret disabled successfully")})
    @PostMapping(path = "/secrets/{uuid}/disable")
    void disableSecret(@Parameter(description = "UUID of the secret") @PathVariable UUID uuid);

    @Operation(summary = "Add vault profile to secret")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Vault profile added to secret successfully")})
    @PatchMapping(path = "/secrets/{uuid}/syncVaultProfiles/{vaultProfileUuid}")
    void addVaultProfileToSecret(@Parameter(description = "UUID of the secret") @PathVariable UUID uuid, @Parameter(description = "UUID of the vault profile") @PathVariable UUID vaultProfileUuid);

    @Operation(summary = "Remove vault profile from secret")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Vault profile removed from secret successfully")})
    @DeleteMapping(path = "/secrets/{uuid}/syncVaultProfiles/{vaultProfileUuid}")
    void removeVaultProfileFromSecret(@Parameter(description = "UUID of the secret") @PathVariable UUID uuid, @Parameter(description = "UUID of the vault profile") @PathVariable UUID vaultProfileUuid);

}
