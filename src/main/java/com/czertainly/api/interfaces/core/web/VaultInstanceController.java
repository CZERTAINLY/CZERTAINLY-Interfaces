package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.model.client.certificate.SearchRequestDto;
import com.czertainly.api.model.common.attribute.common.BaseAttribute;
import com.czertainly.api.model.core.search.SearchFieldDataByGroupDto;
import com.czertainly.api.model.core.vault.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/v1/vaults")
@Tag(name = "Vault Instance Management", description = "Vault Instance Management API")
public interface VaultInstanceController {

    @Operation(summary = "List Vault Instance Attributes")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Attribute information retrieved")})
    @GetMapping(path = "/attributes", produces = {"application/json"})
    List<BaseAttribute> listVaultInstanceAttributes();

    @Operation(summary = "Details of a Vault instance")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Vault instance details retrieved")})
    @GetMapping(path = "/{uuid}", produces = {"application/json"})
    VaultInstanceDetailDto getVaultInstanceDetails(@Parameter(description = "Vault instance UUID") @PathVariable String uuid);

    @Operation(summary = "List Vault instances")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of Vault instances retrieved")})
    @PostMapping(path = "/list", produces = {"application/json"})
    VaultInstanceListResponseDto listVaultInstances(@RequestBody SearchRequestDto searchRequest);

    @Operation(summary = "Delete a Vault instance")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Vault instance deleted")})
    @DeleteMapping(path = "/{uuid}")
    void deleteVaultInstance(@Parameter(description = "Vault instance UUID") @PathVariable String uuid);

    @Operation(summary = "Create a Vault instance")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Vault instance created")})
    @PostMapping(produces = {"application/json"})
    VaultInstanceDetailDto createVaultInstance(@RequestBody VaultInstanceRequestDto vaultInstanceRequest);

    @Operation(summary = "Update a Vault instance")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Vault instance updated")})
    @PutMapping(path = "/{uuid}", produces = {"application/json"})
    VaultInstanceDetailDto updateVaultInstance(@Parameter(description = "Vault instance UUID") @PathVariable String uuid, @RequestBody VaultInstanceUpdateRequestDto vaultInstanceRequest) throws ConnectorException, NotFoundException;

    @Operation(summary = "List search filters for Vault instances")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of search filters retrieved")})
    @GetMapping(path = "/search", produces = {"application/json"})
    List<SearchFieldDataByGroupDto> getSearchableFieldInformation();

}
