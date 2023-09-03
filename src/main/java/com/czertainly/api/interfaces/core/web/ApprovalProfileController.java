package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.client.approvalprofile.*;
import com.czertainly.api.model.client.notification.NotificationRequestDto;
import com.czertainly.api.model.common.AuthenticationServiceExceptionDto;
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

@RestController
@RequestMapping("/v1/approvalProfiles")
@Tag(name = "Approval profile Inventory", description = "Approval profile Inventory API")
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
                )
        })
public interface ApprovalProfileController {

    @Operation(summary = "List Approval Profiles")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of all the approval profiles")})
    @RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
    ApprovalProfileResponseDto listApprovalProfiles(final PaginationRequestDto paginationRequestDto);

    @Operation(summary = "Get Approval Profile Details")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Approval profile retrieved")})
    @RequestMapping(path = "/{uuid}", method = RequestMethod.GET, produces = {"application/json"})
    ApprovalProfileDetailDto getApprovalProfile(@Parameter(description = "Approval profile UUID") @PathVariable String uuid,
                                                @Parameter(in = ParameterIn.QUERY, description = "Select specific version of the approval profile") ApprovalProfileForVersionDto approvalProfileForVersionDto)
            throws NotFoundException;

    @Operation(summary = "Delete an approval profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Approval profile deleted")})
    @RequestMapping(path = "/{uuid}", method = RequestMethod.DELETE, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteApprovalProfile(@Parameter(description = "Approval profile UUID") @PathVariable String uuid) throws NotFoundException, ValidationException;

    @Operation(summary = "Enabling of Approval profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Approval profile enabled")})
    @RequestMapping(path = "/{uuid}/enable", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void enableApprovalProfile(@Parameter(description = "Approval profile UUID") @PathVariable String uuid) throws NotFoundException, ValidationException;

    @Operation(summary = "Disabling of Approval profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Approval profile disabled")})
    @RequestMapping(path = "/{uuid}/disable", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void disableApprovalProfile(@Parameter(description = "Approval profile UUID") @PathVariable String uuid) throws NotFoundException, ValidationException;

    @Operation(summary = "Create a Approval profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "New Approval profile created", content = @Content(schema = @Schema(implementation = UuidDto.class))),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")})),})

    @RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    ResponseEntity<?> createApprovalProfile(@RequestBody ApprovalProfileRequestDto approvalProfileRequestDto) throws NotFoundException, AlreadyExistException;

    @Operation(summary = "Edit an Approval profile")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Approval profile updated"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @RequestMapping(path = "/{uuid}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {
            "application/json"})
    ResponseEntity<?> editApprovalProfile(@Parameter(description = "Approval profile UUID") @PathVariable String uuid, @RequestBody ApprovalProfileUpdateRequestDto approvalProfileUpdateRequestDto) throws NotFoundException;

}
