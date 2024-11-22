package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.model.common.AuthenticationServiceExceptionDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.core.settings.*;
import com.czertainly.api.model.core.settings.logging.LoggingSettingsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/settings")
@Tag(name = "Settings", description = "Settings API")
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
public interface SettingController {

    @Operation(
            summary = "Get platform settings"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Platform settings retrieved")
            })
    @RequestMapping(
            method = RequestMethod.GET,
            path = "/platform",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    PlatformSettingsDto getPlatformSettings();

    @Operation(
            summary = "Update platform setting"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Setting updated")
            })
    @RequestMapping(
            method = RequestMethod.PUT,
            path = "/platform",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    void updatePlatformSettings(
            @RequestBody PlatformSettingsDto platformSettingsDto
    );

    @Operation(
            summary = "Get notification settings"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Notiifcation settings retrieved")
            })
    @RequestMapping(
            method = RequestMethod.GET,
            path = "/notifications",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    NotificationSettingsDto getNotificationsSettings();

    @Operation(
            summary = "Update notifications setting"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Setting updated")
            })
    @RequestMapping(
            method = RequestMethod.PUT,
            path = "/notifications",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    void updateNotificationsSettings(
            @RequestBody NotificationSettingsDto notificationSettingsDto
    );

    @Operation(
            summary = "Get logging settings"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Logging settings retrieved")
            })
    @GetMapping(path = "/logging", produces = MediaType.APPLICATION_JSON_VALUE)
    LoggingSettingsDto getLoggingSettings();

    @Operation(
            summary = "Update logging setting"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Setting updated")
            })
    @PutMapping(path = "/logging", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    void updateLoggingSettings(@RequestBody LoggingSettingsDto loggingSettingsDto);
}
