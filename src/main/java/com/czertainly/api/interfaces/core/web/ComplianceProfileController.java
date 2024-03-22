package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.*;
import com.czertainly.api.model.client.compliance.*;
import com.czertainly.api.model.client.raprofile.SimplifiedRaProfileDto;
import com.czertainly.api.model.common.AuthenticationServiceExceptionDto;
import com.czertainly.api.model.common.BulkActionMessageDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.UuidDto;
import com.czertainly.api.model.core.certificate.CertificateType;
import com.czertainly.api.model.core.compliance.ComplianceProfileDto;
import com.czertainly.api.model.core.compliance.ComplianceProfilesListDto;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/complianceProfiles")
@Tag(name = "Compliance Profile Management", description = "Compliance Profile Management API")
@ApiResponses(
        value = {
                @ApiResponse(
                        responseCode = "400",
                        description = "Bad Request",
                        content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))
                ),
                @ApiResponse(
                        responseCode = "401",
                        description = "Unauthorized",
                        content = @Content(schema = @Schema())
                ),
                @ApiResponse(
                        responseCode = "403",
                        description = "Forbidden",
                        content = @Content(schema = @Schema(implementation = AuthenticationServiceExceptionDto.class))
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "Not Found",
                        content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal Server Error",
                        content = @Content
                ),
                @ApiResponse(
                        responseCode = "502",
                        description = "Connector Error",
                        content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))
                ),
                @ApiResponse(
                        responseCode = "503",
                        description = "Connector Communication Error",
                        content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))
                ),
        })
public interface ComplianceProfileController {

    @Operation(summary = "Get Compliance rules")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Compliance rules retrieved"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")})),})
    @RequestMapping(path = "/rules", method = RequestMethod.GET, produces = {"application/json"})
    List<ComplianceRulesListResponseDto> getComplianceRules(@RequestParam(required = false, name = "complianceProvider") String complianceProviderUuid,
                                                            @RequestParam(required = false) String kind,
                                                            @RequestParam(required = false) List<CertificateType> certificateType)
            throws NotFoundException;

    @Operation(summary = "Get Compliance groups")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Compliance groups retrieved"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")})),})
    @RequestMapping(path = "/groups", method = RequestMethod.GET, produces = {"application/json"})
    List<ComplianceGroupsListResponseDto> getComplianceGroups(@RequestParam(required = false, name = "complianceProvider") String complianceProviderUuid,
                                                              @RequestParam(required = false) String kind)
            throws NotFoundException;

    @Operation(summary = "List of available Compliance Profiles")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Compliance Profiles retrieved")})
    @RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
    List<ComplianceProfilesListDto> listComplianceProfiles();

    @Operation(summary = "Details of a Compliance Profiles")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Compliance Profile details retrieved")})
    @RequestMapping(path = "/{uuid}", method = RequestMethod.GET, produces = {"application/json"})
    ComplianceProfileDto getComplianceProfile(@Parameter(description = "Compliance Profile UUID")
                                              @PathVariable String uuid) throws NotFoundException;

    @Operation(summary = "Add Compliance Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "New Compliance profile added"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")})),})
    @RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    ResponseEntity<UuidDto> createComplianceProfile(@RequestBody ComplianceProfileRequestDto request)
            throws AlreadyExistException, ConnectorException, AttributeException;

    @Operation(summary = "Add rule to a Compliance Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "New rule added to the profile"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")})),})
    @RequestMapping(path = "/{uuid}/rules", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    ComplianceProfileRuleDto addRule(@Parameter(description = "Compliance Profile UUID")
                 @PathVariable String uuid, @RequestBody ComplianceRuleAdditionRequestDto request)
            throws AlreadyExistException, NotFoundException, ValidationException;

    @Operation(summary = "Delete rule from a Compliance Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "New group is added to the profile"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")})),})
    @RequestMapping(path = "/{uuid}/rules", method = RequestMethod.DELETE, consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void removeRule(@Parameter(description = "Compliance Profile UUID")
                    @PathVariable String uuid, @RequestBody ComplianceRuleDeletionRequestDto request)
            throws NotFoundException;

    @Operation(summary = "Add group to a Compliance Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "New group is deleted from the profile"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")})),})
    @RequestMapping(path = "/{uuid}/groups", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void addGroup(@Parameter(description = "Compliance Profile UUID")
                  @PathVariable String uuid, @RequestBody ComplianceGroupRequestDto request)
            throws AlreadyExistException, NotFoundException;

    @Operation(summary = "Delete group from a Compliance Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "New rule is added to the profile"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")})),})
    @RequestMapping(path = "/{uuid}/groups", method = RequestMethod.DELETE, consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void removeGroup(@Parameter(description = "Compliance Profile UUID")
                     @PathVariable String uuid, @RequestBody ComplianceGroupRequestDto request)
            throws NotFoundException;

    @Operation(summary = "Delete Compliance Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Compliance Profile deleted")})
    @RequestMapping(path = "/{uuid}", method = RequestMethod.DELETE, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteComplianceProfile(@Parameter(description = "Compliance Profile UUID") @PathVariable String uuid) throws NotFoundException;


    @Operation(summary = "Get RA Profiles for a Compliance Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "RA Profiles retrieved")})
    @RequestMapping(path = "/{uuid}/raProfiles", method = RequestMethod.GET, produces = {"application/json"})
    List<SimplifiedRaProfileDto> getAssociatedRAProfiles(@Parameter(description = "Compliance Profile UUID")
                                                         @PathVariable String uuid) throws NotFoundException;

    @Operation(summary = "Delete multiple Compliance Profiles")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Compliance Profiles deleted"),
            @ApiResponse(responseCode = "422", description = "Unprocessible Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @RequestMapping(method = RequestMethod.DELETE, produces = {"application/json"})
    List<BulkActionMessageDto> bulkDeleteComplianceProfiles(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Compliance Profile UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
                                                            @RequestBody List<String> uuids) throws NotFoundException, ValidationException;

    @Operation(summary = "Force delete Compliance Profiles")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Compliance Profiles forced to delete"),
            @ApiResponse(responseCode = "422", description = "Unprocessible Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @RequestMapping(path = "/force", method = RequestMethod.DELETE, produces = {"application/json"})
    List<BulkActionMessageDto> forceDeleteComplianceProfiles(@RequestBody List<String> uuids) throws NotFoundException, ValidationException;

    @Operation(summary = "Associate Compliance Profile to RA Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "RA Profile association successful"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")})),})
    @RequestMapping(path = "/{uuid}/raProfiles/associate", method = RequestMethod.PATCH, consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void associateProfiles(@Parameter(description = "Compliance Profile UUID") @PathVariable String uuid,
                           @RequestBody RaProfileAssociationRequestDto raProfiles)
            throws ConnectorException;

    @Operation(summary = "Disassociate Compliance Profile to RA Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "RA Profile disassociation successful"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")})),})
    @RequestMapping(path = "/{uuid}/raProfiles/disassociate", method = RequestMethod.PATCH, consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void disassociateProfiles(@Parameter(description = "Compliance Profile UUID") @PathVariable String uuid,
                              @RequestBody RaProfileAssociationRequestDto raProfiles)
            throws ConnectorException;

    @Operation(summary = "Initiate Certificate Compliance Check")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Compliance check initiated")})
    @RequestMapping(path = "/compliance", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void checkCompliance(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "RA Profile UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
                         @RequestBody List<String> uuids) throws NotFoundException;

}
