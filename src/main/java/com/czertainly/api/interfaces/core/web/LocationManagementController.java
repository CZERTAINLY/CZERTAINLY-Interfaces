package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.*;
import com.czertainly.api.model.client.certificate.LocationsResponseDto;
import com.czertainly.api.model.client.certificate.SearchRequestDto;
import com.czertainly.api.model.client.location.AddLocationRequestDto;
import com.czertainly.api.model.client.location.EditLocationRequestDto;
import com.czertainly.api.model.client.location.IssueToLocationRequestDto;
import com.czertainly.api.model.client.location.PushToLocationRequestDto;
import com.czertainly.api.model.common.AuthenticationServiceExceptionDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.UuidDto;
import com.czertainly.api.model.common.attribute.v2.BaseAttribute;
import com.czertainly.api.model.core.location.LocationDto;
import com.czertainly.api.model.core.search.SearchFieldDataByGroupDto;
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
import java.util.Optional;

@RestController
@RequestMapping("/v1")
@Tag(name = "Location Management", description = "Location Management API")
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

public interface LocationManagementController {

    @Operation(
            summary = "List Locations"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Locations retrieved"
                    )
            })
    @RequestMapping(
            path = "/locations",
            method = RequestMethod.POST,
            produces = {"application/json"}
    )
    LocationsResponseDto listLocations(@RequestBody SearchRequestDto request);

    @Operation(summary = "Get Locations searchable fields information")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Locations searchable field information retrieved")})
    @RequestMapping(path = "/locations/search", method = RequestMethod.GET, produces = {"application/json"})
    List<SearchFieldDataByGroupDto> getSearchableFieldInformation();

    @Operation(
            summary = "Add Location"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Location added",
                            content = @Content(schema = @Schema(implementation = UuidDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Unprocessable Entity",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")})
                    )
            })
    @RequestMapping(
            path = "/entities/{entityUuid}/locations",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    ResponseEntity<?> addLocation(
            @Parameter(description = "Entity UUID") @PathVariable String entityUuid,
            @RequestBody AddLocationRequestDto request
    ) throws ConnectorException, AlreadyExistException, LocationException, AttributeException;

    @Operation(
            summary = "Get Location Details"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Location detail retrieved"
                    )
            })
    @RequestMapping(
            path = "/entities/{entityUuid}/locations/{locationUuid}",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    LocationDto getLocation(
            @Parameter(description = "Entity UUID") @PathVariable String entityUuid,
            @Parameter(description = "Location UUID") @PathVariable String locationUuid
    ) throws NotFoundException;

    @Operation(
            summary = "Edit Location"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Location updated"
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Unprocessible Entity",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")})
                    )
            })
    @RequestMapping(
            path = "/entities/{entityUuid}/locations/{locationUuid}",
            method = RequestMethod.PUT,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    LocationDto editLocation(
            @Parameter(description = "Entity UUID") @PathVariable String entityUuid,
            @Parameter(description = "Location UUID") @PathVariable String locationUuid,
            @RequestBody EditLocationRequestDto request
    ) throws ConnectorException, LocationException, AttributeException;

    @Operation(
            summary = "Delete Location"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Location deleted"
                    )
            })
    @RequestMapping(
            path = "/entities/{entityUuid}/locations/{locationUuid}",
            method = RequestMethod.DELETE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteLocation(
            @Parameter(description = "Entity UUID") @PathVariable String entityUuid,
            @Parameter(description = "Location UUID") @PathVariable String locationUuid
    ) throws NotFoundException;

    @Operation(
            summary = "Disable Location"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Location disabled"
                    )
            })
    @RequestMapping(
            path = "/entities/{entityUuid}/locations/{locationUuid}/disable",
            method = RequestMethod.PATCH
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void disableLocation(
            @Parameter(description = "Entity UUID") @PathVariable String entityUuid,
            @Parameter(description = "Location UUID") @PathVariable String locationUuid
    ) throws NotFoundException;

    @Operation(
            summary = "Enable Location"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Location enabled"
                    )
            })
    @RequestMapping(
            path = "/entities/{entityUuid}/locations/{locationUuid}/enable",
            method = RequestMethod.PATCH
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void enableLocation(
            @Parameter(description = "Entity UUID") @PathVariable String entityUuid,
            @Parameter(description = "Location UUID") @PathVariable String locationUuid
    ) throws NotFoundException;

    @Operation(
            summary = "Get push Attributes"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Push attributes list obtained"
                    )
            })
    @RequestMapping(
            path = "/entities/{entityUuid}/locations/{locationUuid}/attributes/push",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    List<BaseAttribute> listPushAttributes(
            @Parameter(description = "Entity UUID") @PathVariable String entityUuid,
            @Parameter(description = "Location UUID") @PathVariable String locationUuid
    ) throws NotFoundException, LocationException;

    @Operation(
            summary = "Get CSR Attributes"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "CSR Attributes list obtained"
                    )
            })
    @RequestMapping(
            path = "/entities/{entityUuid}/locations/{locationUuid}/attributes/issue",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    List<BaseAttribute> listCsrAttributes(
            @Parameter(description = "Entity UUID") @PathVariable String entityUuid,
            @Parameter(description = "Location UUID") @PathVariable String locationUuid
    ) throws NotFoundException, LocationException;

    @Operation(
            summary = "Push Certificate to Location"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Certificate pushed"
                    )
            })
    @RequestMapping(
            path = "/entities/{entityUuid}/locations/{locationUuid}/certificates/{certificateUuid}",
            method = RequestMethod.PUT,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    LocationDto pushCertificate(
            @Parameter(description = "Entity UUID") @PathVariable String entityUuid,
            @Parameter(description = "Location UUID") @PathVariable String locationUuid,
            @Parameter(description = "Certificate UUID") @PathVariable String certificateUuid,
            @RequestBody PushToLocationRequestDto request
    ) throws NotFoundException, LocationException, AttributeException;

    @Operation(
            summary = "Remove Certificate from Location"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Certificate removed"
                    )
            })
    @RequestMapping(
            path = "/entities/{entityUuid}/locations/{locationUuid}/certificates/{certificateUuid}",
            method = RequestMethod.DELETE,
            produces = {"application/json"}
    )
    LocationDto removeCertificate(
            @Parameter(description = "Entity UUID") @PathVariable String entityUuid,
            @Parameter(description = "Location UUID") @PathVariable String locationUuid,
            @Parameter(description = "Certificate UUID") @PathVariable String certificateUuid
    ) throws NotFoundException, LocationException;

    @Operation(
            summary = "Issue Certificate to Location",
            operationId = "issueCertificateToLocation"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Certificate issued"
                    )
            })
    @RequestMapping(
            path = "/entities/{entityUuid}/locations/{locationUuid}/certificates",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    LocationDto issueCertificate(
            @Parameter(description = "Entity UUID") @PathVariable String entityUuid,
            @Parameter(description = "Location UUID") @PathVariable String locationUuid,
            @RequestBody IssueToLocationRequestDto request
    ) throws ConnectorException, LocationException;

    @Operation(
            summary = "Sync Location content"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Content updated"
                    )
            })
    @RequestMapping(
            path = "/entities/{entityUuid}/locations/{locationUuid}/sync",
            method = RequestMethod.PUT,
            produces = {"application/json"}
    )
    LocationDto updateLocationContent(
            @Parameter(description = "Entity UUID") @PathVariable String entityUuid,
            @Parameter(description = "Location UUID") @PathVariable String locationUuid
    ) throws NotFoundException, LocationException;

    @Operation(
            summary = "Renew Certificate in Location"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Content updated"
                    )
            })
    @RequestMapping(
            path = "/entities/{entityUuid}/locations/{locationUuid}/certificates/{certificateUuid}",
            method = RequestMethod.PATCH,
            produces = {"application/json"}
    )
    LocationDto renewCertificateInLocation(
            @Parameter(description = "Entity UUID") @PathVariable String entityUuid,
            @Parameter(description = "Location UUID") @PathVariable String locationUuid,
            @Parameter(description = "Certificate UUID") @PathVariable String certificateUuid
    ) throws ConnectorException, LocationException;
}
