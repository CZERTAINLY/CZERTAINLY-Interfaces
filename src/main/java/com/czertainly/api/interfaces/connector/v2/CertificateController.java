package com.czertainly.api.interfaces.connector.v2;

import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.common.AttributeDefinition;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.RequestAttributeDto;
import com.czertainly.api.model.connector.v2.CertRevocationDto;
import com.czertainly.api.model.connector.v2.CertificateDataResponseDto;
import com.czertainly.api.model.connector.v2.CertificateRenewRequestDto;
import com.czertainly.api.model.connector.v2.CertificateSignRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v2/authorityProvider/authorities/{uuid}/certificates")
@Tag(name = "Certificate Management API", description = "Certificate Management API")
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

public interface CertificateController {

    @Operation(
            summary = "List of Attributes to issue Certificate"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Attribute list retrieved"
                    )
    })
    @RequestMapping(path = "/issue/attributes", method = RequestMethod.GET, produces = {"application/json"})
    List<AttributeDefinition> listIssueCertificateAttributes(
            @Parameter(description = "Authority Instance UUID") @PathVariable String uuid) throws NotFoundException;

    @Operation(
            summary = "Validate list of Attributes to issue Certificate"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Attributes validated"
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Unprocessable Entity",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                                    examples={@ExampleObject(value="[\"Error Message 1\",\"Error Message 2\"]")}
                    ))
            })
    @RequestMapping(path = "/issue/attributes/validate", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    void validateIssueCertificateAttributes(
            @Parameter(description = "Authority Instance UUID") @PathVariable String uuid,
            @RequestBody List<RequestAttributeDto> attributes) throws NotFoundException, ValidationException;

    @Operation(
            summary = "Issue Certificate"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Certificate issued"
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Unprocessable Entity",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                                    examples={@ExampleObject(value="[\"Error Message 1\",\"Error Message 2\"]")}
                            ))
            })
    @RequestMapping(path = "/issue", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    CertificateDataResponseDto issueCertificate(
            @Parameter(description = "Authority Instance UUID") @PathVariable String uuid,
            @RequestBody CertificateSignRequestDto request) throws NotFoundException;

    @Operation(
            summary = "Renew Certificate"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Certificate renewed"
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Unprocessable Entity",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                                    examples={@ExampleObject(value="[\"Error Message 1\",\"Error Message 2\"]")}
                            ))
            })
    @RequestMapping(path = "/renew", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    CertificateDataResponseDto renewCertificate(
            @Parameter(description = "Authority Instance UUID") @PathVariable String uuid,
            @RequestBody CertificateRenewRequestDto request) throws NotFoundException;

    @Operation(
            summary = "List of Attributes to revoke Certificate"
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
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                                    examples={@ExampleObject(value="[\"Error Message 1\",\"Error Message 2\"]")}
                            ))
            })
    @RequestMapping(path = "/revoke/attributes", method = RequestMethod.GET, produces = {"application/json"})
    List<AttributeDefinition> listRevokeCertificateAttributes(
            @Parameter(description = "Authority Instance UUID") @PathVariable String uuid) throws NotFoundException;

    @Operation(
            summary = "Validate list of Attributes to revoke certificate"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Attributes validated"
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Unprocessable Entity",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                                    examples={@ExampleObject(value="[\"Error Message 1\",\"Error Message 2\"]")}
                            ))
            })
    @RequestMapping(path = "/revoke/attributes/validate", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    void validateRevokeCertificateAttributes(
            @Parameter(description = "Authority Instance UUID") @PathVariable String uuid,
            @RequestBody List<RequestAttributeDto> attributes) throws NotFoundException, ValidationException;

    @Operation(
            summary = "Revoke Certificate"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Certificate revoked"
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Unprocessable Entity",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                                    examples={@ExampleObject(value="[\"Error Message 1\",\"Error Message 2\"]")}
                            ))
            })
    @RequestMapping(path = "/revoke", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    void revokeCertificate(
            @Parameter(description = "Authority Instance UUID") @PathVariable String uuid,
            @RequestBody CertRevocationDto request) throws NotFoundException;
}
