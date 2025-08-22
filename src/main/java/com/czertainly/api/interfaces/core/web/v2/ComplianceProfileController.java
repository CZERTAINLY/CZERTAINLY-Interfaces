package com.czertainly.api.interfaces.core.web.v2;

import com.czertainly.api.interfaces.AuthProtectedController;
import com.czertainly.api.model.client.compliance.v2.ComplianceProfileGroupsPatchRequestDto;
import com.czertainly.api.model.client.compliance.v2.ComplianceProfileRequestDto;
import com.czertainly.api.model.client.compliance.v2.ComplianceProfileRulesPatchRequestDto;
import com.czertainly.api.model.client.compliance.v2.ComplianceProfileUpdateRequestDto;
import com.czertainly.api.model.common.BulkActionMessageDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.core.auth.Resource;
import com.czertainly.api.model.core.compliance.v2.ComplianceGroupListDto;
import com.czertainly.api.model.core.compliance.v2.ComplianceProfileDto;
import com.czertainly.api.model.core.compliance.v2.ComplianceProfileListDto;
import com.czertainly.api.model.core.compliance.v2.ComplianceRuleListDto;
import com.czertainly.api.model.core.other.ResourceObjectDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/v2/complianceProfiles")
@Tag(name = "Compliance Profile Management", description = "Compliance Profile Management API")
@ApiResponses(value = {@ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))), @ApiResponse(responseCode = "502", description = "Connector Error", content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))), @ApiResponse(responseCode = "503", description = "Connector Communication Error", content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))),})
public interface ComplianceProfileController extends AuthProtectedController {

    @Operation(summary = "List of available Compliance Profiles")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Compliance Profiles retrieved")})
    @GetMapping(produces = {"application/json"})
    List<ComplianceProfileListDto> listComplianceProfiles();

