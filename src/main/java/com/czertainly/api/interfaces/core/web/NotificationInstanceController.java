package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.AttributeException;
import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.model.common.AuthenticationServiceExceptionDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.UuidDto;
import com.czertainly.api.model.common.attribute.v2.DataAttribute;
import com.czertainly.api.model.core.notification.NotificationInstanceDto;
import com.czertainly.api.model.core.notification.NotificationInstanceRequestDto;
import com.czertainly.api.model.core.notification.NotificationInstanceUpdateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/notificationInstances")
@Tag(name = "External Notification Management",
        description = "External Notification Management API for managing notification instances that can be used " +
                "to notify recipient(s) about events that were triggered in the platform. Note that this API does " +
                "not handle internal notifications. For internal notifications, please refer to the Internal " +
                "Notification API"
)
@ApiResponses(value = {@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))), @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema())), @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = AuthenticationServiceExceptionDto.class))), @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))), @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content), @ApiResponse(responseCode = "502", description = "Connector Error", content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))), @ApiResponse(responseCode = "503", description = "Connector Communication Error", content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))),})
public interface NotificationInstanceController {

    @Operation(summary = "List of available Notification instances")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of Notification instances")})
    @RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
    List<NotificationInstanceDto> listNotificationInstances();

    @Operation(summary = "Details of an Notification instance")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Notification instance details retrieved")})
    @RequestMapping(path = "/{uuid}", method = RequestMethod.GET, produces = {"application/json"})
    NotificationInstanceDto getNotificationInstance(
            @Parameter(description = "Notification instance UUID") @PathVariable String uuid) throws ConnectorException;

    @Operation(summary = "Add Notification instance")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "New Notification instance added", content = @Content(schema = @Schema(implementation = UuidDto.class))), @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)), examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")})),})
    @RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    ResponseEntity<?> createNotificationInstance(
            @RequestBody NotificationInstanceRequestDto request) throws AlreadyExistException, ConnectorException, AttributeException;

    @Operation(summary = "Edit Notification instance")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Notification instance details updated"), @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)), examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @RequestMapping(path = "/{uuid}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
    NotificationInstanceDto editNotificationInstance(
            @Parameter(description = "Notification instance UUID") @PathVariable String uuid,
            @RequestBody NotificationInstanceUpdateRequestDto request) throws ConnectorException, AttributeException;

    @Operation(summary = "Delete Notification instance")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Notification instance deleted")})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/{uuid}", method = RequestMethod.DELETE, produces = {"application/json"})
    void deleteNotificationInstance(
            @Parameter(description = "Notification instance UUID") @PathVariable String uuid) throws ConnectorException;

    @Operation(summary = "List of mapping attributes")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of mapping attributes")})
    @RequestMapping(path = "/attributes/mapping/{connectorUuid}/{kind}", method = RequestMethod.GET, produces = {"application/json"})
    List<DataAttribute> listMappingAttributes(
            @Parameter(description = "Connector UUID") @PathVariable String connectorUuid,
            @Parameter(description = "Kind") @PathVariable String kind) throws ConnectorException;
}
