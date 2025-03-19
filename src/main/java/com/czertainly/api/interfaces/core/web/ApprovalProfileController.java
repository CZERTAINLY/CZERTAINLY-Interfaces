package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.interfaces.AuthProtectedController;
import com.czertainly.api.model.client.approvalprofile.*;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.UuidDto;
import com.czertainly.api.model.core.scheduler.PaginationRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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

@RequestMapping("/v1/approvalProfiles")
@Tag(name = "Approval profile Inventory", description = "Approval profile Inventory API")
public interface ApprovalProfileController extends AuthProtectedController {

    @Operation(summary = "List Approval Profiles")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of all the approval profiles")})
    @GetMapping(produces = {"application/json"})
    ApprovalProfileResponseDto listApprovalProfiles(final PaginationRequestDto paginationRequestDto);

    @Operation(summary = "Get Approval Profile Details")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Approval profile retrieved"), @ApiResponse(responseCode = "404", description = "Approval profile not found", content = @Content(schema = @Schema(implementation = ErrorMessageDto.class)))})
    @GetMapping(path = "/{uuid}", produces = {"application/json"})
    ApprovalProfileDetailDto getApprovalProfile(@Parameter(description = "Approval profile UUID") @PathVariable String uuid, @Parameter(in = ParameterIn.QUERY, description = "Select specific version of the approval profile") ApprovalProfileForVersionDto approvalProfileForVersionDto) throws NotFoundException;

    @Operation(summary = "Delete an approval profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Approval profile deleted"), @ApiResponse(responseCode = "404", description = "Approval profile not found", content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))), @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)), examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @DeleteMapping(path = "/{uuid}", produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteApprovalProfile(@Parameter(description = "Approval profile UUID") @PathVariable String uuid) throws NotFoundException, ValidationException;

    @Operation(summary = "Create a Approval profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "New Approval profile created", content = @Content(schema = @Schema(implementation = UuidDto.class))), @ApiResponse(responseCode = "404", description = "Approval profile not found", content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))), @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)), examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")})),})
    @PostMapping(consumes = {"application/json"}, produces = {"application/json"})
    ResponseEntity<?> createApprovalProfile(@RequestBody ApprovalProfileRequestDto approvalProfileRequestDto) throws NotFoundException, AlreadyExistException;

    @Operation(summary = "Edit an Approval profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Approval profile updated"), @ApiResponse(responseCode = "404", description = "Approval profile not found", content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))), @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)), examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @PutMapping(path = "/{uuid}", consumes = {"application/json"}, produces = {"application/json"})
    ResponseEntity<?> editApprovalProfile(@Parameter(description = "Approval profile UUID") @PathVariable String uuid, @RequestBody ApprovalProfileUpdateRequestDto approvalProfileUpdateRequestDto) throws NotFoundException;

}
