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
@RequestMapping("/v2/authorityProvider/authorities/{uuid}/certificates")
@Tag(name = "Certificate Management API", description = "Certificate Management API")
public interface CertificateController {

    @Operation(
            summary = "List issue Certificate Attributes",
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
            @PathVariable String uuid) throws NotFoundException;

    @Operation(
            summary = "Validate issue Certificate Attributes",
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
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Unprocessable Entity",
                            content = @Content(schema = @Schema(implementation = ValidationException.class)
                    ))
            })
    @RequestMapping(path = "/issue/attributes/validate", method = RequestMethod.POST)
    boolean validateIssueCertificateAttributes(
            @PathVariable String uuid,
            @RequestBody List<AttributeDefinition> attributes) throws NotFoundException, ValidationException;

    @Operation(
            summary = "Issue Certificate",
            description = "Method for issuing Certificates " +
                    "based on given Attributes."
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
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Unprocessable Entity",
                            content = @Content(schema = @Schema(implementation = ValidationException.class)
                            ))
            })
    @RequestMapping(path = "/issue", method = RequestMethod.POST)
    CertificateDataResponseDto issueCertificate(
            @PathVariable String uuid,
            @RequestBody CertificateSignRequestDto request) throws NotFoundException;

    @Operation(
            summary = "Renew Certificate",
            description = "Method for renewing existing Certificates " +
                    "by given Certificate identifier. Certificate identifier value depends on" +
                    "implementation of Authority, which data it needs to uniquely identify Certificate"
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
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Unprocessable Entity",
                            content = @Content(schema = @Schema(implementation = ValidationException.class)
                            ))
            })
    @RequestMapping(path = "/{certificateId}/renew", method = RequestMethod.POST)
    CertificateDataResponseDto renewCertificate(
            @PathVariable String uuid,
            @PathVariable String certificateId,
            @RequestBody CertificateRenewRequestDto request) throws NotFoundException;

    @Operation(
            summary = "List revoke Certificate Attributes",
            description = "Method for listing of Attributes " +
                    "needed to revoke Certificate."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Attribute list retrieved"
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Unprocessable Entity",
                            content = @Content(schema = @Schema(implementation = ValidationException.class)
                            ))
            })
    @RequestMapping(path = "/revoke/attributes", method = RequestMethod.GET)
    List<AttributeDefinition> listRevokeCertificateAttributes(
            @PathVariable String uuid) throws NotFoundException;

    @Operation(
            summary = "Validate revoke Certificate Attributes",
            description = "Method for validation of Attributes " +
                    "needed to revoke or sign Certificate."
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
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Unprocessable Entity",
                            content = @Content(schema = @Schema(implementation = ValidationException.class)
                            ))
            })
    @RequestMapping(path = "/revoke/attributes/validate", method = RequestMethod.POST)
    boolean validateRevokeCertificateAttributes(
            @PathVariable String uuid,
            @RequestBody List<AttributeDefinition> attributes) throws NotFoundException, ValidationException;

    @Operation(
            summary = "Revoke Certificate",
            description = "Method for revoking existing Certificates " +
                    "by given Certificate identifier. Certificate identifier value depends on" +
                    "implementation of Authority, which data it needs to uniquely identify Certificate"
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
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Unprocessable Entity",
                            content = @Content(schema = @Schema(implementation = ValidationException.class)
                            ))
            })
    @RequestMapping(path = "/{certificateId}/revoke", method = RequestMethod.POST)
    void revokeCertificate(
            @PathVariable String uuid,
            @PathVariable String certificateId,
            @RequestBody CertRevocationDto request) throws NotFoundException;
}
