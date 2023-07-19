package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.client.approval.ApprovalDetailDto;
import com.czertainly.api.model.client.approval.ApprovalResponseDto;
import com.czertainly.api.model.client.approval.ApprovalUserDto;
import com.czertainly.api.model.client.approval.UserApprovalDto;
import com.czertainly.api.model.common.AuthenticationServiceExceptionDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.core.scheduler.PaginationRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/approvals")
@Tag(name = "Approval Inventory", description = "Approval Inventory API")
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
public interface ApprovalController {

    @Operation(summary = "List of Approvals")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of all approvals")})
    @RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
    ApprovalResponseDto listApprovals(@RequestBody final PaginationRequestDto paginationRequestDto) throws ValidationException;

    @Operation(summary = "List of User's Approvals")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of all approvals")})
    @RequestMapping(method = RequestMethod.GET, path = "/user", produces = {"application/json"})
    ApprovalResponseDto listUserApprovals(@RequestBody final PaginationRequestDto paginationRequestDto,
            @Parameter(in = ParameterIn.QUERY, description = "Select if you want to list all history of approvals by user") ApprovalUserDto approvalUserDto) throws ValidationException;

    @Operation(summary = "Get Approval Detail")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Approval detail retrieved")})
    @RequestMapping(path = "/{uuid}", method = RequestMethod.GET, produces = {"application/json"})
    ApprovalDetailDto getApproval(@Parameter(description = "Approval UUID") @PathVariable String uuid)
            throws NotFoundException;


    @Operation(summary = "Approving of the Approval")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Approval approved")})
    @RequestMapping(path = "/{uuid}/approve", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void approveApproval(@Parameter(description = "Approval UUID") @PathVariable String uuid) throws NotFoundException;

    @Operation(summary = "Rejecting of the Approval")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Approval rejected")})
    @RequestMapping(path = "/{uuid}/reject", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void rejectApproval(@Parameter(description = "Approval UUID") @PathVariable String uuid) throws NotFoundException;

    @Operation(summary = "Approving of Recipient of the Approval")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Approval Recipient approved")})
    @RequestMapping(path = "/{uuid}/approveRecipient", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void approveApprovalRecipient(@Parameter(description = "Approval UUID") @PathVariable String uuid,
                                  @RequestBody UserApprovalDto userApprovalDto) throws NotFoundException;

    @Operation(summary = "Rejecting of Recipient of the Approval")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Approval Recipient rejected")})
    @RequestMapping(path = "/{uuid}/rejectRecipient", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void rejectApprovalRecipient(@Parameter(description = "Approval UUID") @PathVariable String uuid,
                                 @RequestBody UserApprovalDto userApprovalDto) throws NotFoundException;

}
