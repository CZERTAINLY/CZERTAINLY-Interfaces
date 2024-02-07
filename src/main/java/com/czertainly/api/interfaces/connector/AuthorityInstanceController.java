package com.czertainly.api.interfaces.connector;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.attribute.v2.BaseAttribute;
import com.czertainly.api.model.connector.authority.AuthorityProviderInstanceDto;
import com.czertainly.api.model.connector.authority.AuthorityProviderInstanceRequestDto;
import com.czertainly.api.model.connector.authority.CertificateRevocationListRequestDto;
import com.czertainly.api.model.connector.authority.CertificateRevocationListResponseDto;
import com.czertainly.api.model.connector.authority.CaCertificatesRequestDto;
import com.czertainly.api.model.connector.authority.CaCertificatesResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/authorityProvider/authorities")
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
@Tag(name = "Authority Management", description = "Authority Management API")
public interface AuthorityInstanceController {

    @Operation(
            summary = "List Authority instances"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Authority instance list retrieved"
                    )
            })
    @RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
    List<AuthorityProviderInstanceDto> listAuthorityInstances();

    @Operation(
            summary = "Get an Authority instance"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Authority instance retrieved"
                    )
            })
    @RequestMapping(path = "/{uuid}", method = RequestMethod.GET, produces = {"application/json"})
    AuthorityProviderInstanceDto getAuthorityInstance(@Parameter(description = "Authority Instance UUID") @PathVariable String uuid) throws NotFoundException;

    @Operation(
            summary = "Create Authority instance"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Authority instance created"
                    )
            })
    @RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    AuthorityProviderInstanceDto createAuthorityInstance(@RequestBody AuthorityProviderInstanceRequestDto request) throws AlreadyExistException;

    @Operation(
            summary = "Update Authority instance"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Authority instance updated"
                    )
            })
    @RequestMapping(path = "/{uuid}", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    AuthorityProviderInstanceDto updateAuthorityInstance(@Parameter(description = "Authority Instance UUID") @PathVariable String uuid, @RequestBody AuthorityProviderInstanceRequestDto request) throws NotFoundException;

    @Operation(
            summary = "Remove Authority instance"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Authority instance removed"
                    )
            })
    @RequestMapping(path = "/{uuid}", method = RequestMethod.DELETE, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void removeAuthorityInstance(@Parameter(description = "Authority Instance UUID") @PathVariable String uuid) throws NotFoundException;

    @RequestMapping(path = "/{uuid}/connect", method = RequestMethod.GET, produces = {"application/json"})
    @Operation(
            summary = "Connect to Authority"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Authority instance connected"
                    )
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void getConnection(@Parameter(description = "Authority Instance UUID") @PathVariable String uuid) throws NotFoundException;

    @Operation(
            summary = "List RA Profile Attributes"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "RA Profile Attributes retrieved"
                    )
            })
    @RequestMapping(path = "/{uuid}/raProfile/attributes", method = RequestMethod.GET, produces = {"application/json"})
    List<BaseAttribute> listRAProfileAttributes(
            @Parameter(description = "Authority Instance UUID") @PathVariable String uuid) throws NotFoundException;

    @Operation(
            summary = "Validate RA Profile attributes"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "RA Profile Attributes information validated"
                    )
            })
    @RequestMapping(path = "/{uuid}/raProfile/attributes/validate", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    void validateRAProfileAttributes(
            @Parameter(description = "Authority Instance UUID") @PathVariable String uuid,
            @RequestBody List<RequestAttributeDto> attributes) throws ValidationException, NotFoundException;

    @Operation(
            summary = "Get the latest CRL for the Authority Instance",
            description = "Returns the latest CRL for the Authority Instance. " +
            "If delta is true, the delta CRL is returned, otherwise the full CRL is returned. " +
            "When the CRL is not available for Authority Instance, null data is returned."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "CRL retrieved"
                    )
            })
    @RequestMapping(
            path = "/{uuid}/crl",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    CertificateRevocationListResponseDto getCrl(
            @Parameter(description = "Authority Instance UUID") @PathVariable String uuid,
            @RequestBody CertificateRevocationListRequestDto request
    ) throws NotFoundException;
    
    @Operation(
            summary = "Get the Authority Instance's certificate chain",
            description = "Returns the Authority Instance's certificate chain. The chain is returned as a list of " +
                    "Base64 encoded certificates, starting with the Authority Instance's certificate " +
                    "and ending with the root certificate, if available."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Authority Instance's certificate chain retrieved"
                    )
            })
    @RequestMapping(
            path = "/{uuid}/caCertificates",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    CaCertificatesResponseDto getCaCertificates(
            @Parameter(description = "Authority Instance UUID") @PathVariable String uuid,
            @RequestBody CaCertificatesRequestDto raProfileAttributes
    ) throws ValidationException, NotFoundException;

}