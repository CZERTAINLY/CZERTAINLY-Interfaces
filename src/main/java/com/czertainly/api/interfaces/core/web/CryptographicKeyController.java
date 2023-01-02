package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.client.cryptography.key.KeyRequestDto;
import com.czertainly.api.model.common.AuthenticationServiceExceptionDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.attribute.v2.BaseAttribute;
import com.czertainly.api.model.core.cryptography.key.KeyDetailDto;
import com.czertainly.api.model.core.cryptography.key.KeyDto;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/cryptography")
@Tag(name = "Cryptographic Key Controller", description = "Cryptographic Key Controller API")
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
public interface CryptographicKeyController {

    // Token Instance Operation APIs
    @Operation(
            summary = "List Cryptographic Keys"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cryptographic Keys retrieved")
            })
    @RequestMapping(
            path = "/keys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<KeyDto> listKeys(@RequestParam(required = false) Optional<String> tokenProfileUuid);

    @Operation(
            summary = "Get Cryptographic Key Detail"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cryptographic Key Detail retrieved")
            })
    @RequestMapping(
            path = "/tokenProfiles/{tokenProfileUuid}/keys/{uuid}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    KeyDetailDto getKey(
            @Parameter(description = "UUID of the Token Instance") @PathVariable String tokenProfileUuid,
            @Parameter(description = "UUID of the Key") @PathVariable String uuid
    ) throws NotFoundException;

    @Operation(
            summary = "Create a new Cryptographic Key"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Cryptographic Key Created Successfully"),
                    @ApiResponse(responseCode = "422",
                            description = "Unprocessible Entity",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                                    examples = {
                                            @ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")
                                    })
                    )
            })
    @RequestMapping(
            path = "/tokenProfiles/{tokenProfileUuid}/keys",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    KeyDetailDto createKey(@Parameter(description = "UUID of the Token Instance") @PathVariable String tokenProfileUuid,
                           @RequestBody KeyRequestDto request) throws AlreadyExistException, ValidationException, ConnectorException;

    @Operation(
            description = "Destroy Cryptographic Key"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Token Instance destroyed")
            }
    )
    @RequestMapping(
            path = "/tokenProfiles/{tokenProfileUuid}/keys/{uuid}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void destroyKey(
            @Parameter(description = "Token Instance UUID") @PathVariable String tokenProfileUuid,
            @Parameter(description = "Key UUID") @PathVariable String uuid)
            throws ConnectorException;


    // Attribute related API

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
            path = "/tokenProfiles/{tokenProfileUuid}/keys/attributes",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    List<BaseAttribute> listCreateKeyAttributes(
            @Parameter(description = "Token instance UUID") @PathVariable String tokenProfileUuid
    ) throws ConnectorException;
}
