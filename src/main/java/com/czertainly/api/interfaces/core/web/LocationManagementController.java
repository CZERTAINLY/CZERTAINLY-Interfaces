package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.client.location.AddLocationRequestDto;
import com.czertainly.api.model.client.location.EditLocationRequestDto;
import com.czertainly.api.model.client.location.IssueToLocationRequestDto;
import com.czertainly.api.model.client.location.PushToLocationRequestDto;
import com.czertainly.api.model.common.AttributeDefinition;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.UuidDto;
import com.czertainly.api.model.core.location.LocationDto;
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
@RequestMapping("/v1/locations")
@Tag(name = "Location Management API", description = "Location Management API")
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

public interface LocationManagementController {

    @Operation(
            summary = "List of available Locations"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Locations retrieved"
                    )
            })
    @RequestMapping(
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    List<LocationDto> listLocations();

    @Operation(
            summary = "List of available Locations by Status"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Locations retrieved"
                    )
            })
    @RequestMapping(
            method = RequestMethod.GET,
            params = {"isEnabled"},
            produces = {"application/json"}
    )
    List<LocationDto> listLocations(
            @RequestParam Boolean isEnabled
    );

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
                            description = "Unprocessible Entity",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                            examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")})
                    )
            })
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    ResponseEntity<?> addLocation(
            @RequestBody AddLocationRequestDto request
    ) throws AlreadyExistException, ValidationException, ConnectorException;

    @Operation(
            summary = "Get information about the Location"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Location detail retrieved"
                    )
            })
    @RequestMapping(
            path = "/{locationUuid}",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    LocationDto getLocation(
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
            path = "/{locationUuid}",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    LocationDto editLocation(
            @Parameter(description = "Location UUID") @PathVariable String locationUuid,
            @RequestBody EditLocationRequestDto request
    ) throws ConnectorException;

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
            path = "/{locationUuid}",
            method = RequestMethod.DELETE,
            produces = {"application/json"}
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void removeLocation(
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
            path = "/{locationUuid}/disable",
            method = RequestMethod.PUT,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void disableLocation(
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
            path = "/{locationUuid}/enable",
            method = RequestMethod.PUT,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void enableLocation(
            @Parameter(description = "Location UUID") @PathVariable String locationUuid
    ) throws NotFoundException;

    @Operation(
            summary = "Get push Attributes"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Attributes list obtained"
                    )
            })
    @RequestMapping(
            path = "{locationUuid}/push/attributes",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    List<AttributeDefinition> listPushAttributes(
            @Parameter(description = "Location UUID") @PathVariable String locationUuid
    ) throws ConnectorException;

    @Operation(
            summary = "Get CSR generate Attributes"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Attributes list obtained"
                    )
            })
    @RequestMapping(
            path = "{locationUuid}/issue/attributes",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    List<AttributeDefinition> listCsrAttributes(
            @Parameter(description = "Location UUID") @PathVariable String locationUuid
    ) throws ConnectorException;

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
            path = "{locationUuid}/push/{certificateUuid}",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    LocationDto pushCertificate(
            @Parameter(description = "Location UUID") @PathVariable String locationUuid,
            @Parameter(description = "Certificate UUID") @PathVariable String certificateUuid,
            @RequestBody PushToLocationRequestDto request
    ) throws ConnectorException;

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
            path = "{locationUuid}/remove/{certificateUuid}",
            method = RequestMethod.DELETE,
            produces = {"application/json"}
    )
    LocationDto removeCertificate(
            @Parameter(description = "Location UUID") @PathVariable String locationUuid,
            @Parameter(description = "Certificate UUID") @PathVariable String certificateUuid
    ) throws ConnectorException;

    @Operation(
            summary = "Issue Certificate for Location"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Certificate issued"
                    )
            })
    @RequestMapping(
            path = "{locationUuid}/issue/{raProfileUuid}",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    LocationDto issueCertificate(
            @Parameter(description = "Location UUID") @PathVariable String locationUuid,
            @Parameter(description = "RA Profile UUID") @PathVariable String raProfileUuid,
            @RequestBody IssueToLocationRequestDto request
    ) throws ConnectorException;

    // update location data
}
