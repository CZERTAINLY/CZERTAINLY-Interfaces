package com.czertainly.api.interfaces.connector;

import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.interfaces.AuthProtectedConnectorController;
import com.czertainly.api.model.core.authority.CertRevocationDto;
import com.czertainly.api.model.core.authority.CertificateSignRequestDto;
import com.czertainly.api.model.core.authority.CertificateSignResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/authorityProvider/authorities/{uuid}/endEntityProfiles/{endEntityProfileName}/certificates")
@Tag(name = "Certificate Management", description = "Certificate Management API")
public interface CertificateController extends AuthProtectedConnectorController {

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
    @PostMapping(path = "/issue", consumes = {"application/json"}, produces = {"application/json"})
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
    @PostMapping(path = "/revoke", consumes = {"application/json"}, produces = {"application/json"})
    void revokeCertificate(
            @Parameter(description = "Authority Instance UUID") @PathVariable String uuid,
            @Parameter(description = "End Entity Profile Name") @PathVariable String endEntityProfileName,
            @RequestBody CertRevocationDto request) throws NotFoundException;

}