package com.czertainly.api.interfaces.core.web;


import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.model.common.AuthenticationServiceExceptionDto;
import com.czertainly.api.model.core.auth.Resource;
import com.czertainly.api.model.core.other.ResourceDto;
import com.czertainly.api.model.core.other.ResourceEventDto;
import com.czertainly.api.model.core.search.SearchFieldDataByGroupDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/resources")
@Tag(name = "Resource Management", description = "Resource Management API")
@ApiResponses(
        value = {
                @ApiResponse(
                        responseCode = "400",
                        description = "Bad Request",
                        content = @Content(schema = @Schema(implementation = AuthenticationServiceExceptionDto.class))
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
                        content = @Content(schema = @Schema(implementation = AuthenticationServiceExceptionDto.class))
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal Server Error",
                        content = @Content
                )
        })
public interface ResourceController {

    @Operation(summary = "Retrieve list of resources with information and settings")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Resources retrieved")})
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    List<ResourceDto> listResources();

    @Operation(summary = "Retrieve filter fields that can be used for creating rule conditions and actions")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Filter fields retrieved")})
    @RequestMapping(path = "/{resource}/filters/rules", method = RequestMethod.GET, produces = {"application/json"})
    List<SearchFieldDataByGroupDto> listResourceRuleFilterFields(@Parameter(description = "Resource") @PathVariable Resource resource, @RequestParam(required = false) boolean settable) throws NotFoundException;

    @Operation(summary = "Retrieve a list of all events that can be triggered by a resource")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Events retrieved")})
    @RequestMapping(path = "/{resource}/events", method = RequestMethod.GET, produces = {"application/json"})
    List<ResourceEventDto> listResourceEvents(@Parameter(description = "Resource") @PathVariable Resource resource);
}
