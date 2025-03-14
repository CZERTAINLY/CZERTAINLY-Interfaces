package com.czertainly.api.interfaces.connector;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.interfaces.NoAuthController;
import com.czertainly.api.model.common.attribute.v2.DataAttribute;
import com.czertainly.api.model.connector.notification.NotificationProviderInstanceDto;
import com.czertainly.api.model.connector.notification.NotificationProviderInstanceRequestDto;
import com.czertainly.api.model.connector.notification.NotificationProviderNotifyRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/v1/notificationProvider")
@Tag(name = "Notification instances Management", description = "Notification instances Management API")
public interface NotificationInstanceController extends NoAuthController {

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
    @GetMapping(path = "/notifications", produces = {"application/json"})
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
    @GetMapping(path = "/notifications/{uuid}", produces = {"application/json"})
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
    @PostMapping(path = "/notifications", consumes = {"application/json"}, produces = {"application/json"})
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
    @PutMapping(path = "/notifications/{uuid}", consumes = {"application/json"}, produces = {"application/json"})
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
    @DeleteMapping(path = "/notifications/{uuid}", produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void removeNotificationInstance(@Parameter(description = "Notification Instance UUID") @PathVariable String uuid) throws NotFoundException;

    @Operation(
            summary = "Notify by Notification instance"
    )
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Notification sent")})
    @PostMapping(path = "/notifications/{uuid}/notify", consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void sendNotification(
            @Parameter(description = "Notification Instance UUID") @PathVariable String uuid,
            @RequestBody NotificationProviderNotifyRequestDto request) throws NotFoundException;

    @Operation(summary = "List of mapping attributes")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of mapping attributes")})
    @GetMapping(path = "/{kind}/attributes/mapping", produces = {"application/json"})
    List<DataAttribute> listMappingAttributes(@Parameter(description = "Kind") @PathVariable String kind);

}