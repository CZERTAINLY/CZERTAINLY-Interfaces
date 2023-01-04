package com.czertainly.api.interfaces.connector.cryptography;

import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.attribute.v2.BaseAttribute;
import com.czertainly.api.model.connector.cryptography.key.*;
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
        description = "Key Management API for cryptographic key management operations. Each key is managed by a specific token that is connected through the Token instance."
)
public interface KeyManagementController {

    @Operation(
            summary = "List of Attributes to create a Secret Key"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of Attributes retrieved"
                    )
            })
    @RequestMapping(
            path = "/secret/attributes",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    /**
     * @throws NotFoundException Token instance not found
     */
    List<BaseAttribute> listCreateSecretKeyAttributes(
            @Parameter(description = "Token instance UUID") @PathVariable String uuid
    ) throws NotFoundException;

    @Operation(
            summary = "Validate list of Attributes to create a Secret Key"
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
            path = "/secret/attributes/validate",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    @ResponseStatus(
            value = HttpStatus.NO_CONTENT
    )
    /**
     * @throws NotFoundException Token instance not found
     * @throws ValidationException Invalid Attributes
     */
    void validateCreateSecretKeyAttributes(
            @Parameter(description = "Token instance UUID") @PathVariable String uuid,
            @RequestBody List<RequestAttributeDto> attributes
    ) throws NotFoundException, ValidationException;

    @Operation(
            summary = "Create a Secret Key"
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
            path = "/secret",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    /**
     * @throws NotFoundException Token instance not found
     */
    KeyDataResponseDto createSecretKey(
            @Parameter(description = "Token instance UUID") @PathVariable String uuid,
            @RequestBody CreateKeyRequestDto request
    ) throws NotFoundException;

    @Operation(
            summary = "List of Attributes to create a Key Pair"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of Attributes retrieved"
                    )
            })
    @RequestMapping(
            path = "/pair/attributes",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    /**
     * @throws NotFoundException Token instance not found
     */
    List<BaseAttribute> listCreateKeyPairAttributes(
            @Parameter(description = "Token instance UUID") @PathVariable String uuid
    ) throws NotFoundException;

    @Operation(
            summary = "Validate list of Attributes to create a Key Pair"
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
            path = "/pair/attributes/validate",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    @ResponseStatus(
            value = HttpStatus.NO_CONTENT
    )
    /**
     * @throws NotFoundException Token instance not found
     * @throws ValidationException Invalid Attributes
     */
    void validateCreateKeyPairAttributes(
            @Parameter(description = "Token instance UUID") @PathVariable String uuid,
            @RequestBody List<RequestAttributeDto> attributes
    ) throws NotFoundException, ValidationException;

    @Operation(
            summary = "Create a Key Pair, Public and Private Key"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Key Pair created"
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Unprocessable Entity",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                                    examples={@ExampleObject(value="[\"Error Message 1\",\"Error Message 2\"]")}
                            ))
            })
    @RequestMapping(
            path = "/pair",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    /**
     * @throws NotFoundException Token instance not found
     */
    KeyPairDataResponseDto createKeyPair(
            @Parameter(description = "Token instance UUID") @PathVariable String uuid,
            @RequestBody CreateKeyRequestDto request
    ) throws NotFoundException;

    @Operation(
            summary = "List Keys for the Token instance"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Keys listed"
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Unprocessable Entity",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                                    examples={@ExampleObject(value="[\"Error Message 1\",\"Error Message 2\"]")}
                            ))
            })
    @RequestMapping(
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    /**
     * @throws NotFoundException Token instance not found
     */
    List<KeyDataResponseDto> listKeys(
            @Parameter(description = "Token instance UUID") @PathVariable String uuid
    ) throws NotFoundException;

    @Operation(
            summary = "Get details about the Key"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Key data retrieved"
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Unprocessable Entity",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                                    examples={@ExampleObject(value="[\"Error Message 1\",\"Error Message 2\"]")}
                            ))
            })
    @RequestMapping(
            path = "/{keyUuid}",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    /**
     * @throws NotFoundException Token instance or Key not found
     */
    KeyDataResponseDto getKey(
            @Parameter(description = "Token instance UUID") @PathVariable String uuid,
            @Parameter(description = "Key UUID") @PathVariable String keyUuid
    ) throws NotFoundException;

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
            path = "/{keyUuid}",
            method = RequestMethod.DELETE
    )
    @ResponseStatus(
            value = HttpStatus.NO_CONTENT
    )
    /**
     * @throws NotFoundException Token instance or Key not found
     */
    void destroyKey(
            @Parameter(description = "Token instance UUID") @PathVariable String uuid,
            @Parameter(description = "Key UUID") @PathVariable String keyUuid
    ) throws NotFoundException;

}
