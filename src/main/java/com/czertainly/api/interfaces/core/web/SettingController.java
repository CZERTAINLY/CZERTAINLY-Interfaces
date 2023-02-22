package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import com.czertainly.api.model.common.AuthenticationServiceExceptionDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.attribute.v2.BaseAttribute;
import com.czertainly.api.model.core.setting.Section;
import com.czertainly.api.model.core.setting.SectionDto;
import com.czertainly.api.model.core.setting.SectionSettingsDto;
import com.czertainly.api.model.core.setting.AllSettingsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
            summary = "Get settings sections"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Settings sections retrieved")
            })
    @RequestMapping(
            path = "/sections",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<SectionDto> getSettingsSections();

    @Operation(
            summary = "Get all settings extracted from attributes in dedicated DTO"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Settings details retrieved")
            })
    @RequestMapping(
            path = "/all",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    AllSettingsDto getAllSettings();

    @Operation(
            summary = "Get sections settings"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sections settings details retrieved")
            })
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<SectionSettingsDto> getSettings();

    @Operation(
            summary = "Get section settings definitions"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Section settings definitions retrieved")
            })
    @RequestMapping(
            path = "/sections/{section}/attributes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<BaseAttribute> getSectionSettingsAttributes(
            @Parameter(description = "Section type") @PathVariable Section section
    ) throws NotFoundException;

    @Operation(
            summary = "Get section settings details"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Setting details retrieved")
            })
    @RequestMapping(
            path = "/sections/{section}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    SectionSettingsDto getSectionSettings(
            @Parameter(description = "Section type") @PathVariable Section section
    ) throws NotFoundException;

    @Operation(
            summary = "Update setting"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Setting updated")
            })
    @RequestMapping(
            path = "/sections/{section}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    SectionSettingsDto updateSectionSettings(
            @Parameter(description = "Section type") @PathVariable Section section,
            @RequestBody List<RequestAttributeDto> attributes
    );
}