    @Operation(summary = "Details of a Compliance Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Compliance Profile details retrieved")})
    @GetMapping(path = "/{uuid}", produces = {"application/json"})
    ComplianceProfileDto getComplianceProfile(@Parameter(description = "Compliance Profile UUID") @PathVariable UUID uuid);

    @Operation(summary = "Add Compliance Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "New Compliance profile added"), @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)), examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")})),})
    @PostMapping(consumes = {"application/json"}, produces = {"application/json"})
    ComplianceProfileDto createComplianceProfile(@RequestBody ComplianceProfileRequestDto request);

    @Operation(summary = "Update Compliance Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Compliance profile updated"), @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)), examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")})),})
    @PutMapping(path = "/{uuid}", consumes = {"application/json"}, produces = {"application/json"})
    ComplianceProfileDto updateComplianceProfile(@Parameter(description = "Compliance Profile UUID") @PathVariable UUID uuid, @RequestBody ComplianceProfileUpdateRequestDto request);

    @Operation(summary = "Delete Compliance Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Compliance Profile deleted")})
    @DeleteMapping(path = "/{uuid}", produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteComplianceProfile(@Parameter(description = "Compliance Profile UUID") @PathVariable UUID uuid);

    @Operation(summary = "Delete multiple Compliance Profiles")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Compliance Profiles deleted"), @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)), examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @DeleteMapping(produces = {"application/json"})
    List<BulkActionMessageDto> bulkDeleteComplianceProfiles(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Compliance Profile UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)), examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")})) @RequestBody List<UUID> uuids);

    @Operation(summary = "Force delete Compliance Profiles")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Compliance Profiles forced to delete"), @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)), examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @DeleteMapping(path = "/force", produces = {"application/json"})
    List<BulkActionMessageDto> forceDeleteComplianceProfiles(@RequestBody List<UUID> uuids);


    @Operation(summary = "Get Compliance rules", description = "Lists compliance rules. If provider UUID is sent (also kind is required) then provider rules are listed, otherwise lists internal rules")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Compliance rules retrieved"), @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)), examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")})),})
    @GetMapping(path = "/rules", produces = {"application/json"})
    List<ComplianceRuleListDto> getComplianceRules(@RequestParam Resource resource, @RequestParam(required = false) UUID connectorUuid, @RequestParam(required = false) String kind, @RequestParam(required = false) String type, @RequestParam(required = false) String format);

    @Operation(summary = "Get Compliance groups")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Compliance groups retrieved"), @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)), examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")})),})
    @GetMapping(path = "/groups", produces = {"application/json"})
    List<ComplianceGroupListDto> getComplianceGroups(@RequestParam(required = false) Resource resource, @RequestParam UUID connectorUuid, @RequestParam String kind);

    @Operation(summary = "Add/remove compliance rule to/from Compliance Profile", description = "If provider UUID is sent (also kind is required) then provider rules is handled, otherwise handling internal rule")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Rule added/removed to/from the profile"), @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)), examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")})),})
    @PatchMapping(path = "/{uuid}/rules", consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void patchComplianceProfileRule(@Parameter(description = "Compliance Profile UUID") @PathVariable UUID uuid, @RequestBody ComplianceProfileRulesPatchRequestDto request);

    @Operation(summary = "Add/remove group to/from Compliance Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Group is added/removed to/from the profile"), @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)), examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")})),})
    @PatchMapping(path = "/{uuid}/groups", consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void patchComplianceProfileGroup(@Parameter(description = "Compliance Profile UUID") @PathVariable UUID uuid, @RequestBody ComplianceProfileGroupsPatchRequestDto request);

    @Operation(summary = "Get associations of Compliance Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Associated resource objects retrieved")})
    @GetMapping(path = "/{uuid}/associations", produces = {"application/json"})
    List<ResourceObjectDto> getAssociations(@Parameter(description = "Compliance Profile UUID") @PathVariable UUID uuid);

    @Operation(summary = "Associate Compliance Profile to specified resource object")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Resource object association successful"), @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)), examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")})),})
    @PatchMapping(path = "/{uuid}/associations/{resource}/{associationObjectUuid}", consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void associateComplianceProfile(@Parameter(description = "Compliance Profile UUID", required = true) @PathVariable UUID uuid, @Parameter(description = "Resource", required = true, example = Resource.Codes.RA_PROFILE) @PathVariable Resource resource, @Parameter(description = "Association object UUID", required = true) @PathVariable UUID associationObjectUuid);

    @Operation(summary = "Disassociate Compliance Profile from specified resource object")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Resource object disassociation successful"), @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)), examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")})),})
    @DeleteMapping(path = "/{uuid}/associations/{resource}/{associationObjectUuid}", consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void disassociateComplianceProfile(@Parameter(description = "Compliance Profile UUID", required = true) @PathVariable UUID uuid, @Parameter(description = "Resource", required = true, example = Resource.Codes.RA_PROFILE) @PathVariable Resource resource, @Parameter(description = "Association object UUID", required = true) @PathVariable UUID associationObjectUuid);

    @Operation(summary = "Get associated Compliance Profiles for resource object")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Associated Compliance Profiles retrieved")})
    @GetMapping(path = "/associations/{resource}/{associationObjectUuid}", produces = {"application/json"})
    List<ComplianceProfileListDto> getAssociatedComplianceProfiles(@Parameter(description = "Resource", required = true, example = Resource.Codes.RA_PROFILE) @PathVariable Resource resource, @Parameter(description = "Association object UUID", required = true) @PathVariable UUID associationObjectUuid);

    @Operation(summary = "Initiate Certificate Compliance Check for requested compliance profiles")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Compliance check initiated")})
    @PostMapping(path = "/compliance", produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void checkCompliance(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "RA Profile UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)), examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")})) @RequestBody List<UUID> uuids);

    @Operation(summary = "Initiate Certificate Compliance Check for requested resource object")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Compliance check initiated")})
    @PostMapping(path = "/compliance/{resource}/{objectUuid}", produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void checkResourceObjectCompliance(@Parameter(description = "Resource", required = true, example = Resource.Codes.RA_PROFILE) @PathVariable Resource resource, @Parameter(description = "Object UUID", required = true) @PathVariable UUID objectUuid);

}
