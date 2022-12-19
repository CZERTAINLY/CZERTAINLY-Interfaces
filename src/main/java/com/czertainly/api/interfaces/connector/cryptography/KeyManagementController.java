package com.czertainly.api.interfaces.connector.cryptography;

import com.czertainly.api.exception.KeyManagementException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.attribute.v2.BaseAttribute;
import com.czertainly.api.model.connector.cryptography.key.CreateKeyRequestDto;
import com.czertainly.api.model.connector.cryptography.key.DestroyKeyRequestDto;
import com.czertainly.api.model.connector.cryptography.key.KeyDataResponseDto;
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
@RequestMapping("/v1/cryptographyProvider/tokens/{uuid}/keys")
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
@Tag(
        name = "Key Management",
        description = "Key Management API for cryptographic key management operations. Each key is managed by a specific token that is connected through the Token instance. The cryptographic operations are implemented independently by the Token Profile."
)
public interface KeyManagementController {

    @Operation(
            summary = "List of Attributes to create a Key"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of Attributes retrieved"
                    )
            })
    @RequestMapping(
            path = "/create/attributes",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    List<BaseAttribute> listCreateKeyAttributes(
            @Parameter(description = "Token instance UUID") @PathVariable String uuid
    ) throws NotFoundException;

    @Operation(
            summary = "Validate list of Attributes to create a Key"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204",
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
            path = "/issue/attributes/validate",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    void validateCreateKeyAttributes(
            @Parameter(description = "Token instance UUID") @PathVariable String uuid,
            @RequestBody List<RequestAttributeDto> attributes
    ) throws NotFoundException, ValidationException;

    @Operation(
            summary = "Create a Key"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Key created"
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Unprocessable Entity",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                                    examples={@ExampleObject(value="[\"Error Message 1\",\"Error Message 2\"]")}
                            ))
            })
    @RequestMapping(
            path = "/create",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    KeyDataResponseDto createKey(
            @Parameter(description = "Token instance UUID") @PathVariable String uuid,
            @RequestBody CreateKeyRequestDto request
    ) throws NotFoundException, KeyManagementException;

    @Operation(
            summary = "Destroy a Key"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Key destroyed"
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Unprocessable Entity",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                                    examples={@ExampleObject(value="[\"Error Message 1\",\"Error Message 2\"]")}
                            ))
            })
    @RequestMapping(
            path = "/destroy",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    void destroyKey(
            @Parameter(description = "Token instance UUID") @PathVariable String uuid,
            @RequestBody DestroyKeyRequestDto request
    ) throws NotFoundException, KeyManagementException;

}
