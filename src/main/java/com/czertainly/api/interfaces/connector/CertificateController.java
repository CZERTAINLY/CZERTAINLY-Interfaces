package com.czertainly.api.interfaces.connector;

import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.model.core.authority.CertRevocationDto;
import com.czertainly.api.model.core.authority.CertificateSignRequestDto;
import com.czertainly.api.model.core.authority.CertificateSignResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/authorityProvider/authorities/{uuid}/endEntityProfiles/{endEntityProfileName}/certificates")
@ApiResponses(
        value = {
                @ApiResponse(
                        responseCode = "400",
                        description = "Bad Request",
                        content = @Content
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "Not Found",
                        content = @Content
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal Server Error",
                        content = @Content
                )
        })
@Tag(name = "Certificate Management API", description = "Certificate Management API")
public interface CertificateController {

    @Operation(
            summary = "Issue Certificate"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Certificate issued"
                    )
            })
    @RequestMapping(path = "/issue", method = RequestMethod.POST)
    CertificateSignResponseDto issueCertificate(
            @Parameter(description = "Authority Instance UUID") @PathVariable String uuid,
            @Parameter(description = "End Entity Profile Name") @PathVariable String endEntityProfileName,
            @RequestBody CertificateSignRequestDto request) throws NotFoundException;

    @Operation(
            summary = "Revoke Certificate"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Certificate revoked"
                    )
            })
    @RequestMapping(path = "/revoke", method = RequestMethod.POST)
    void revokeCertificate(
            @Parameter(description = "Authority Instance UUID") @PathVariable String uuid,
            @Parameter(description = "End Entity Profile Name") @PathVariable String endEntityProfileName,
            @RequestBody CertRevocationDto request) throws NotFoundException;

}