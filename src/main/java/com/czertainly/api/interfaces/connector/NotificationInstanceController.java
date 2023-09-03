package com.czertainly.api.interfaces.connector;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.attribute.v2.DataAttribute;
import com.czertainly.api.model.connector.notification.NotificationProviderInstanceDto;
import com.czertainly.api.model.connector.notification.NotificationProviderInstanceRequestDto;
import com.czertainly.api.model.connector.notification.NotificationProviderNotifyRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/notificationProvider")
@ApiResponses(
        value = {
                @ApiResponse(
                        responseCode = "400",
                        description = "Bad Request",
                        content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))
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
@Tag(name = "Notification instances Management", description = "Notification instances Management API")
public interface NotificationInstanceController {

    @Operation(
            summary = "List Notification instances"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Notification instance list retrieved"
                    )
            })
    @RequestMapping(path = "/notifications", method = RequestMethod.GET, produces = {"application/json"})
    List<NotificationProviderInstanceDto> listNotificationInstances();

    @Operation(
            summary = "Get Notification instance"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Notification instance retrieved"
                    )
            })
    @RequestMapping(path = "/notifications/{uuid}", method = RequestMethod.GET, produces = {"application/json"})
    NotificationProviderInstanceDto getNotificationInstance(@Parameter(description = "Notification Instance UUID") @PathVariable String uuid) throws NotFoundException;

    @Operation(
            summary = "Create Notification instance"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Notification instance created"
                    )
            })
    @RequestMapping(path = "/notifications", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    NotificationProviderInstanceDto createNotificationInstance(@RequestBody NotificationProviderInstanceRequestDto request) throws AlreadyExistException;

    @Operation(
            summary = "Update Notification instance"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Notification instance updated"
                    )
            })
    @RequestMapping(path = "/notifications/{uuid}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
    NotificationProviderInstanceDto updateNotificationInstance(@Parameter(description = "Notification Instance UUID") @PathVariable String uuid, @RequestBody NotificationProviderInstanceRequestDto request) throws NotFoundException;

    @Operation(
            summary = "Remove Notification instance"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Notification instance removed"
                    )
            })
    @RequestMapping(path = "/notifications/{uuid}", method = RequestMethod.DELETE, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void removeNotificationInstance(@Parameter(description = "Notification Instance UUID") @PathVariable String uuid) throws NotFoundException;

    @Operation(
            summary = "Notify by Notification instance"
    )
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Notification sent")})
    @RequestMapping(path = "/notifications/{uuid}/notify", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void sendNotification(
            @Parameter(description = "Notification Instance UUID") @PathVariable String uuid,
            @RequestBody NotificationProviderNotifyRequestDto request) throws NotFoundException;

    @Operation(summary = "List of mapping attributes")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of mapping attributes")})
    @RequestMapping(path = "/{kind}/attributes/mapping", method = RequestMethod.GET, produces = {"application/json"})
    List<DataAttribute> listMappingAttributes(@Parameter(description = "Kind") @PathVariable String kind);

}