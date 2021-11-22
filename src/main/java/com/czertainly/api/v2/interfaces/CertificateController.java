package com.czertainly.api.v2.interfaces;

import com.czertainly.api.model.ErrorMessageDto;
import com.czertainly.api.v2.model.ca.CertRevocationDto;
import com.czertainly.api.v2.model.ca.CertificateDataResponseDto;
import com.czertainly.api.v2.model.ca.CertificateRenewRequestDto;
import com.czertainly.api.v2.model.ca.CertificateSignRequestDto;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.AttributeDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v2/caConnector/authorities/{authorityId}/certificates")
@Tag(name = "Certificate Management API", description = "Certificate Management API")
public interface CertificateController {

    @Operation(
            summary = "List issue certificate attributes",
            description = "Method for listing of attributes " +
                    "needed to issue or sign certificate."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Attribute list retrieved"
                    )
    })
    @RequestMapping(path = "/issue/attributes", method = RequestMethod.GET)
    List<AttributeDefinition> listIssueCertificateAttributes(
            @PathVariable Long authorityId) throws NotFoundException;

    @Operation(
            summary = "Validate issue certificate attributes",
            description = "Method for validation of attributes " +
                    "needed to issue or sign certificate."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Attributes validated"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content
                    )
            })
    @RequestMapping(path = "/issue/attributes/validate", method = RequestMethod.POST)
    boolean validateIssueCertificateAttributes(
            @PathVariable Long authorityId,
            @RequestBody List<AttributeDefinition> attributes) throws NotFoundException, ValidationException;

    @Operation(
            summary = "Issue certificate",
            description = "Method for issuing certificates " +
                    "based on given attributes."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Certificate issued"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = @Content
                    )
            })
    @RequestMapping(path = "/issue", method = RequestMethod.POST)
    CertificateDataResponseDto issueCertificate(
            @PathVariable Long authorityId,
            @RequestBody CertificateSignRequestDto request) throws NotFoundException;

    @Operation(
            summary = "Renew certificate",
            description = "Method for renewing existing certificates " +
                    "by given certificate identifier. Certificate identifier value depends on" +
                    "implementation of CA instance, which data it needs to uniquely identify certificate"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Certificate renewed"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = @Content
                    )
            })
    @RequestMapping(path = "/{certificateId}/renew", method = RequestMethod.POST)
    CertificateDataResponseDto renewCertificate(
            @PathVariable Long authorityId,
            @PathVariable String certificateId,
            @RequestBody CertificateRenewRequestDto request) throws NotFoundException;

    @Operation(
            summary = "List revoke certificate attributes",
            description = "Method for listing of attributes " +
                    "needed to revoke certificate."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Attribute list retrieved"
                    )
            })
    @RequestMapping(path = "/revoke/attributes", method = RequestMethod.GET)
    List<AttributeDefinition> listRevokeCertificateAttributes(
            @PathVariable Long authorityId) throws NotFoundException;

    @Operation(
            summary = "Validate revoke certificate attributes",
            description = "Method for validation of attributes " +
                    "needed to revoke or sign certificate."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Attributes validated"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content
                    )
            })
    @RequestMapping(path = "/revoke/attributes/validate", method = RequestMethod.POST)
    boolean validateRevokeCertificateAttributes(
            @PathVariable Long authorityId,
            @RequestBody List<AttributeDefinition> attributes) throws NotFoundException, ValidationException;

    @Operation(
            summary = "Revoke certificate",
            description = "Method for revoking existing certificates " +
                    "by given certificate identifier. Certificate identifier value depends on" +
                    "implementation of CA instance, which data it needs to uniquely identify certificate"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Certificate revoked"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = @Content
                    )
            })
    @RequestMapping(path = "/{certificateId}/revoke", method = RequestMethod.POST)
    void revokeCertificate(
            @PathVariable Long authorityId,
            @PathVariable String certificateId,
            @RequestBody CertRevocationDto request) throws NotFoundException;
}
