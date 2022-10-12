package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.client.compliance.SimplifiedComplianceProfileDto;
import com.czertainly.api.model.client.raprofile.ActivateAcmeForRaProfileRequestDto;
import com.czertainly.api.model.client.raprofile.AddRaProfileRequestDto;
import com.czertainly.api.model.client.raprofile.EditRaProfileRequestDto;
import com.czertainly.api.model.client.raprofile.RaProfileAcmeDetailResponseDto;
import com.czertainly.api.model.client.raprofile.SimplifiedRaProfileDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.UuidDto;
import com.czertainly.api.model.common.attribute.AttributeDefinition;
import com.czertainly.api.model.core.compliance.ComplianceProfileDto;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1")
@Tag(name = "RA Profile Management API", description = "RA Profile Management API")
@ApiResponses(
        value = {
                @ApiResponse(
                        responseCode = "400",
                        description = "Bad Request",
                        content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))
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

    @Operation(summary = "Details of RA Profile")
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

    @Operation(summary = "Delete RA Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "RA Profile deleted")})
    @RequestMapping(path = "/raProfiles/{raProfileUuid}", method = RequestMethod.DELETE, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteRaProfile(@Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid) throws NotFoundException;

    @Operation(summary = "Disable RA Profiles")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "RA Profile disabled")})
    @RequestMapping(path = "/authorities/{authorityUuid}/raProfiles/{raProfileUuid}/disable", method = RequestMethod.PATCH, consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void disableRaProfile(@Parameter(description = "Authority Instance UUID") @PathVariable String authorityUuid, @Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid) throws NotFoundException;

    @Operation(summary = "Enable RA Profiles")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "RA Profile enabled")})
    @RequestMapping(path = "/authorities/{authorityUuid}/raProfiles/{raProfileUuid}/enable", method = RequestMethod.PATCH, consumes = {"application/json"}, produces = {"application/json"})
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
    @RequestMapping(path = "/authorities/{authorityUuid}/raProfiles/{raProfileUuid}/acme", method = RequestMethod.GET, produces = {"application/json"})
    RaProfileAcmeDetailResponseDto getAcmeForRaProfile(@Parameter(description = "Authority Instance UUID") @PathVariable String authorityUuid,
                                                       @Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid)
            throws NotFoundException;

    @Operation(summary = "Activate ACME for RA Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "ACME activated"),
            @ApiResponse(responseCode = "422", description = "Unprocessible Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @RequestMapping(path = "/authorities/{authorityUuid}/raProfiles/{raProfileUuid}/acme/activate/{acmeProfileUuid}", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    RaProfileAcmeDetailResponseDto activateAcmeForRaProfile(@Parameter(description = "Authority Instance UUID") @PathVariable String authorityUuid, @Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid, @Parameter(description = "ACME Profile UUID") @PathVariable String acmeProfileUuid, @RequestBody ActivateAcmeForRaProfileRequestDto request)
            throws ConnectorException;

    @Operation(summary = "Deactivate ACME for RA Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "ACME deactivated"),
            @ApiResponse(responseCode = "422", description = "Unprocessible Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @RequestMapping(path = "/authorities/{authorityUuid}/raProfiles/{raProfileUuid}/acme/deactivate", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deactivateAcmeForRaProfile(@Parameter(description = "Authority Instance UUID") @PathVariable String authorityUuid, @Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid)
            throws NotFoundException;

    @Operation(summary = "Get revocation Attributes")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Attributes list obtained")})
    @RequestMapping(path = "/authorities/{authorityUuid}/raProfiles/{raProfileUuid}/attributes/revoke", method = RequestMethod.GET, produces = {"application/json"})
    List<AttributeDefinition> listRevokeCertificateAttributes(@Parameter(description = "Authority Instance UUID") @PathVariable String authorityUuid,
                                                              @Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid) throws ConnectorException;

    @Operation(summary = "Get issue Certificate Attributes")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Attributes list obtained")})
    @RequestMapping(path = "/authorities/{authorityUuid}/raProfiles/{raProfileUuid}/attributes/issue", method = RequestMethod.GET, produces = {"application/json"})
    List<AttributeDefinition> listIssueCertificateAttributes(@Parameter(description = "Authority Instance UUID") @PathVariable String authorityUuid,
                                                             @Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid) throws ConnectorException;

    @Operation(summary = "Initiate Certificate Compliance Check")
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
}
