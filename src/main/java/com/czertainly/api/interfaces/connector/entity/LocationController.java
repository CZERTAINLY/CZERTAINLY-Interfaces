package com.czertainly.api.interfaces.connector.entity;

import com.czertainly.api.exception.LocationException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.attribute.BaseAttribute;
import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import com.czertainly.api.model.connector.entity.GenerateCsrRequestDto;
import com.czertainly.api.model.connector.entity.GenerateCsrResponseDto;
import com.czertainly.api.model.connector.entity.LocationDetailRequestDto;
import com.czertainly.api.model.connector.entity.LocationDetailResponseDto;
import com.czertainly.api.model.connector.entity.PushCertificateRequestDto;
import com.czertainly.api.model.connector.entity.PushCertificateResponseDto;
import com.czertainly.api.model.connector.entity.RemoveCertificateRequestDto;
import com.czertainly.api.model.connector.entity.RemoveCertificateResponseDto;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/entityProvider/entities/{entityUuid}/locations")
@Tag(
        name = "Location Operations API",
        description = "Interfaces to control certificates and key stores on Entities. " +
                "Locations provides capabilities of getting the certificates already on the Entity, " +
                "pushing new certificates, generation of new key pair and certificate signing requests, " +
                "removing certificates and management of the Entity end-to-end automation."
)
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
public interface LocationController {

    @Operation(
            summary = "Get Location Details",
            description = "Get information about the Location content. All identified certificates are returned"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Location detail and content retrieved"
                    )
            })
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    LocationDetailResponseDto getLocationDetail(
            @Parameter(description = "Entity instance UUID") @PathVariable String entityUuid,
            @RequestBody LocationDetailRequestDto request
    ) throws NotFoundException, LocationException;

    @Operation(
            summary = "Push Certificate",
            description = "Push the Certificate into the Location"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Certificate pushed to Location"
                    )
            })
    @RequestMapping(
            path = "/push",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    PushCertificateResponseDto pushCertificateToLocation(
            @Parameter(description = "Entity instance UUID") @PathVariable String entityUuid,
            @RequestBody PushCertificateRequestDto request
    ) throws NotFoundException, LocationException;

    @Operation(
            summary = "List Push Certificate Attributes",
            description = "List of Attributes to push Certificate into Location"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Attributes for push retrieved"
                    )
            })
    @RequestMapping(
            path = "/push/attributes",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    List<BaseAttribute> listPushCertificateAttributes(
            @Parameter(description = "Entity instance UUID") @PathVariable String entityUuid
    ) throws NotFoundException;

    @Operation(
            summary = "Validate Push Certificate Attributes",
            description = "Validate list of Attributes to push Certificate into Location"
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
    @RequestMapping(
            path = "/push/attributes/validate",
            method = RequestMethod.POST,
            consumes = {"application/json"}
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void validatePushCertificateAttributes(
            @Parameter(description = "Entity Instance UUID") @PathVariable String entityUuid,
            @RequestBody List<RequestAttributeDto> pushAttributes
    ) throws NotFoundException, ValidationException;

    @Operation(
            summary = "Remove Certificate",
            description = "Remove Certificate from Location"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Certificate removed"
                    )
            })
    @RequestMapping(
            path = "/remove",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    RemoveCertificateResponseDto removeCertificateFromLocation(
            @Parameter(description = "Entity instance UUID") @PathVariable String entityUuid,
            @RequestBody RemoveCertificateRequestDto request
    ) throws NotFoundException, LocationException;

    @Operation(
            summary = "Generate CSR",
            description = "Generate key pair and CSR for the Location"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "CSR generated"
                    )
            })
    @RequestMapping(
            path = "/csr",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    GenerateCsrResponseDto generateCsrLocation(
            @Parameter(description = "Entity instance UUID") @PathVariable String entityUuid,
            @RequestBody GenerateCsrRequestDto request
    ) throws NotFoundException, LocationException;

    @Operation(
            summary = "List CSR Attributes",
            description = "List of Attributes to generate key pair and CSR"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Attributes retrieved"
                    )
            })
    @RequestMapping(
            path = "/csr/attributes",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    List<BaseAttribute> listGenerateCsrAttributes(
            @Parameter(description = "Entity instance UUID") @PathVariable String entityUuid
    ) throws NotFoundException;

    @Operation(
            summary = "Validate CSR Attributes",
            description = "Validate list of Attributes to generate key pair and CSR"
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
    @RequestMapping(
            path = "/csr/attributes/validate",
            method = RequestMethod.POST,
            consumes = {"application/json"}
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void validateGenerateCsrAttributes(
            @Parameter(description = "Entity Instance UUID") @PathVariable String entityUuid,
            @RequestBody List<RequestAttributeDto> csrAttributes
    ) throws NotFoundException, ValidationException;
}
