package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.client.certificate.CertificateComplianceCheckDto;
import com.czertainly.api.model.client.client.SimplifiedClientDto;
import com.czertainly.api.model.client.raprofile.ActivateAcmeForRaProfileRequestDto;
import com.czertainly.api.model.client.raprofile.AddRaProfileRequestDto;
import com.czertainly.api.model.client.raprofile.EditRaProfileRequestDto;
import com.czertainly.api.model.client.raprofile.RaProfileAcmeDetailResponseDto;
import com.czertainly.api.model.client.raprofile.RaProfileComplianceCheckDto;
import com.czertainly.api.model.common.attribute.AttributeDefinition;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.UuidDto;
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

import java.util.List;

@RestController
@RequestMapping("/v1/raprofiles")
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
    @RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
    public List<RaProfileDto> listRaProfiles();

    @Operation(summary = "List of available RA Profiles by Status")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "RA Profiles retrieved")})
    @RequestMapping(method = RequestMethod.GET, params = {"isEnabled"}, produces = {"application/json"})
    public List<RaProfileDto> listRaProfiles(@RequestParam Boolean isEnabled);

    @Operation(summary = "Add RA Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "RA Profile added", content = @Content(schema = @Schema(implementation = UuidDto.class))),
            @ApiResponse(responseCode = "422", description = "Unprocessible Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> addRaProfile(@RequestBody AddRaProfileRequestDto request)
            throws AlreadyExistException, ValidationException, NotFoundException, ConnectorException;

    @Operation(summary = "Details of RA Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "RA Profile details retrieved")})
    @RequestMapping(path = "/{uuid}", method = RequestMethod.GET, produces = {"application/json"})
    public RaProfileDto getRaProfile(@Parameter(description = "RA Profile UUID") @PathVariable String uuid) throws NotFoundException;

    @Operation(summary = "Edit RA Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "RA Profile updated"),
            @ApiResponse(responseCode = "422", description = "Unprocessible Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @RequestMapping(path = "/{uuid}", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    public RaProfileDto editRaProfile(@Parameter(description = "RA Profile UUID") @PathVariable String uuid, @RequestBody EditRaProfileRequestDto request)
            throws NotFoundException, ConnectorException;

    @Operation(summary = "Delete RA Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "RA Profile deleted")})
    @RequestMapping(path = "/{uuid}", method = RequestMethod.DELETE, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeRaProfile(@Parameter(description = "RA Profile UUID") @PathVariable String uuid) throws NotFoundException;

    @Operation(summary = "Disable RA Profiles")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "RA Profile disabled")})
    @RequestMapping(path = "/{uuid}/disable", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disableRaProfile(@Parameter(description = "RA Profile UUID") @PathVariable String uuid) throws NotFoundException;

    @Operation(summary = "Enable RA Profiles")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "RA Profile enabled")})
    @RequestMapping(path = "/{uuid}/enable", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enableRaProfile(@Parameter(description = "RA Profile UUID") @PathVariable String uuid) throws NotFoundException;

    @Operation(summary = "List authorized Clients of RA Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of clients of RA Profile")})
    @RequestMapping(path = "/{uuid}/listclients", method = RequestMethod.GET, produces = {"application/json"})
    public List<SimplifiedClientDto> listClients(@Parameter(description = "RA Profile UUID") @PathVariable String uuid) throws NotFoundException;

    @Operation(summary = "Delete multiple RA Profiles")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "RA Profiles deleted"),
            @ApiResponse(responseCode = "422", description = "Unprocessible Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @RequestMapping(method = RequestMethod.DELETE, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void bulkRemoveRaProfile(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "RA Profile UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
                                    @RequestBody List<String> uuids) throws NotFoundException, ValidationException;

    @Operation(summary = "Disable multiple RA Profiles")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "RA Profiles disabled")})
    @RequestMapping(path = "/disable", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void bulkDisableRaProfile(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "RA Profile UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
                                     @RequestBody List<String> uuids) throws NotFoundException;

    @Operation(summary = "Enable multiple RA Profiles")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "RA Profiles enabled")})
    @RequestMapping(path = "/enable", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void bulkEnableRaProfile(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "RA Profile UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
                                    @RequestBody List<String> uuids) throws NotFoundException;

    @Operation(summary = "Get ACME details for RA Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "ACME details retrieved"),
            @ApiResponse(responseCode = "422", description = "Unprocessible Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @RequestMapping(path = "/{uuid}/acme", method = RequestMethod.GET, produces = {"application/json"})
    public RaProfileAcmeDetailResponseDto getAcmeForRaProfile(@Parameter(description = "RA Profile UUID") @PathVariable String uuid)
            throws NotFoundException;

    @Operation(summary = "Activate ACME for RA Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "ACME activated"),
            @ApiResponse(responseCode = "422", description = "Unprocessible Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @RequestMapping(path = "/{uuid}/activateAcme", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    public RaProfileAcmeDetailResponseDto activateAcmeForRaProfile(@Parameter(description = "RA Profile UUID") @PathVariable String uuid, @RequestBody ActivateAcmeForRaProfileRequestDto request)
            throws ConnectorException;

    @Operation(summary = "Deactivate ACME for RA Profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "ACME deactivated"),
            @ApiResponse(responseCode = "422", description = "Unprocessible Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @RequestMapping(path = "/{uuid}/deactivateAcme", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivateAcmeForRaProfile(@Parameter(description = "RA Profile UUID") @PathVariable String uuid)
            throws NotFoundException;

    @Operation(summary = "Get revocation Attributes")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Attributes list obtained") })
    @RequestMapping(path = "{uuid}/revoke/attributes", method = RequestMethod.GET, produces = {"application/json"})
    List<AttributeDefinition> listRevokeCertificateAttributes(
            @Parameter(description = "RA Profile UUID") @PathVariable String uuid) throws NotFoundException, ConnectorException;

    @Operation(summary = "Get issue Certificate Attributes")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Attributes list obtained")})
    @RequestMapping(path = "{uuid}/issue/attributes", method = RequestMethod.GET, produces = {"application/json"})
    List<AttributeDefinition> listIssueCertificateAttributes(
            @Parameter(description = "RA Profile UUID") @PathVariable String uuid) throws NotFoundException, ConnectorException;

    @Operation(summary = "Initiate Certificate Compliance Check")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Compliance check initiated")})
    @RequestMapping(path = "/compliance", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void checkCompliance(@RequestBody RaProfileComplianceCheckDto request) throws NotFoundException;
}
