package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.model.common.AuthenticationServiceExceptionDto;
import com.czertainly.api.model.core.auth.Resource;
import com.czertainly.api.model.core.workflows.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/workflows")
@Tag(name = "Workflow Triggers Management", description = "Workflow Triggers Management API")
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
public interface TriggerController {
    @Operation(summary = "List Triggers")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of triggers")})
    @RequestMapping(path = "/triggers", method = RequestMethod.GET, produces = {"application/json"})
    List<TriggerDto> listTriggers(@RequestParam(required = false) Resource resource, @RequestParam(required = false) Resource eventResource);

    @Operation(summary = "Create Trigger")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Trigger created")})
    @RequestMapping(path = "/triggers", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    TriggerDetailDto createTrigger(@RequestBody TriggerRequestDto request) throws NotFoundException, AlreadyExistException;

    @Operation(summary = "Get Trigger details")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Trigger details retrieved")})
    @RequestMapping(path = "/triggers/{triggerUuid}", method = RequestMethod.GET, produces = {"application/json"})
    TriggerDetailDto getTrigger(@Parameter(description = "Trigger UUID") @PathVariable String triggerUuid) throws NotFoundException;

    @Operation(summary = "Update Trigger")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Trigger updated")})
    @RequestMapping(path = "/triggers/{triggerUuid}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
    TriggerDetailDto updateTrigger(@Parameter(description = "Trigger UUID") @PathVariable String triggerUuid, @RequestBody UpdateTriggerRequestDto request) throws NotFoundException;

    @Operation(summary = "Delete Trigger")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Trigger deleted")})
    @RequestMapping(path = "/triggers/{triggerUuid}", method = RequestMethod.DELETE, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteTrigger(@Parameter(description = "Trigger UUID") @PathVariable String triggerUuid) throws NotFoundException;

    @Operation(summary = "Get Trigger History")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Trigger History retrieved")})
    @RequestMapping(path = "/triggers/{triggerUuid}/history/{triggerObjectUuid}", method = RequestMethod.GET, produces = {"application/json"})
    List<TriggerHistoryDto> getTriggerHistory(@Parameter(description = "Trigger UUID") @PathVariable String triggerUuid, @Parameter(description = "Trigger Object UUID") @PathVariable String triggerObjectUuid);

}
