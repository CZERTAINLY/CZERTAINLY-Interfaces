package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.*;
import com.czertainly.api.model.client.approvalprofile.ApprovalProfileDto;
import com.czertainly.api.model.client.compliance.SimplifiedComplianceProfileDto;
import com.czertainly.api.model.client.raprofile.*;
import com.czertainly.api.model.common.AuthenticationServiceExceptionDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.UuidDto;
import com.czertainly.api.model.common.attribute.v2.BaseAttribute;
import com.czertainly.api.model.core.certificate.CertificateDetailDto;
import com.czertainly.api.model.core.raprofile.RaProfileDto;
import com.czertainly.api.model.core.raprofile.RaProfileValidationUpdateDto;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1")
@Tag(name = "RA Profile Management", description = "RA Profile Management API")
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

public interface RAProfileManagementController {
    @Operation(summary = "List of available RA Profiles")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "RA Profiles retrieved")})
    @GetMapping(path = "/raProfiles", produces = {"application/json"})
    List<RaProfileDto> listRaProfiles(Optional<Boolean> enabled);

    @Operation(summary = "Create RA Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "RA Profile added", content = @Content(schema = @Schema(implementation = UuidDto.class))),
            @ApiResponse(responseCode = "422", description = "Unprocessible Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @PostMapping(path = "/authorities/{authorityUuid}/raProfiles", consumes = {"application/json"}, produces = {"application/json"})
    ResponseEntity<?> createRaProfile(@Parameter(description = "Authority Instance UUID") @PathVariable String authorityUuid, @RequestBody AddRaProfileRequestDto request)
            throws AlreadyExistException, ValidationException, ConnectorException, AttributeException;

    @Operation(summary = "Details of RA Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "RA Profile details retrieved")})
    @GetMapping(path = "/authorities/{authorityUuid}/raProfiles/{raProfileUuid}", produces = {"application/json"})
    RaProfileDto getRaProfile(@Parameter(description = "Authority Instance UUID") @PathVariable String authorityUuid, @Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid) throws NotFoundException;

    @Operation(summary = "Details of RA Profile", operationId = "getRaProfileWithoutAuthority")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "RA Profile details retrieved")})
    @GetMapping(path = "/raProfiles/{raProfileUuid}", produces = {"application/json"})
    RaProfileDto getRaProfile(@Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid) throws NotFoundException;

    @Operation(summary = "Edit RA Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "RA Profile updated"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @PutMapping(path = "/authorities/{authorityUuid}/raProfiles/{raProfileUuid}", consumes = {"application/json"}, produces = {"application/json"})
    RaProfileDto editRaProfile(@Parameter(description = "Authority Instance UUID") @PathVariable String authorityUuid, @Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid, @RequestBody EditRaProfileRequestDto request)
            throws ConnectorException, AttributeException;

    @Operation(summary = "Update validation configuration of RA profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Configuration of validation of RA Profile updated"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class))))})
    @PatchMapping(path = "/authorities/{authorityUuid}/raProfiles/{raProfileUuid}/validation", consumes = {"application/json"})
    void updateRaProfileValidationConfiguration(@Parameter(description = "Authority Instance UUID") @PathVariable String authorityUuid, @Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid, @RequestBody @Valid RaProfileValidationUpdateDto request) throws NotFoundException;

    @Operation(summary = "Delete RA Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "RA Profile deleted")})
    @DeleteMapping(path = "/authorities/{authorityUuid}/raProfiles/{raProfileUuid}", produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteRaProfile(@Parameter(description = "Authority Instance UUID") @PathVariable String authorityUuid, @Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid) throws NotFoundException;

    @Operation(summary = "Delete RA Profile", operationId = "deleteRaProfileWithoutAuthority")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "RA Profile deleted")})
    @DeleteMapping(path = "/raProfiles/{raProfileUuid}", produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteRaProfile(@Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid) throws NotFoundException;

    @Operation(summary = "Disable RA Profiles")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "RA Profile disabled")})
    @PatchMapping(path = "/authorities/{authorityUuid}/raProfiles/{raProfileUuid}/disable", produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void disableRaProfile(@Parameter(description = "Authority Instance UUID") @PathVariable String authorityUuid, @Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid) throws NotFoundException;

    @Operation(summary = "Enable RA Profiles")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "RA Profile enabled")})
    @PatchMapping(path = "/authorities/{authorityUuid}/raProfiles/{raProfileUuid}/enable", produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void enableRaProfile(@Parameter(description = "Authority Instance UUID") @PathVariable String authorityUuid, @Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid) throws NotFoundException;

    @Operation(summary = "Delete multiple RA Profiles")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "RA Profiles deleted"),
            @ApiResponse(responseCode = "422", description = "Unprocessible Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @DeleteMapping(path = "/raProfiles", produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void bulkDeleteRaProfile(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "RA Profile UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
                             @RequestBody List<String> uuids) throws NotFoundException, ValidationException;

    @Operation(summary = "Disable multiple RA Profiles")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "RA Profiles disabled")})
    @PatchMapping(path = "/raProfiles/disable", consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void bulkDisableRaProfile(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "RA Profile UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
                              @RequestBody List<String> uuids) throws NotFoundException;

    @Operation(summary = "Enable multiple RA Profiles")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "RA Profiles enabled")})
    @PatchMapping(path = "/raProfiles/enable", consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void bulkEnableRaProfile(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "RA Profile UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
                             @RequestBody List<String> uuids) throws NotFoundException;

    @Operation(summary = "Get ACME details for RA Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "ACME details retrieved"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @GetMapping(path = "/authorities/{authorityUuid}/raProfiles/{raProfileUuid}/protocols/acme", produces = {"application/json"})
    RaProfileAcmeDetailResponseDto getAcmeForRaProfile(@Parameter(description = "Authority Instance UUID") @PathVariable String authorityUuid,
                                                       @Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid)
            throws NotFoundException;

    @Operation(summary = "Activate ACME for RA Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "ACME activated"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @PatchMapping(path = "/authorities/{authorityUuid}/raProfiles/{raProfileUuid}/protocols/acme/activate/{acmeProfileUuid}", consumes = {"application/json"}, produces = {"application/json"})
    RaProfileAcmeDetailResponseDto activateAcmeForRaProfile(@Parameter(description = "Authority Instance UUID") @PathVariable String authorityUuid, @Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid, @Parameter(description = "ACME Profile UUID") @PathVariable String acmeProfileUuid, @RequestBody ActivateAcmeForRaProfileRequestDto request)
            throws ConnectorException, AttributeException;


    @Operation(summary = "Deactivate ACME for RA Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "ACME deactivated"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @PatchMapping(path = "/authorities/{authorityUuid}/raProfiles/{raProfileUuid}/protocols/acme/deactivate", produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deactivateAcmeForRaProfile(@Parameter(description = "Authority Instance UUID") @PathVariable String authorityUuid, @Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid)
            throws NotFoundException;

    @Operation(summary = "Get SCEP details for RA Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "SCEP details retrieved"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @GetMapping(path = "/authorities/{authorityUuid}/raProfiles/{raProfileUuid}/protocols/scep", produces = {"application/json"})
    RaProfileScepDetailResponseDto getScepForRaProfile(@Parameter(description = "Authority Instance UUID") @PathVariable String authorityUuid,
                                                       @Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid)
            throws NotFoundException;


    @Operation(summary = "Activate SCEP for RA Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "SCEP activated"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @PatchMapping(path = "/authorities/{authorityUuid}/raProfiles/{raProfileUuid}/protocols/scep/activate/{scepProfileUuid}", consumes = {"application/json"}, produces = {"application/json"})
    RaProfileScepDetailResponseDto activateScepForRaProfile(
            @Parameter(description = "Authority Instance UUID") @PathVariable String authorityUuid,
            @Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid,
            @Parameter(description = "SCEP Profile UUID") @PathVariable String scepProfileUuid,
            @RequestBody ActivateScepForRaProfileRequestDto request)
            throws ConnectorException, AttributeException;

    @Operation(summary = "Deactivate SCEP for RA Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "SCEP deactivated"),
            @ApiResponse(responseCode = "422", description = "Unprocessible Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @PatchMapping(path = "/authorities/{authorityUuid}/raProfiles/{raProfileUuid}/protocols/scep/deactivate", produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deactivateScepForRaProfile(
            @Parameter(description = "Authority Instance UUID") @PathVariable String authorityUuid,
            @Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid
    ) throws NotFoundException;

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------
    // CMP protocol
    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @Operation(
            summary = "Get CMP details for RA Profile"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "CMP details retrieved"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = String.class)),
                            examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}
                    )
            )
    })
    @GetMapping(
            path = "/authorities/{authorityUuid}/raProfiles/{raProfileUuid}/protocols/cmp",
            produces = {"application/json"}
    )
    RaProfileCmpDetailResponseDto getCmpForRaProfile(
            @Parameter(description = "Authority Instance UUID") @PathVariable String authorityUuid,
            @Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid
    ) throws NotFoundException;

    @Operation(
            summary = "Activate CMP for RA Profile"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "CMP activated"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}
                    )
            )
    })
    @PatchMapping(
            path = "/authorities/{authorityUuid}/raProfiles/{raProfileUuid}/protocols/cmp/activate/{cmpProfileUuid}",
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    RaProfileCmpDetailResponseDto activateCmpForRaProfile(
            @Parameter(description = "Authority Instance UUID") @PathVariable String authorityUuid,
            @Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid,
            @Parameter(description = "CMP Profile UUID") @PathVariable String cmpProfileUuid,
            @RequestBody ActivateCmpForRaProfileRequestDto request
    ) throws ConnectorException, AttributeException;

    @Operation(
            summary = "Deactivate CMP for RA Profile"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "CMP deactivated"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}
                    )
            )
    })
    @PatchMapping(
            path = "/authorities/{authorityUuid}/raProfiles/{raProfileUuid}/protocols/cmp/deactivate",
            produces = {"application/json"}
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deactivateCmpForRaProfile(
            @Parameter(description = "Authority Instance UUID") @PathVariable String authorityUuid,
            @Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid
    ) throws NotFoundException;

    @Operation(summary = "Get revocation Attributes", operationId = "listRaProfileRevokeCertificateAttributes")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Revocation attributes list obtained")})
    @GetMapping(path = "/authorities/{authorityUuid}/raProfiles/{raProfileUuid}/attributes/revoke", produces = {"application/json"})
    List<BaseAttribute> listRevokeCertificateAttributes(@Parameter(description = "Authority Instance UUID") @PathVariable String authorityUuid,
                                                        @Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid) throws ConnectorException;

    @Operation(summary = "Get issue Certificate Attributes", operationId = "listRaProfileIssueCertificateAttributes")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Issue certificate attributes list obtained")})
    @GetMapping(path = "/authorities/{authorityUuid}/raProfiles/{raProfileUuid}/attributes/issue", produces = {"application/json"})
    List<BaseAttribute> listIssueCertificateAttributes(@Parameter(description = "Authority Instance UUID") @PathVariable String authorityUuid,
                                                       @Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid) throws ConnectorException;

    @Operation(summary = "Initiate Certificate Compliance Check", operationId = "checkRaProfileCompliance")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Compliance check initiated")})
    @PostMapping(path = "/raProfiles/compliance", produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void checkCompliance(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "RA Profile UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
                         @RequestBody List<String> uuids) throws NotFoundException;

    @Operation(summary = "Get Compliance Profiles for an RA Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Compliance Profiles retrieved")})
    @GetMapping(path = "/authorities/{authorityUuid}/raProfiles/{raProfileUuid}/complianceProfiles", produces = {"application/json"})
    List<SimplifiedComplianceProfileDto> getAssociatedComplianceProfiles(@Parameter(description = "Authority UUID")
                                                                         @PathVariable String authorityUuid,
                                                                         @Parameter(description = "RA Profile UUID")
                                                                         @PathVariable String raProfileUuid) throws NotFoundException;

    @Operation(summary = "List of Approval profiles associated with the RAProfile")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Approval profiles retrieved")})
    @GetMapping(path = "/authorities/{authorityUuid}/raProfiles/{raProfileUuid}/approvalProfiles", produces = {"application/json"})
    List<ApprovalProfileDto> getAssociatedApprovalProfiles(@Parameter(description = "Authority instance UUID") @PathVariable String authorityUuid,
                                                          @Parameter(description = "RA profile UUID") @PathVariable String raProfileUuid) throws NotFoundException;

    @Operation(summary = "Associated RA profile with the Approval profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Approval profile associated with the RA profile")})
    @PatchMapping(path = "/authorities/{authorityUuid}/raProfiles/{raProfileUuid}/approvalProfiles/{approvalProfileUuid}", produces = {"application/json"})
    void associateRAProfileWithApprovalProfile(@Parameter(description = "Authority instance UUID") @PathVariable String authorityUuid,
                                               @Parameter(description = "RA profile UUID") @PathVariable String raProfileUuid,
                                               @Parameter(description = "Approval profile UUID") @PathVariable String approvalProfileUuid) throws NotFoundException;

    @Operation(summary = "Disassociated RA profile with the Approval profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Approval profile disassociated from the RA profile")})
    @DeleteMapping(path = "/authorities/{authorityUuid}/raProfiles/{raProfileUuid}/approvalProfiles/{approvalProfileUuid}", produces = {"application/json"})
    void disassociateRAProfileFromApprovalProfile(@Parameter(description = "Authority instance UUID") @PathVariable String authorityUuid,
                                                  @Parameter(description = "RA profile UUID") @PathVariable String raProfileUuid,
                                                  @Parameter(description = "Approval profile UUID") @PathVariable String approvalProfileUuid) throws NotFoundException;

    @Operation(summary = "Retrieve certificates of authority belonging to RA profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Approval profile associated with the RA profile")})
    @GetMapping(path = "/authorities/{authorityUuid}/raProfiles/{raProfileUuid}/caCertificates", produces = {"application/json"})
    List<CertificateDetailDto> getAuthorityCertificateChain(@Parameter(description = "Authority instance UUID") @PathVariable String authorityUuid,
                                                            @Parameter(description = "RA profile UUID") @PathVariable String raProfileUuid) throws ConnectorException, CertificateException, NoSuchAlgorithmException;

}
