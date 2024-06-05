package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.*;
import com.czertainly.api.model.client.cmp.CmpProfileEditRequestDto;
import com.czertainly.api.model.client.cmp.CmpProfileRequestDto;
import com.czertainly.api.model.client.cmp.validation.ValidUuid;
import com.czertainly.api.model.client.cmp.validation.ValidUuidList;
import com.czertainly.api.model.common.AuthenticationServiceExceptionDto;
import com.czertainly.api.model.common.BulkActionMessageDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.core.certificate.CertificateDto;
import com.czertainly.api.model.core.cmp.CmpProfileDetailDto;
import com.czertainly.api.model.core.cmp.CmpProfileDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/cmpProfiles")
@Tag(name = "CMP Profile Management", description = "CMP Profile Management API")
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
@Validated
public interface CmpProfileController {

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------
    // List and Detail
    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @Operation(
            summary = "Get list of CMP Profiles"
    )
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "CMP Profile list retrieved")}
    )
    @RequestMapping(
            produces = {"application/json"},
            method = RequestMethod.GET
    )
    List<CmpProfileDto> listCmpProfiles();


    @Operation(
            summary = "Get details of CMP Profile"
    )
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "CMP Profile details retrieved") }
    )
    @RequestMapping(
            path = "/{cmpProfileUuid}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    CmpProfileDetailDto getCmpProfile(
            @Parameter(description = "CMP Profile UUID") @PathVariable @ValidUuid String cmpProfileUuid
    ) throws NotFoundException;

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------
    // Create and Update
    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @Operation(
            summary = "Create CMP Profile"
    )
    @ApiResponses(
            value = { @ApiResponse(responseCode = "201", description = "CMP Profile created") }
    )
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = { "application/json" },
            produces = { "application/json" }
    )
    ResponseEntity<CmpProfileDetailDto> createCmpProfile(
            @Valid @RequestBody CmpProfileRequestDto request
    ) throws AlreadyExistException, ValidationException, ConnectorException, AttributeException;


    @Operation(
            summary = "Edit CMP Profile"
    )
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "CMP Profile updated") }
    )
    @RequestMapping(
            path="/{cmpProfileUuid}",
            method = RequestMethod.PUT,
            consumes = { "application/json" },
            produces = { "application/json" }
    )
    CmpProfileDetailDto editCmpProfile(
            @Parameter(description = "CMP Profile UUID") @PathVariable @ValidUuid String cmpProfileUuid,
            @Valid @RequestBody CmpProfileEditRequestDto request
    ) throws ConnectorException, AttributeException;

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------
    // Delete
    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @Operation(
            summary = "Delete CMP Profile"
    )
    @ApiResponses(
            value = { @ApiResponse(responseCode = "204", description = "CMP Profile deleted") }
    )
    @RequestMapping(
            path="/{cmpProfileUuid}",
            method = RequestMethod.DELETE,
            produces = { "application/json" }
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCmpProfile(
            @Parameter(description = "CMP Profile UUID") @PathVariable @ValidUuid String cmpProfileUuid
    ) throws NotFoundException, ValidationException;


    @Operation(
            summary = "Delete multiple CMP Profiles"
    )
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "CMP Profiles deleted") }
    )
    @RequestMapping(
            path = "/delete",
            method = RequestMethod.DELETE,
            consumes = { "application/json" },
            produces = { "application/json" }
    )
    List<BulkActionMessageDto> bulkDeleteCmpProfile(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "CMP Profile UUIDs",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = String.class)),
                            examples={@ExampleObject(value="[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}
                    )
            ) @RequestBody @ValidUuidList List<String> cmpProfileUuids
    );

    @Operation(
            summary = "Force delete multiple CMP Profiles"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CMP Profiles forced to delete"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = String.class)),
                            examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}
                    )
            )
    })
    @RequestMapping(
            path = "/delete/force",
            method = RequestMethod.DELETE,
            produces = {"application/json"}
    )
    List<BulkActionMessageDto> forceDeleteCmpProfiles(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "CMP Profile UUIDs",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = String.class)),
                            examples={@ExampleObject(value="[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}
                    )
            ) @RequestBody @ValidUuidList List<String> cmpProfileUuids
    ) throws NotFoundException, ValidationException;


    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------
    // Enable
    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @Operation(
            summary = "Enable CMP Profile"
    )
    @ApiResponses(
            value = { @ApiResponse(responseCode = "204", description = "CMP Profile enabled") }
    )
    @RequestMapping(
            path = "/{cmpProfileUuid}/enable",
            method = RequestMethod.PATCH,
            produces = { "application/json" }
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void enableCmpProfile(
            @Parameter(description = "CMP Profile UUID") @PathVariable @ValidUuid String cmpProfileUuid
    ) throws NotFoundException;


    @Operation(
            summary = "Enable multiple CMP Profiles"
    )
    @ApiResponses(
            value = { @ApiResponse(responseCode = "204", description = "CMP Profiles enabled") }
    )
    @RequestMapping(
            path = "/enable",
            method = RequestMethod.PATCH,
            consumes = { "application/json" },
            produces = { "application/json" }
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void bulkEnableCmpProfile(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "CMP Profile UUIDs",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = String.class)),
                            examples={@ExampleObject(value="[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}
                    )
            ) @RequestBody @ValidUuidList List<String> cmpProfileUuids
    );

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------
    // Disable
    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @Operation(
            summary = "Disable CMP Profile"
    )
    @ApiResponses(
            value = { @ApiResponse(responseCode = "204", description = "CMP Profile disabled") }
    )
    @RequestMapping(
            path = "/{cmpProfileUuid}/disable",
            method = RequestMethod.PATCH,
            produces = { "application/json" }
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void disableCmpProfile(
            @Parameter(description = "CMP Profile UUID") @PathVariable @ValidUuid String cmpProfileUuid
    ) throws NotFoundException;


    @Operation(
            summary = "Disable multiple CMP Profile"
    )
    @ApiResponses(
            value = { @ApiResponse(responseCode = "204", description = "CMP Profiles disabled") }
    )
    @RequestMapping(
            path = "/disable",
            method = RequestMethod.PATCH,
            consumes = { "application/json" },
            produces = { "application/json" }
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void bulkDisableCmpProfile(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "CMP Profile UUIDs",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = String.class)),
                            examples={@ExampleObject(value="[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}
                    )
            ) @RequestBody @ValidUuidList List<String> cmpProfileUuids
    );

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------
    // RA profile Assignment
    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @Operation(
            summary = "Update RA Profile for CMP Profile"
    )
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "RA Profile updated") }
    )
    @RequestMapping(
            path = "/{cmpProfileUuid}/raProfiles/{raProfileUuid}",
            method = RequestMethod.PATCH,
            produces = { "application/json" }
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateRaProfile(
            @Parameter(description = "CMP Profile UUID") @PathVariable @ValidUuid String cmpProfileUuid,
            @Parameter(description = "RA Profile UUID") @PathVariable @ValidUuid String raProfileUuid
    ) throws NotFoundException;

    @Operation(
            summary = "Get list of certificates eligible for signing of CMP responses"
    )
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "List of signing certificates retrieved") }
    )
    @RequestMapping(
            path = "/signingCertificates",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<CertificateDto> listCmpSigningCertificates();
}
