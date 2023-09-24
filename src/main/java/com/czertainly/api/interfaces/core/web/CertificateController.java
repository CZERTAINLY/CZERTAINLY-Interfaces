package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.client.approval.ApprovalResponseDto;
import com.czertainly.api.model.client.certificate.*;
import com.czertainly.api.model.common.AuthenticationServiceExceptionDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.UuidDto;
import com.czertainly.api.model.common.attribute.v2.BaseAttribute;
import com.czertainly.api.model.core.certificate.*;
import com.czertainly.api.model.core.location.LocationDto;
import com.czertainly.api.model.core.scheduler.PaginationRequestDto;
import com.czertainly.api.model.core.search.SearchFieldDataByGroupDto;
import com.czertainly.api.model.core.v2.ClientCertificateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/certificates")
@Tag(name = "Certificate Inventory", description = "Certificate Inventory API")
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
public interface CertificateController {

    @Operation(summary = "List Certificates")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of all the certificates")})
    @RequestMapping(method = RequestMethod.POST, produces = {"application/json"})
    CertificateResponseDto listCertificates(@RequestBody SearchRequestDto request) throws ValidationException;

    @Operation(summary = "Get Certificate Details")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Certificate detail retrieved")})
    @RequestMapping(path = "/{uuid}", method = RequestMethod.GET, produces = {"application/json"})
    CertificateDetailDto getCertificate(@Parameter(description = "Certificate UUID") @PathVariable String uuid)
            throws NotFoundException, CertificateException, IOException;

