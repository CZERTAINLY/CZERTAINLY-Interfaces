package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.model.client.cryptography.operations.CipherDataRequestDto;
import com.czertainly.api.model.client.cryptography.operations.RandomDataRequestDto;
import com.czertainly.api.model.client.cryptography.operations.SignDataRequestDto;
import com.czertainly.api.model.client.cryptography.operations.VerifyDataRequestDto;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/operations/tokens/{tokenInstanceUuid}")
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
            path = "/tokenProfiles/{tokenProfileUuid}/keys/{uuid}/items/{keyItemUuid}/cipher/{algorithm}/attributes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<BaseAttribute> listCipherAttributes(
            @Parameter(description = "Token Instance UUID") @PathVariable String tokenInstanceUuid,
            @Parameter(description = "Token Profile UUID") @PathVariable String tokenProfileUuid,
            @Parameter(description = "Key UUID") @PathVariable String uuid,
            @Parameter(description = "Key Item UUID") @PathVariable String keyItemUuid,
            @Parameter(description = "Cryptographic algorithm") @PathVariable CryptographicAlgorithm algorithm
    ) throws ConnectorException;

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
            path = "/tokenProfiles/{tokenProfileUuid}/keys/{uuid}/items/{keyItemUuid}/encrypt",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    EncryptDataResponseDto encryptData(
            @Parameter(description = "Token Instance UUID") @PathVariable String tokenInstanceUuid,
            @Parameter(description = "Token Profile UUID") @PathVariable String tokenProfileUuid,
            @Parameter(description = "Key UUID") @PathVariable String uuid,
            @Parameter(description = "Key Item UUID") @PathVariable String keyItemUuid,
            @RequestBody CipherDataRequestDto request
    ) throws ConnectorException;

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
            path = "/tokenProfiles/{tokenProfileUuid}/keys/{uuid}/items/{keyItemUuid}/decrypt",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    DecryptDataResponseDto decryptData(
            @Parameter(description = "Token Instance UUID") @PathVariable String tokenInstanceUuid,
            @Parameter(description = "Token Profile UUID") @PathVariable String tokenProfileUuid,
            @Parameter(description = "Key UUID") @PathVariable String uuid,
            @Parameter(description = "Key Item UUID") @PathVariable String keyItemUuid,
            @RequestBody CipherDataRequestDto request
    ) throws ConnectorException;

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
            path = "/tokenProfiles/{tokenProfileUuid}/keys/{uuid}/items/{keyItemUuid}/signature/{algorithm}/attributes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<BaseAttribute> listSignatureAttributes(
            @Parameter(description = "Token Instance UUID") @PathVariable String tokenInstanceUuid,
            @Parameter(description = "Token Profile UUID") @PathVariable String tokenProfileUuid,
            @Parameter(description = "Key instance UUID") @PathVariable String uuid,
            @Parameter(description = "Key Item UUID") @PathVariable String keyItemUuid,
            @Parameter(description = "Cryptographic algorithm") @PathVariable CryptographicAlgorithm algorithm
    ) throws ConnectorException;


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
            path = "/tokenProfiles/{tokenProfileUuid}/keys/{uuid}/items/{keyItemUuid}/sign",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    SignDataResponseDto signData(
            @Parameter(description = "Token Instance UUID") @PathVariable String tokenInstanceUuid,
            @Parameter(description = "Token Profile UUID") @PathVariable String tokenProfileUuid,
            @Parameter(description = "Key UUID") @PathVariable String uuid,
            @Parameter(description = "Key Item UUID") @PathVariable String keyItemUuid,
            @RequestBody SignDataRequestDto request
    ) throws ConnectorException;

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
            path = "/tokenProfiles/{tokenProfileUuid}/keys/{uuid}/items/{keyItemUuid}/verify",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    VerifyDataResponseDto verifyData(
            @Parameter(description = "Token Instance UUID") @PathVariable String tokenInstanceUuid,
            @Parameter(description = "Token Profile UUID") @PathVariable String tokenProfileUuid,
            @Parameter(description = "Key UUID") @PathVariable String uuid,
            @Parameter(description = "Key Item UUID") @PathVariable String keyItemUuid,
            @RequestBody VerifyDataRequestDto request
    ) throws ConnectorException;

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
            @Parameter(description = "Token Instance UUID") @PathVariable String tokenInstanceUuid
    ) throws ConnectorException;


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
            @Parameter(description = "Token Instance UUID") @PathVariable String tokenInstanceUuid,
            @RequestBody RandomDataRequestDto request
    ) throws ConnectorException;
}
