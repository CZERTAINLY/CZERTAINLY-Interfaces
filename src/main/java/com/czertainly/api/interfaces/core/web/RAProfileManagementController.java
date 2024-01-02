package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.client.approvalprofile.ApprovalProfileDto;
import com.czertainly.api.model.client.compliance.SimplifiedComplianceProfileDto;
import com.czertainly.api.model.client.raprofile.*;
import com.czertainly.api.model.common.AuthenticationServiceExceptionDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.UuidDto;
import com.czertainly.api.model.common.attribute.v2.BaseAttribute;
import com.czertainly.api.model.core.certificate.CertificateDetailDto;
import com.czertainly.api.model.core.raprofile.RaProfileDto;
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
    @RequestMapping(path = "/raProfiles", method = RequestMethod.GET, produces = {"application/json"})
    List<RaProfileDto> listRaProfiles(Optional<Boolean> enabled);

    @Operation(summary = "Create RA Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "RA Profile added", content = @Content(schema = @Schema(implementation = UuidDto.class))),
            @ApiResponse(responseCode = "422", description = "Unprocessible Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @RequestMapping(path = "/authorities/{authorityUuid}/raProfiles", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    ResponseEntity<?> createRaProfile(@Parameter(description = "Authority Instance UUID") @PathVariable String authorityUuid, @RequestBody AddRaProfileRequestDto request)
            throws AlreadyExistException, ValidationException, ConnectorException;

    @Operation(summary = "Details of RA Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "RA Profile details retrieved")})
    @RequestMapping(path = "/authorities/{authorityUuid}/raProfiles/{raProfileUuid}", method = RequestMethod.GET, produces = {"application/json"})
    RaProfileDto getRaProfile(@Parameter(description = "Authority Instance UUID") @PathVariable String authorityUuid, @Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid) throws NotFoundException;

    @Operation(summary = "Details of RA Profile", operationId = "getRaProfileWithoutAuthority")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "RA Profile details retrieved")})
    @RequestMapping(path = "/raProfiles/{raProfileUuid}", method = RequestMethod.GET, produces = {"application/json"})
    RaProfileDto getRaProfile(@Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid) throws NotFoundException;

    @Operation(summary = "Edit RA Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "RA Profile updated"),
            @ApiResponse(responseCode = "422", description = "Unprocessible Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @RequestMapping(path = "/authorities/{authorityUuid}/raProfiles/{raProfileUuid}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
    RaProfileDto editRaProfile(@Parameter(description = "Authority Instance UUID") @PathVariable String authorityUuid, @Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid, @RequestBody EditRaProfileRequestDto request)
            throws ConnectorException;

    @Operation(summary = "Delete RA Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "RA Profile deleted")})
    @RequestMapping(path = "/authorities/{authorityUuid}/raProfiles/{raProfileUuid}", method = RequestMethod.DELETE, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteRaProfile(@Parameter(description = "Authority Instance UUID") @PathVariable String authorityUuid, @Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid) throws NotFoundException;

    @Operation(summary = "Delete RA Profile", operationId = "deleteRaProfileWithoutAuthority")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "RA Profile deleted")})
    @RequestMapping(path = "/raProfiles/{raProfileUuid}", method = RequestMethod.DELETE, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteRaProfile(@Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid) throws NotFoundException;

    @Operation(summary = "Disable RA Profiles")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "RA Profile disabled")})
    @RequestMapping(path = "/authorities/{authorityUuid}/raProfiles/{raProfileUuid}/disable", method = RequestMethod.PATCH, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void disableRaProfile(@Parameter(description = "Authority Instance UUID") @PathVariable String authorityUuid, @Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid) throws NotFoundException;

    @Operation(summary = "Enable RA Profiles")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "RA Profile enabled")})
    @RequestMapping(path = "/authorities/{authorityUuid}/raProfiles/{raProfileUuid}/enable", method = RequestMethod.PATCH, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void enableRaProfile(@Parameter(description = "Authority Instance UUID") @PathVariable String authorityUuid, @Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid) throws NotFoundException;

    @Operation(summary = "Delete multiple RA Profiles")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "RA Profiles deleted"),
            @ApiResponse(responseCode = "422", description = "Unprocessible Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @RequestMapping(path = "/raProfiles", method = RequestMethod.DELETE, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void bulkDeleteRaProfile(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "RA Profile UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
                             @RequestBody List<String> uuids) throws NotFoundException, ValidationException;

    @Operation(summary = "Disable multiple RA Profiles")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "RA Profiles disabled")})
    @RequestMapping(path = "/raProfiles/disable", method = RequestMethod.PATCH, consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void bulkDisableRaProfile(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "RA Profile UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
                              @RequestBody List<String> uuids) throws NotFoundException;

    @Operation(summary = "Enable multiple RA Profiles")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "RA Profiles enabled")})
    @RequestMapping(path = "/raProfiles/enable", method = RequestMethod.PATCH, consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void bulkEnableRaProfile(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "RA Profile UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
                             @RequestBody List<String> uuids) throws NotFoundException;

    @Operation(summary = "Get ACME details for RA Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "ACME details retrieved"),
            @ApiResponse(responseCode = "422", description = "Unprocessible Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @RequestMapping(path = "/authorities/{authorityUuid}/raProfiles/{raProfileUuid}/protocols/acme", method = RequestMethod.GET, produces = {"application/json"})
    RaProfileAcmeDetailResponseDto getAcmeForRaProfile(@Parameter(description = "Authority Instance UUID") @PathVariable String authorityUuid,
                                                       @Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid)
            throws NotFoundException;

    @Operation(summary = "Activate ACME for RA Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "ACME activated"),
            @ApiResponse(responseCode = "422", description = "Unprocessible Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @RequestMapping(path = "/authorities/{authorityUuid}/raProfiles/{raProfileUuid}/protocols/acme/activate/{acmeProfileUuid}", method = RequestMethod.PATCH, consumes = {"application/json"}, produces = {"application/json"})
    RaProfileAcmeDetailResponseDto activateAcmeForRaProfile(@Parameter(description = "Authority Instance UUID") @PathVariable String authorityUuid, @Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid, @Parameter(description = "ACME Profile UUID") @PathVariable String acmeProfileUuid, @RequestBody ActivateAcmeForRaProfileRequestDto request)
            throws ConnectorException;


    @Operation(summary = "Deactivate ACME for RA Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "ACME deactivated"),
            @ApiResponse(responseCode = "422", description = "Unprocessible Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @RequestMapping(path = "/authorities/{authorityUuid}/raProfiles/{raProfileUuid}/protocols/acme/deactivate", method = RequestMethod.PATCH, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deactivateAcmeForRaProfile(@Parameter(description = "Authority Instance UUID") @PathVariable String authorityUuid, @Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid)
            throws NotFoundException;

    @Operation(summary = "Get SCEP details for RA Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "SCEP details retrieved"),
            @ApiResponse(responseCode = "422", description = "Unprocessible Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @RequestMapping(path = "/authorities/{authorityUuid}/raProfiles/{raProfileUuid}/protocols/scep", method = RequestMethod.GET, produces = {"application/json"})
    RaProfileScepDetailResponseDto getScepForRaProfile(@Parameter(description = "Authority Instance UUID") @PathVariable String authorityUuid,
                                                       @Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid)
            throws NotFoundException;


    @Operation(summary = "Activate SCEP for RA Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "SCEP activated"),
            @ApiResponse(responseCode = "422", description = "Unprocessible Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @RequestMapping(path = "/authorities/{authorityUuid}/raProfiles/{raProfileUuid}/protocols/scep/activate/{scepProfileUuid}", method = RequestMethod.PATCH, consumes = {"application/json"}, produces = {"application/json"})
    RaProfileScepDetailResponseDto activateScepForRaProfile(
            @Parameter(description = "Authority Instance UUID") @PathVariable String authorityUuid,
            @Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid,
            @Parameter(description = "SCEP Profile UUID") @PathVariable String scepProfileUuid,
            @RequestBody ActivateScepForRaProfileRequestDto request)
            throws ConnectorException;

    @Operation(summary = "Deactivate SCEP for RA Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "SCEP deactivated"),
            @ApiResponse(responseCode = "422", description = "Unprocessible Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @RequestMapping(path = "/authorities/{authorityUuid}/raProfiles/{raProfileUuid}/protocols/scep/deactivate", method = RequestMethod.PATCH, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deactivateScepForRaProfile(
            @Parameter(description = "Authority Instance UUID") @PathVariable String authorityUuid,
            @Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid
    ) throws NotFoundException;

    @Operation(summary = "Get revocation Attributes", operationId = "listRaProfileRevokeCertificateAttributes")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Revocation attributes list obtained")})
    @RequestMapping(path = "/authorities/{authorityUuid}/raProfiles/{raProfileUuid}/attributes/revoke", method = RequestMethod.GET, produces = {"application/json"})
    List<BaseAttribute> listRevokeCertificateAttributes(@Parameter(description = "Authority Instance UUID") @PathVariable String authorityUuid,
                                                        @Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid) throws ConnectorException;

    @Operation(summary = "Get issue Certificate Attributes", operationId = "listRaProfileIssueCertificateAttributes")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Issue certificate attributes list obtained")})
    @RequestMapping(path = "/authorities/{authorityUuid}/raProfiles/{raProfileUuid}/attributes/issue", method = RequestMethod.GET, produces = {"application/json"})
    List<BaseAttribute> listIssueCertificateAttributes(@Parameter(description = "Authority Instance UUID") @PathVariable String authorityUuid,
                                                       @Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid) throws ConnectorException;

    @Operation(summary = "Initiate Certificate Compliance Check", operationId = "checkRaProfileCompliance")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Compliance check initiated")})
    @RequestMapping(path = "/raProfiles/compliance", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void checkCompliance(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "RA Profile UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
                         @RequestBody List<String> uuids) throws NotFoundException;

    @Operation(summary = "Get Compliance Profiles for an RA Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Compliance Profiles retrieved")})
    @RequestMapping(path = "/authorities/{authorityUuid}/raProfiles/{raProfileUuid}/complianceProfiles", method = RequestMethod.GET, produces = {"application/json"})
    List<SimplifiedComplianceProfileDto> getAssociatedComplianceProfiles(@Parameter(description = "Authority UUID")
                                                                         @PathVariable String authorityUuid,
                                                                         @Parameter(description = "RA Profile UUID")
                                                                         @PathVariable String raProfileUuid) throws NotFoundException;

    @Operation(summary = "List of Approval profiles associated with the RAProfile")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Approval profiles retrieved")})
    @RequestMapping(path = "/authorities/{authorityUuid}/raProfiles/{raProfileUuid}/approvalProfiles", method = RequestMethod.GET, produces = {"application/json"})
    List<ApprovalProfileDto> getAssociatedApprovalProfiles(@Parameter(description = "Authority instance UUID") @PathVariable String authorityUuid,
                                                          @Parameter(description = "RA profile UUID") @PathVariable String raProfileUuid) throws NotFoundException;

    @Operation(summary = "Associated RA profile with the Approval profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Approval profile associated with the RA profile")})
    @RequestMapping(path = "/authorities/{authorityUuid}/raProfiles/{raProfileUuid}/approvalProfiles/{approvalProfileUuid}", method = RequestMethod.PATCH, produces = {"application/json"})
    void associateRAProfileWithApprovalProfile(@Parameter(description = "Authority instance UUID") @PathVariable String authorityUuid,
                                               @Parameter(description = "RA profile UUID") @PathVariable String raProfileUuid,
                                               @Parameter(description = "Approval profile UUID") @PathVariable String approvalProfileUuid) throws NotFoundException;

    @Operation(summary = "Disassociated RA profile with the Approval profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Approval profile disassociated from the RA profile")})
    @RequestMapping(path = "/authorities/{authorityUuid}/raProfiles/{raProfileUuid}/approvalProfiles/{approvalProfileUuid}", method = RequestMethod.DELETE, produces = {"application/json"})
    void disassociateRAProfileFromApprovalProfile(@Parameter(description = "Authority instance UUID") @PathVariable String authorityUuid,
                                                  @Parameter(description = "RA profile UUID") @PathVariable String raProfileUuid,
                                                  @Parameter(description = "Approval profile UUID") @PathVariable String approvalProfileUuid) throws NotFoundException;

    @Operation(summary = "Retrieve certificates of authority belonging to RA profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Approval profile associated with the RA profile")})
    @RequestMapping(path = "/authorities/{authorityUuid}/raProfiles/{raProfileUuid}/caCertificates", method = RequestMethod.GET, produces = {"application/json"})
    List<CertificateDetailDto> getAuthorityCertificateChain(@Parameter(description = "Authority instance UUID") @PathVariable String authorityUuid,
                                                            @Parameter(description = "RA profile UUID") @PathVariable String raProfileUuid) throws ConnectorException, CertificateException, NoSuchAlgorithmException;

}
