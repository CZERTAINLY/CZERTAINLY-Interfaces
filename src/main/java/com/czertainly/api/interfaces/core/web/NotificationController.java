package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.client.notification.NotificationDto;
import com.czertainly.api.model.client.notification.NotificationRequestDto;
import com.czertainly.api.model.client.notification.NotificationResponseDto;
import com.czertainly.api.model.common.AuthenticationServiceExceptionDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/notifications")
@Tag(name = "Notifications", description = "Notifications API")
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
public interface NotificationController {
    @Operation(summary = "List notifications for logged user")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of notifications")})
    @RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
    NotificationResponseDto listNotifications(@Parameter(in = ParameterIn.QUERY, description = "Show only unread notifications") NotificationRequestDto request) throws ValidationException;

    @Operation(summary = "Delete a notification for logged user")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Notification deleted")})
    @RequestMapping(path = "/{uuid}", method = RequestMethod.DELETE, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteNotification(@Parameter(description = "Notification UUID") @PathVariable String uuid) throws ValidationException, NotFoundException;

    @Operation(summary = "Mark notification as read for logged user")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Notification marked as read")})
    @RequestMapping(path = "/{uuid}", method = RequestMethod.PATCH, consumes = {"application/json"}, produces = {"application/json"})
    NotificationDto markNotificationAsRead(@Parameter(description = "Notification UUID") @PathVariable String uuid) throws ValidationException, NotFoundException;
}
