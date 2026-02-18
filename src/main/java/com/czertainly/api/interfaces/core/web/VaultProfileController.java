package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.AttributeException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.interfaces.AuthProtectedController;
import com.czertainly.api.model.client.certificate.SearchRequestDto;
import com.czertainly.api.model.common.attribute.common.BaseAttribute;
import com.czertainly.api.model.core.secret.SecretType;
import com.czertainly.api.model.core.vaultprofile.VaultProfileDetailDto;
import com.czertainly.api.model.core.vaultprofile.VaultProfileListResponseDto;
import com.czertainly.api.model.core.vaultprofile.VaultProfileRequestDto;
import com.czertainly.api.model.core.vaultprofile.VaultProfileUpdateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/v1")
@Tag(name = "Vault Profile Management", description = "APIs for managing Vault Profiles")
public interface VaultProfileController extends AuthProtectedController {

    @Operation(summary = "List Vault Profiles")
    @ApiResponse(responseCode = "200", description = "List of Vault Profiles retrieved")
    @PostMapping(path = "/vaultProfiles/list", consumes = {"application/json"}, produces = {"application/json"})
    VaultProfileListResponseDto listVaultProfiles(@RequestBody SearchRequestDto searchRequest);

    @Operation(summary = "Get details of a Vault Profile")
    @ApiResponse(responseCode = "200", description = "Vault Profile details retrieved")
    @GetMapping(path = "/vaults/{vaultUuid}/vaultProfiles/{vaultProfileUuid}", consumes = {"application/json"}, produces = {"application/json"})
    VaultProfileDetailDto getVaultProfileDetails(@Parameter(description = "UUID of Vault Instance") @PathVariable UUID vaultUuid, @Parameter(description = "UUID of vault profile") @PathVariable UUID vaultProfileUuid) throws NotFoundException;

    @Operation(summary = "Update a Vault Profile")
    @ApiResponse(responseCode = "200", description = "Vault Profile updated")
    @PostMapping(path = "/vaults/{vaultUuid}/vaultProfiles/{vaultProfileUuid}", consumes = {"application/json"}, produces = {"application/json"})
    VaultProfileDetailDto updateVaultProfile(@Parameter(description = "UUID of Vault Instance") @PathVariable UUID vaultUuid, @Parameter(description = "UUID of vault profile") @PathVariable UUID vaultProfileUuid, @RequestBody VaultProfileUpdateRequestDto vaultProfileUpdateRequest) throws NotFoundException;

    @Operation(summary = "Delete a Vault Profile")
    @ApiResponse(responseCode = "204", description = "Vault Profile deleted")
    @DeleteMapping(path = "/vaults/{vaultUuid}/vaultProfiles/{vaultProfileUuid}")
    void deleteVaultProfile(@Parameter(description = "UUID of Vault Instance") @PathVariable UUID vaultUuid, @Parameter(description = "UUID of vault profile") @PathVariable UUID vaultProfileUuid) throws NotFoundException;

    @Operation(summary = "Create a Vault Profile")
    @ApiResponse(responseCode = "201", description = "Vault Profile created")
    @PostMapping(path = "/vaults/{vaultUuid}/vaultProfiles", consumes = {"application/json"}, produces = {"application/json"})
    VaultProfileDetailDto createVaultProfile(@Parameter(description = "UUID of Vault Instance") @PathVariable UUID vaultUuid, @RequestBody VaultProfileRequestDto vaultProfileDetail) throws NotFoundException, AttributeException;

    @Operation(summary = "Enable a Vault Profile")
    @ApiResponse(responseCode = "204", description = "Vault Profile enabled")
    @PatchMapping(path = "/vaults/{vaultUuid}/vaultProfiles/{vaultProfileUuid}/enable")
    void enableVaultProfile(@Parameter(description = "UUID of Vault Instance") @PathVariable UUID vaultUuid, @Parameter(description = "UUID of vault profile") @PathVariable UUID vaultProfileUuid) throws NotFoundException;

    @Operation(summary = "Disable a Vault Profile")
    @ApiResponse(responseCode = "204", description = "Vault Profile disabled")
    @PatchMapping(path = "/vaults/{vaultUuid}/vaultProfiles/{vaultProfileUuid}/disable")
    void disableVaultProfile(@Parameter(description = "UUID of Vault Instance") @PathVariable UUID vaultUuid, @Parameter(description = "UUID of vault profile") @PathVariable UUID vaultProfileUuid) throws NotFoundException;

    @Operation(summary = "List attributes for creating a secret in a Vault Profile")
    @ApiResponse(responseCode = "200", description = "List of attributes for creating a secret in a Vault Profile retrieved")
    @GetMapping(path = "/vaults/{vaultUuid}/vaultProfiles/{vaultProfileUuid}/secrets/{secretType}/attributes", produces = {"application/json"})
    List<BaseAttribute> getAttributesForCreatingSecret(@Parameter(description = "UUID of Vault Instance") @PathVariable UUID vaultUuid, @Parameter(description = "UUID of vault profile") @PathVariable UUID vaultProfileUuid, @Parameter(description = "Type of the secret") @PathVariable SecretType secretType);

}
