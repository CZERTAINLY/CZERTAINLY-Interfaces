package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.model.common.AuthenticationServiceExceptionDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.core.settings.*;
import com.czertainly.api.model.core.settings.authentication.AuthenticationSettingsDto;
import com.czertainly.api.model.core.settings.authentication.AuthenticationSettingsUpdateDto;
import com.czertainly.api.model.core.settings.authentication.OAuth2ProviderSettingsDto;
import com.czertainly.api.model.core.settings.authentication.OAuth2ProviderSettingsUpdateDto;
import com.czertainly.api.model.core.settings.logging.LoggingSettingsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/settings")
@Tag(name = "Settings", description = "Settings API")
@ApiResponses(value = {@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))), @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema())), @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = AuthenticationServiceExceptionDto.class))), @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))), @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
public interface SettingController {

    @Operation(summary = "Get platform settings")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Platform settings retrieved")})
    @GetMapping(path = "/platform", produces = MediaType.APPLICATION_JSON_VALUE)
    PlatformSettingsDto getPlatformSettings();

    @Operation(summary = "Update platform setting")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Setting updated")})
    @PutMapping(path = "/platform", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    void updatePlatformSettings(@RequestBody PlatformSettingsDto platformSettingsDto);

    @Operation(summary = "Get notification settings")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Notification settings retrieved")})
    @GetMapping(path = "/notifications", produces = MediaType.APPLICATION_JSON_VALUE)
    NotificationSettingsDto getNotificationsSettings();

    @Operation(summary = "Update notifications setting")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Setting updated")})
    @PutMapping(path = "/notifications", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    void updateNotificationsSettings(@RequestBody NotificationSettingsDto notificationSettingsDto);

    @Operation(summary = "Get authentication settings")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Authentication settings retrieved")})
    @GetMapping(path = "/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
    AuthenticationSettingsDto getAuthenticationSettings();

    @Operation(summary = "Update authentication settings")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Authentication settings updated")})
    @PutMapping(path = "/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
    void updateAuthenticationSettings(@RequestBody AuthenticationSettingsUpdateDto authenticationSettingsDto);

    @Operation(summary = "Get OAuth2 Provider settings")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OAuth2 Provider settings retrieved")})
    @GetMapping(path = "/authentication/oauth2Providers/{providerName}", produces = MediaType.APPLICATION_JSON_VALUE)
    OAuth2ProviderSettingsDto getOAuth2ProviderSettings(@Parameter(description = "OAuth2 Provider Name") @PathVariable String providerName);

    @Operation(summary = "Update OAuth2 Provider settings")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Setting updated")})
    @PutMapping(path = "/authentication/oauth2Providers/{providerName}", consumes = MediaType.APPLICATION_JSON_VALUE)
    void updateOAuth2ProviderSettings(@Parameter(description = "OAuth2 Provider Name") @PathVariable String providerName, @Valid @RequestBody OAuth2ProviderSettingsUpdateDto oauth2SettingsDto);

    @Operation(summary = "Remove OAuth2 Provider")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "OAuth2 Provider removed.")})
    @DeleteMapping(path = "/authentication/oauth2Providers/{providerName}")
    void removeOAuth2Provider(@Parameter(description = "OAuth2 Provider Name") @PathVariable String providerName);

    @Operation(summary = "Get logging settings")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Logging settings retrieved")})
    @GetMapping(path = "/logging", produces = MediaType.APPLICATION_JSON_VALUE)
    LoggingSettingsDto getLoggingSettings();

    @Operation(summary = "Update logging setting")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Logging settings updated")})
    @PutMapping(path = "/logging", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    void updateLoggingSettings(@RequestBody LoggingSettingsDto loggingSettingsDto);
}
