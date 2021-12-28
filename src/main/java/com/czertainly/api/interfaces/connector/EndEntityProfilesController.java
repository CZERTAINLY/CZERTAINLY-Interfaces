package com.czertainly.api.interfaces.connector;

import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.model.common.NameAndIdDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
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
@Tag(name = "End Entity Profiles API", description = "End Entity Profiles API")
public interface EndEntityProfilesController {

    @RequestMapping(method = RequestMethod.GET)
    @Operation(
            summary = "List available end entity profiles"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "End entities retrieved"),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    List<NameAndIdDto> listEntityProfiles(
            @Parameter(in = ParameterIn.PATH, name = "uuid", description = "Authority Instance Id")
            @PathVariable String uuid
    )
            throws NotFoundException;

    @RequestMapping(path = "/{endEntityProfileId}/certificateprofiles", method = RequestMethod.GET)
    @Operation(
            summary = "List available certificate profiles for given end entity profile"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Certificate profiles retrieved"),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    List<NameAndIdDto> listCertificateProfiles(
            @Parameter(in = ParameterIn.PATH, name = "uuid", description = "Authority Instance Id")
            @PathVariable String uuid,
            @Parameter(in = ParameterIn.PATH, name = "endEntityProfileId", description = "End Entity Profile Id")
            @PathVariable Integer endEntityProfileId
    )
            throws NotFoundException;

    @RequestMapping(path = "/{endEntityProfileId}/cas", method = RequestMethod.GET)
    @Operation(
            summary = "List available certification authorities for given end entity profile"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CAs retrieved"),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    List<NameAndIdDto> listCAsInProfile(
            @Parameter(in = ParameterIn.PATH, name = "uuid", description = "Authority Instance Id")
            @PathVariable String uuid,
            @Parameter(in = ParameterIn.PATH, name = "endEntityProfileId", description = "End Entity Profile Id")
            @PathVariable Integer endEntityProfileId
    )
            throws NotFoundException;

}