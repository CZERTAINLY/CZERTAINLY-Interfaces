package com.czertainly.api.interfaces.core.web;


import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.interfaces.AuthProtectedController;
import com.czertainly.api.model.common.ErrorMessageDto;
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

@RequestMapping("/v1/resources")
@Tag(name = "Resource Management", description = "Resource Management API")
public interface ResourceController extends AuthProtectedController {

    @Operation(summary = "Retrieve list of resources with information and settings")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Resources retrieved")})
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    List<ResourceDto> listResources();

    @Operation(summary = "Retrieve filter fields that can be used for creating rule conditions and actions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filter fields retrieved"),
            @ApiResponse(responseCode = "404", description = "Resource objects not found", content = @Content(schema = @Schema(implementation = ErrorMessageDto.class)))
    })
    @GetMapping(path = "/{resource}/filters/rules", produces = {"application/json"})
    List<SearchFieldDataByGroupDto> listResourceRuleFilterFields(@Parameter(description = "Resource") @PathVariable Resource resource, @RequestParam(required = false) boolean settable) throws NotFoundException;

    @Operation(summary = "Retrieve a list of all events that can be triggered by a resource")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Events retrieved")})
    @GetMapping(path = "/{resource}/events", produces = {"application/json"})
    List<ResourceEventDto> listResourceEvents(@Parameter(description = "Resource") @PathVariable Resource resource);
}
