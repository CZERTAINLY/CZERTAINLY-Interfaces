package com.czertainly.api.interfaces.connector;

import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.NameAndIdDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/authorityProvider/authorities/{uuid}/endEntityProfiles")
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
@Tag(name = "End Entity Profiles", description = "End Entity Profiles API")
public interface EndEntityProfilesController {

    @RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
    @Operation(
            summary = "List available end entity profiles"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "End entities retrieved")
    })
    List<NameAndIdDto> listEntityProfiles(
            @Parameter(in = ParameterIn.PATH, name = "uuid", description = "Authority Instance UUID")
            @PathVariable String uuid
    )
            throws NotFoundException;

    @RequestMapping(path = "/{endEntityProfileId}/certificateprofiles", method = RequestMethod.GET, produces = {"application/json"})
    @Operation(
            summary = "List available certificate profiles for given end entity profile"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Certificate profiles retrieved")
    })
    List<NameAndIdDto> listCertificateProfiles(
            @Parameter(in = ParameterIn.PATH, name = "uuid", description = "Authority Instance UUID")
            @PathVariable String uuid,
            @Parameter(in = ParameterIn.PATH, name = "endEntityProfileId", description = "End Entity Profile Id")
            @PathVariable Integer endEntityProfileId
    )
            throws NotFoundException;

    @RequestMapping(path = "/{endEntityProfileId}/cas", method = RequestMethod.GET, produces = {"application/json"})
    @Operation(
            summary = "List available certification authorities for given end entity profile"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CAs retrieved")
    })
    List<NameAndIdDto> listCAsInProfile(
            @Parameter(in = ParameterIn.PATH, name = "uuid", description = "Authority Instance UUID")
            @PathVariable String uuid,
            @Parameter(in = ParameterIn.PATH, name = "endEntityProfileId", description = "End Entity Profile Id")
            @PathVariable Integer endEntityProfileId
    )
            throws NotFoundException;

}