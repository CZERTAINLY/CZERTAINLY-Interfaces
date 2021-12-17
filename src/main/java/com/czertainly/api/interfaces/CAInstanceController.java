package com.czertainly.api.interfaces;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.AttributeDefinition;
import com.czertainly.api.model.ca.CAInstanceDto;
import com.czertainly.api.model.ca.ConnectorCAInstanceDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/caConnector/authorities")
@Tag(name = "Certification Authority API", description = "Certification Authority API")
public interface CAInstanceController {

    @Operation(
            summary = "List CA Instances",
            description = "Method for listing all CA Instances managed by CA connector."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "CA instances retrieved"
                    )
            })
    @RequestMapping(method = RequestMethod.GET)
    List<ConnectorCAInstanceDto> listCAInstances();

    @Operation(
            summary = "Get CA instance",
            description = "Method for retrieving detail of CA instance managed by CA connector."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "CA instance retrieved"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = @Content
                    )
            })
    @RequestMapping(path = "/{uuid}", method = RequestMethod.GET)
    ConnectorCAInstanceDto getCAInstance(@PathVariable String uuid) throws NotFoundException;

    @Operation(
            summary = "Create CA instance",
            description = "Method for creating new CA instance to be managed by CA connector."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "CA instance created"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content
                    )
            })
    @RequestMapping(method = RequestMethod.POST)
    ConnectorCAInstanceDto createCAInstance(@RequestBody CAInstanceDto request) throws AlreadyExistException;

    @Operation(
            summary = "Update CA instance",
            description = "Method for updating existing CA instance managed by CA connector."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "CA instance updated"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = @Content
                    )
            })
    @RequestMapping(path = "/{uuid}", method = RequestMethod.POST)
    ConnectorCAInstanceDto updateCAInstance(@PathVariable String uuid, @RequestBody CAInstanceDto request) throws NotFoundException;

    @Operation(
            summary = "Remove CA instance",
            description = "Method for removing existing CA instance managed by CA connector."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "CA instance removed"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = @Content
                    )
            })
    @RequestMapping(path = "/{uuid}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void removeCAInstance(@PathVariable String uuid) throws NotFoundException;

    @RequestMapping(path = "/{uuid}/connect", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void getConnection(@PathVariable String uuid) throws NotFoundException;

    @Operation(
            summary = "List RA profile attributes",
            description = "Method for listing RA profile attributes needed to create RA profile."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "CA instance updated"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = @Content
                    )
            })
    @RequestMapping(path = "/{uuid}/raProfiles/attributes", method = RequestMethod.GET)
    List<AttributeDefinition> listRAProfileAttributes(
            @PathVariable String uuid) throws NotFoundException;

    @Operation(
            summary = "Validate RA profile attributes",
            description = "Method for validating RA profile attributes."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "CA instance updated"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = @Content
                    )
            })
    @RequestMapping(path = "/{uuid}/raProfiles/attributes/validate", method = RequestMethod.POST)
    boolean validateRAProfileAttributes(
            @PathVariable String uuid,
            @RequestBody List<AttributeDefinition> attributes) throws ValidationException, NotFoundException;
}