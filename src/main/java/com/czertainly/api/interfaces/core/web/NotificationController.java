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
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/notifications")
@Tag(name = "Internal Notification",
        description = "Internal Notifications API that manages notifications for logged user in the platform. Note " +
                "that this API does not manage nor trigger external notifications. For external notifications, please " +
                "refer to the External Notification Management API."
)
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
    @RequestMapping(path = "/{uuid}", method = RequestMethod.PATCH, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void markNotificationAsRead(@Parameter(description = "Notification UUID") @PathVariable String uuid) throws ValidationException, NotFoundException;

    @Operation(summary = "Delete a list of notifications for logged user")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Notifications deleted")})
    @RequestMapping(method = RequestMethod.DELETE, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void bulkDeleteNotification(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Notifications UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples={@ExampleObject(value="[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")})) @RequestBody List<String> uuids);

    @Operation(summary = "Mark a list of notifications as read for logged user")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Notifications marked as read")})
    @RequestMapping(method = RequestMethod.PATCH, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void bulkMarkNotificationAsRead(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Notifications UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples={@ExampleObject(value="[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")})) @RequestBody List<String> uuids);
}
