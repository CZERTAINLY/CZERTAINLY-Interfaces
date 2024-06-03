package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.*;
import com.czertainly.api.model.client.approvalprofile.ApprovalProfileResponseDto;
import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import com.czertainly.api.model.client.authority.AuthorityInstanceRequestDto;
import com.czertainly.api.model.client.authority.AuthorityInstanceUpdateRequestDto;
import com.czertainly.api.model.common.*;
import com.czertainly.api.model.common.attribute.v2.BaseAttribute;
import com.czertainly.api.model.core.authority.AuthorityInstanceDto;
import com.czertainly.api.model.core.scheduler.PaginationRequestDto;
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
@RequestMapping("/v1/authorities")
@Tag(name = "Authority Management", description = "Authority Management API")
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
public interface AuthorityInstanceController {

    @Operation(summary = "List of available Authority instances")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of Authority instances")})
    @RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
    List<AuthorityInstanceDto> listAuthorityInstances();

    @Operation(summary = "Details of an Authority instance")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Authority instance details retrieved")})
    @RequestMapping(path = "/{uuid}", method = RequestMethod.GET, produces = {"application/json"})
    AuthorityInstanceDto getAuthorityInstance(@Parameter(description = "Authority instance UUID") @PathVariable String uuid) throws ConnectorException;

    @Operation(summary = "Add Authority instance")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "New Authority instance added", content = @Content(schema = @Schema(implementation = UuidDto.class))),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")})),})
    @RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    ResponseEntity<?> createAuthorityInstance(@RequestBody AuthorityInstanceRequestDto request)
            throws AlreadyExistException, ConnectorException, AttributeException;

    @Operation(summary = "Edit Authority instance")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Authority instance details updated"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @RequestMapping(path = "/{uuid}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {
            "application/json"})
    AuthorityInstanceDto editAuthorityInstance(@Parameter(description = "Authority instance UUID") @PathVariable String uuid, @RequestBody AuthorityInstanceUpdateRequestDto request)
            throws ConnectorException, AttributeException;

    @Operation(summary = "Delete Authority instance")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Authority instance deleted")})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/{uuid}", method = RequestMethod.DELETE, produces = {"application/json"})
    void deleteAuthorityInstance(@Parameter(description = "Authority instance UUID") @PathVariable String uuid) throws ConnectorException;

    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Entity profiles retrieved")})
    @RequestMapping(path = "/{uuid}/endentityprofiles", method = RequestMethod.GET, produces = {"application/json"})
    List<NameAndIdDto> listEntityProfiles(@Parameter(description = "Authority instance UUID") @PathVariable String uuid) throws ConnectorException;

    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Certificate profiles retrieved")})
    @RequestMapping(path = "/{uuid}/endentityprofiles/{endEntityProfileId}/certificateprofiles", method = RequestMethod.GET, produces = {"application/json"})
    List<NameAndIdDto> listCertificateProfiles(@Parameter(description = "Authority instance UUID") @PathVariable String uuid, @PathVariable Integer endEntityProfileId)
            throws ConnectorException;

    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "CAs in Profile retrieved")})
    @RequestMapping(path = "/{uuid}/endentityprofiles/{endEntityProfileId}/cas", method = RequestMethod.GET, produces = {"application/json"})
    List<NameAndIdDto> listCAsInProfile(@Parameter(description = "Authority instance UUID") @PathVariable String uuid, @PathVariable Integer endEntityProfileId)
            throws ConnectorException;

    @Operation(summary = "List RA Profile Attributes")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Attribute information retrieved")})
    @RequestMapping(path = "/{uuid}/attributes/raProfile", method = RequestMethod.GET, produces = {"application/json"})
    List<BaseAttribute> listRAProfileAttributes(@Parameter(description = "Authority instance UUID") @PathVariable String uuid) throws ConnectorException;

    @Operation(summary = "Validate RA Profile Attributes")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Attribute information validated")})
    @RequestMapping(path = "/{uuid}/attributes/raProfile/validate", method = RequestMethod.POST, consumes = {
            "application/json"}, produces = {"application/json"})
    void validateRAProfileAttributes(@Parameter(description = "Authority instance UUID") @PathVariable String uuid, @RequestBody List<RequestAttributeDto> attributes)
            throws ConnectorException;

    @Operation(summary = "Delete multiple Authority instances")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Authority instances deleted"),
            @ApiResponse(responseCode = "422", description = "Unprocessible Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @RequestMapping(method = RequestMethod.DELETE, produces = {"application/json"})
    List<BulkActionMessageDto> bulkDeleteAuthorityInstance(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Authority Instance UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
                                                           @RequestBody List<String> uuids) throws ConnectorException, ValidationException;

    @Operation(summary = "Force delete multiple Authority instances")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Authority instances forced to delete"),
            @ApiResponse(responseCode = "422", description = "Unprocessible Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @RequestMapping(path = "/force", method = RequestMethod.DELETE, produces = {"application/json"})
    List<BulkActionMessageDto> forceDeleteAuthorityInstances(@RequestBody List<String> uuids) throws NotFoundException, ValidationException;

}
