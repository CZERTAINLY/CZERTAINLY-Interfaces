package com.otilm.api.interfaces.core.web;

import com.otilm.api.exception.AlreadyExistException;
import com.otilm.api.exception.NotFoundException;
import com.otilm.api.interfaces.AuthProtectedController;
import com.otilm.api.model.common.ErrorMessageDto;
import com.otilm.api.model.core.auth.Resource;
import com.otilm.api.model.core.workflows.ActionDetailDto;
import com.otilm.api.model.core.workflows.ActionDto;
import com.otilm.api.model.core.workflows.ActionRequestDto;
import com.otilm.api.model.core.workflows.ExecutionDto;
import com.otilm.api.model.core.workflows.ExecutionRequestDto;
import com.otilm.api.model.core.workflows.UpdateActionRequestDto;
import com.otilm.api.model.core.workflows.UpdateExecutionRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/v1/workflows")
@Tag(name = "Workflow Actions Management", description = "Workflow Actions Management API")
public interface ActionController extends AuthProtectedController {

    // EXECUTIONS

    @Operation(summary = "List executions")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of executions fetched")})
    @GetMapping(path = "/executions", produces = {"application/json"})
    List<ExecutionDto> listExecutions(@RequestParam(required = false) Resource resource);

    @Operation(summary = "Create Execution")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Execution created")})
    @PostMapping(path = "/executions", consumes = {"application/json"}, produces = {"application/json"})
    ExecutionDto createExecution(@Valid @RequestBody ExecutionRequestDto request)
            throws AlreadyExistException, NotFoundException;

    @Operation(summary = "Get Execution Details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Execution details retrieved"),
            @ApiResponse(responseCode = "404", description = "Execution not found",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class)))})
    @GetMapping(path = "/executions/{executionUuid}", produces = {"application/json"})
    ExecutionDto getExecution(@Parameter(description = "Execution UUID") @PathVariable String executionUuid)
            throws NotFoundException;

    @Operation(summary = "Update Execution")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Execution updated"),
            @ApiResponse(responseCode = "404", description = "Execution not found",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))),
            @ApiResponse(responseCode = "409", description = "Execution already exists",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class)))})
    @PutMapping(path = "/executions/{executionUuid}", consumes = {"application/json"}, produces = {"application/json"})
    ExecutionDto updateExecution(@Parameter(description = "Execution UUID") @PathVariable String executionUuid,
            @RequestBody @Valid UpdateExecutionRequestDto request) throws NotFoundException, AlreadyExistException;

    @Operation(summary = "Delete Execution")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Execution deleted"),
            @ApiResponse(responseCode = "404", description = "Execution not found",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class)))})
    @DeleteMapping(path = "/executions/{executionUuid}", produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteExecution(@Parameter(description = "Execution UUID") @PathVariable String executionUuid)
            throws NotFoundException;

    // ACTIONS

    @Operation(summary = "List Actions")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of actions fetched")})
    @GetMapping(path = "/actions", produces = {"application/json"})
    List<ActionDto> listActions(@RequestParam(required = false) Resource resource);

    @Operation(summary = "Create Action")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Action created"),
            @ApiResponse(responseCode = "404", description = "Execution not found",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class)))})
    @PostMapping(path = "/actions", consumes = {"application/json"}, produces = {"application/json"})
    ActionDetailDto createAction(@RequestBody ActionRequestDto request) throws NotFoundException, AlreadyExistException;

    @Operation(summary = "Get Action Details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Action details retrieved"),
            @ApiResponse(responseCode = "404", description = "Action not found",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class)))})
    @GetMapping(path = "/actions/{actionUuid}", produces = {"application/json"})
    ActionDetailDto getAction(@Parameter(description = "Action UUID") @PathVariable String actionUuid)
            throws NotFoundException;

    @Operation(summary = "Update Action")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Action updated"),
            @ApiResponse(responseCode = "404", description = "Action or Execution not found",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))),
            @ApiResponse(responseCode = "409", description = "Action already exists",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class)))})
    @PutMapping(path = "/actions/{actionUuid}", consumes = {"application/json"}, produces = {"application/json"})
    ActionDetailDto updateAction(@Parameter(description = "Action UUID") @PathVariable String actionUuid,
            @RequestBody @Valid UpdateActionRequestDto request) throws NotFoundException, AlreadyExistException;

    @Operation(summary = "Delete Action")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Action deleted"),
            @ApiResponse(responseCode = "404", description = "Action not found",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class)))})
    @DeleteMapping(path = "/actions/{actionUuid}", produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteAction(@Parameter(description = "Action UUID") @PathVariable String actionUuid) throws NotFoundException;
}