    @Operation(summary = "Delete a certificate")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Certificate deleted")})
    @RequestMapping(path = "/{uuid}", method = RequestMethod.DELETE, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCertificate(@Parameter(description = "Certificate UUID") @PathVariable String uuid) throws NotFoundException;

    //TODO - Merge the DTO and update implementation
    @Operation(summary = "Update Certificate Objects")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Certificate objects updated")})
    @RequestMapping(path = "/{uuid}", method = RequestMethod.PATCH, consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateCertificateObjects(@Parameter(description = "Certificate UUID") @PathVariable String uuid, @RequestBody CertificateUpdateObjectsDto request)
            throws NotFoundException;

    @Operation(summary = "Initiate Certificate validation")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Certificate validation initiated")})
    @RequestMapping(method = RequestMethod.PUT, path = "/{uuid}/validate", produces = {"application/json"})
    void check(@Parameter(description = "Certificate UUID") @PathVariable String uuid)
            throws CertificateException, IOException, NotFoundException;

    @Operation(summary = "Update RA Profile, Group, Owner for multiple Certificates", description = "In this operation, when the list of " +
            "Certificate UUIDs are provided and the filter is left as null or undefined, then the change will " +
            "be applied only to the list of Certificate UUIDs provided. When the filter is provided in the request, " +
            "the list of UUIDs will be ignored and the change will be applied for the all the certificates that matches " +
            "the filter criteria. To apply this change for all the Certificates in the inventory, " +
            "provide an empty array \"[]\" for the value of \"filters\" in the request body")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Certificate objects updated")})
    @RequestMapping(method = RequestMethod.PATCH, consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void bulkUpdateCertificateObjects(@RequestBody MultipleCertificateObjectUpdateDto request)
            throws NotFoundException;

    @Operation(summary = "Upload a new Certificate")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Certificate uploaded", content = @Content(schema = @Schema(implementation = UuidDto.class)))})
    @RequestMapping(path = "/upload", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    ResponseEntity<UuidDto> upload(@RequestBody UploadCertificateRequestDto request)
            throws AlreadyExistException, CertificateException, NoSuchAlgorithmException;

    @Operation(summary = "Delete multiple certificates", description = "In this operation, when the list of " +
            "Certificate UUIDs are provided and the filter is left as null or undefined, then the change will " +
            "be applied only to the list of Certificate UUIDs provided. When the filter is provided in the request, " +
            "the list of UUIDs will be ignored and the change will be applied for the all the certificates that matches " +
            "the filter criteria. To apply this change for all the Certificates in the inventory, " +
            "provide an empty array \"[]\" for the value of \"filters\" in the request body")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Certificates deleted")})
    @RequestMapping(path = "/delete", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    BulkOperationResponse bulkDeleteCertificate(@RequestBody RemoveCertificateDto request) throws NotFoundException;

    @Operation(summary = "Get Certificate searchable fields information")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Certificate searchable field information retrieved")})
    @RequestMapping(path = "/search", method = RequestMethod.GET, produces = {"application/json"})
    List<SearchFieldDataByGroupDto> getSearchableFieldInformation();

    @Operation(summary = "Get Certificate event history")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Certificate event history retrieved")})
    @RequestMapping(path = "/{uuid}/history", method = RequestMethod.GET, produces = {"application/json"})
    List<CertificateEventHistoryDto> getCertificateEventHistory(@Parameter(description = "Certificate UUID") @PathVariable String uuid) throws NotFoundException;

    @Operation(
            summary = "List of available Locations for the Certificate",
            operationId = "listCertificateLocations"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Locations retrieved"
                    )
            })
    @RequestMapping(
            method = RequestMethod.GET,
            path = "/{certificateUuid}/locations",
            produces = {"application/json"}
    )
    List<LocationDto> listLocations(
            @Parameter(description = "Certificate UUID") @PathVariable String certificateUuid
    ) throws NotFoundException;

    @Operation(summary = "Initiate Certificate Compliance Check", operationId = "checkCertificatesCompliance")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Compliance check initiated")})
    @RequestMapping(path = "/compliance", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void checkCompliance(@RequestBody CertificateComplianceCheckDto request) throws NotFoundException;

    @Operation(summary = "Get Certificate Validation Result")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Certificate validation detail retrieved")})
    @RequestMapping(path = "/{uuid}/validate", method = RequestMethod.GET, produces = {"application/json"})
    Map<String, CertificateValidationDto> getCertificateValidationResult(@Parameter(description = "Certificate UUID") @PathVariable String uuid)
            throws NotFoundException, CertificateException, IOException;

    @Operation(summary = "Get CSR Generation Attributes")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "CSR Generation attributes retrieved")})
    @RequestMapping(path = "/csr/attributes", method = RequestMethod.GET, produces = {"application/json"})
    List<BaseAttribute> getCsrGenerationAttributes();

    @Operation(summary = "Get Certificate Content")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Certificate content retrieved"),
            @ApiResponse(responseCode = "422", description = "Unprocessible Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @RequestMapping(path = "/content", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    List<CertificateContentDto> getCertificateContent(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Certificate UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
                                                      @RequestBody List<String> uuids);

    @Operation(
            summary = "Submit certificate request"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Certificate request submit, certificate created and ready to be issued")
    })
    @RequestMapping(
            path = "/create",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    CertificateDetailDto submitCertificateRequest(
            @RequestBody ClientCertificateRequestDto request
    ) throws ValidationException, NotFoundException, CertificateException, IOException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException;
    
    @Operation(
            summary = "Get certificate chain",
            description = "Get certificate chain for the certificate with the given UUID. " +
                    "The certificate chain is returned in the order of the chain, with the first certificate " +
                    "being the certificate with the given UUID, up to the last identified certificate in the chain. " +
                    "If the certificate with the given UUID has status NEW or REJECTED, an empty list is returned."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Certificate chain retrieved")
    })
    @RequestMapping(
            path = "/{uuid}/chain",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<CertificateDto> getCertificateChain(@Parameter(description = "Certificate UUID") @PathVariable String uuid) throws NotFoundException;


    @Operation(summary = "List Certificates Approvals")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of all approvals for the certificate")})
    @RequestMapping(method = RequestMethod.GET, path = "/{uuid}/approvals", produces = {"application/json"})
    ApprovalResponseDto listCertificateApprovals(@Parameter(description = "Certificate UUID") @PathVariable String uuid, final PaginationRequestDto paginationRequestDto);

}
