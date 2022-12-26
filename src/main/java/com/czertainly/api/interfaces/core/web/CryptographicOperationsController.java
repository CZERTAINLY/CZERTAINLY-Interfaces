package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.CryptographicOperationException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.model.common.AuthenticationServiceExceptionDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.attribute.v2.BaseAttribute;
import com.czertainly.api.model.connector.cryptography.enums.CryptographicAlgorithm;
import com.czertainly.api.model.connector.cryptography.operations.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/cryptography/tokenInstances/{uuid}/keys")
@Tag(name = "Cryptographic Operations Controller", description = "Cryptographic Operations Controller API")
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
public interface CryptographicOperationsController {

    /////////////////////////////////////////////////////////////////////////////////
    // cipher operations
    /////////////////////////////////////////////////////////////////////////////////

    @Operation(
            summary = "List of cipher Attributes"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of Attributes retrieved"
                    )
            })
    @RequestMapping(
            path = "/cipher/{algorithm}/attributes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<BaseAttribute> listCipherAttributes(
            @Parameter(description = "Token instance UUID") @PathVariable String tokenInstanceUuid,
            @Parameter(description = "Cryptographic algorithm") @PathVariable CryptographicAlgorithm algorithm
    ) throws NotFoundException;

    @Operation(
            summary = "Encrypt data using a Key"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Data encrypted"
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Unprocessable Entity",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}
                            ))
            })
    @RequestMapping(
            path = "/encrypt",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    EncryptDataResponseDto encryptData(
            @Parameter(description = "Token instance UUID") @PathVariable String tokenInstanceUuid,
            @RequestBody CipherDataRequestDto request
    ) throws NotFoundException, CryptographicOperationException;

    @Operation(
            summary = "Decrypt data using a Key"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Data decrypted"
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Unprocessable Entity",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}
                            ))
            })
    @RequestMapping(
            path = "/decrypt",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    DecryptDataResponseDto decryptData(
            @Parameter(description = "Token instance UUID") @PathVariable String tokenInstanceUuid,
            @RequestBody CipherDataRequestDto request
    ) throws NotFoundException, CryptographicOperationException;

    /////////////////////////////////////////////////////////////////////////////////
    // signature operations
    /////////////////////////////////////////////////////////////////////////////////

    @Operation(
            summary = "List of signature Attributes"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of Attributes retrieved"
                    )
            })
    @RequestMapping(
            path = "/signature/{algorithm}/attributes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<BaseAttribute> listSignatureAttributes(
            @Parameter(description = "Token instance UUID") @PathVariable String tokenInstanceUuid,
            @Parameter(description = "Cryptographic algorithm") @PathVariable CryptographicAlgorithm algorithm
    ) throws NotFoundException;


    @Operation(
            summary = "Sign data using a Key"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Data signed"
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Unprocessable Entity",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}
                            ))
            })
    @RequestMapping(
            path = "/sign",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    SignDataResponseDto signData(
            @Parameter(description = "Token instance UUID") @PathVariable String tokenInstanceUuid,
            @RequestBody SignDataRequestDto request
    ) throws NotFoundException, CryptographicOperationException;

    @Operation(
            summary = "Verify data using a Key"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Data decrypted"
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Unprocessable Entity",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}
                            ))
            })
    @RequestMapping(
            path = "/verify",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    VerifyDataResponseDto verifyData(
            @Parameter(description = "Token instance UUID") @PathVariable String tokenInstanceUuid,
            @RequestBody VerifyDataRequestDto request
    ) throws NotFoundException, CryptographicOperationException;

    /////////////////////////////////////////////////////////////////////////////////
    // generate random operations
    /////////////////////////////////////////////////////////////////////////////////

    @Operation(
            summary = "List of random generator Attributes"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of Attributes retrieved"
                    )
            })
    @RequestMapping(
            path = "/random/attributes",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    List<BaseAttribute> listRandomAttributes(
            @Parameter(description = "Token instance UUID") @PathVariable String tokenInstanceUuid
    ) throws NotFoundException;


    @Operation(
            summary = "Generate random data"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Random data generated"
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Unprocessable Entity",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                                    examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}
                            ))
            })
    @RequestMapping(
            path = "/random",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    RandomDataResponseDto randomData(
            @Parameter(description = "Token instance UUID") @PathVariable String tokenInstanceUuid,
            @RequestBody RandomDataRequestDto request
    ) throws NotFoundException, CryptographicOperationException;
}
