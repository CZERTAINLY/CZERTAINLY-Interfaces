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
@Tag(name = "Workflow Actions Management", description = "Workflow Actions Management API")
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
public interface ActionController {

    // EXECUTIONS

    @Operation(summary = "List executions")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of executions fetched")})
    @RequestMapping(path = "/executions", method = RequestMethod.GET, produces = {"application/json"})
    List<ExecutionDto> listExecutions(@RequestParam(required = false) Resource resource);

    @Operation(summary = "Create Execution")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Execution created")})
    @RequestMapping(path = "/executions", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    ExecutionDto createExecution(@RequestBody ExecutionRequestDto request) throws AlreadyExistException;

    @Operation(summary = "Get Execution Details")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Execution details retrieved")})
    @RequestMapping(path = "/executions/{executionUuid}", method = RequestMethod.GET, produces = {"application/json"})
    ExecutionDto getExecution(@Parameter(description = "Execution UUID") @PathVariable String executionUuid) throws NotFoundException;

    @Operation(summary = "Update Execution")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Execution updated")})
    @RequestMapping(path = "/executions/{executionUuid}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
    ExecutionDto updateExecution(@Parameter(description = "Execution UUID") @PathVariable String executionUuid, @RequestBody UpdateExecutionRequestDto request) throws NotFoundException;

    @Operation(summary = "Delete Execution")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Execution deleted")})
    @RequestMapping(path = "/executions/{executionUuid}", method = RequestMethod.DELETE, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteExecution(@Parameter(description = "Execution UUID") @PathVariable String executionUuid) throws NotFoundException;

    // ACTIONS

    @Operation(summary = "List Actions")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of actions fetched")})
    @RequestMapping(path = "/actions", method = RequestMethod.GET, produces = {"application/json"})
    List<ActionDto> listActions(@RequestParam(required = false) Resource resource);

    @Operation(summary = "Create Action")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Action created")})
    @RequestMapping(path = "/actions", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    ActionDetailDto createAction(@RequestBody ActionRequestDto request) throws NotFoundException, AlreadyExistException;

    @Operation(summary = "Get Action Details")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Action details retrieved")})
    @RequestMapping(path = "/actions/{actionUuid}", method = RequestMethod.GET, produces = {"application/json"})
    ActionDetailDto getAction(@Parameter(description = "Action UUID") @PathVariable String actionUuid) throws NotFoundException;

    @Operation(summary = "Update Action")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Action updated")})
    @RequestMapping(path = "/actions/{actionUuid}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
    ActionDetailDto updateAction(@Parameter(description = "Action UUID") @PathVariable String actionUuid, @RequestBody UpdateActionRequestDto request) throws NotFoundException;

    @Operation(summary = "Delete Action")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Action deleted")})
    @RequestMapping(path = "/actions/{actionUuid}", method = RequestMethod.DELETE, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteAction(@Parameter(description = "Action UUID") @PathVariable String actionUuid) throws NotFoundException;
}
