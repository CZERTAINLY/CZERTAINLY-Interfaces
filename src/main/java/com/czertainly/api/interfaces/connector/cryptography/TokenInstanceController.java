package com.czertainly.api.interfaces.connector.cryptography;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.exception.TokenInstanceException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.attribute.v2.BaseAttribute;
import com.czertainly.api.model.connector.cryptography.token.TokenInstanceDto;
import com.czertainly.api.model.connector.cryptography.token.TokenInstanceRequestDto;
import com.czertainly.api.model.connector.cryptography.token.TokenInstanceStatusDto;
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
@RequestMapping("/v1/cryptographyProvider/tokens")
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
        name = "Token Management",
        description = "Token Management API is used to manage Token instance created from Cryptography Providers. Token represents connection with key stores that can perform cryptographic operations. It can manage one or more key stores, or it can be used with external key stores, such as vaults, hardware security modules, etc. Token Profile represents particular key store that can be used to execute cryptographic operations and key management through the Token instance."
)
public interface TokenInstanceController {

    @Operation(
            summary = "List Token instances"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Token instance list retrieved"
                    )
            })
    @RequestMapping(
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    List<TokenInstanceDto> listTokenInstances();

    @Operation(
            summary = "Get Token instance"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Token instance retrieved"
                    )
            })
    @RequestMapping(
            path = "/{uuid}",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    TokenInstanceDto getTokenInstance(
            @Parameter(description = "Token instance UUID") @PathVariable String uuid
    ) throws NotFoundException;

    @Operation(
            summary = "Create Token instance"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Token instance created"
                    )
            })
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    TokenInstanceDto createTokenInstance(
            @RequestBody TokenInstanceRequestDto request
    ) throws AlreadyExistException;

    @Operation(
            summary = "Update Token instance"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Token instance updated"
                    )
            })
    @RequestMapping(
            path = "/{uuid}",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    TokenInstanceDto updateTokenInstance(
            @Parameter(description = "Token instance UUID") @PathVariable String uuid,
            @RequestBody TokenInstanceRequestDto request
    ) throws NotFoundException;

    @Operation(
            summary = "Remove Token instance"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Token instance removed"
                    )
            })
    @RequestMapping(
            path = "/{uuid}",
            method = RequestMethod.DELETE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void removeTokenInstance(
            @Parameter(description = "Token instance UUID") @PathVariable String uuid
    ) throws NotFoundException;

    @Operation(
            summary = "Get Token instance status",
            description = "Returns the connection status of the Token instance including additional information that might be useful"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Token instance status retrieved"
                    )
            })
    @RequestMapping(
            path = "/{uuid}/status",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    TokenInstanceStatusDto getTokenInstanceStatus(
            @Parameter(description = "Token instance UUID") @PathVariable String uuid
    ) throws NotFoundException;

    @Operation(
            summary = "List Token Profile Attributes"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Token Profile Attributes retrieved"
                    )
            })
    @RequestMapping(
            path = "/{uuid}/tokenProfile/attributes",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    List<BaseAttribute> listTokenProfileAttributes(
            @Parameter(description = "Token instance UUID") @PathVariable String uuid
    ) throws NotFoundException;

    @Operation(
            summary = "Validate Token Profile Attributes"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Token Profile Attributes validated"
                    )
            })
    @RequestMapping(
            path = "/{uuid}/tokenProfile/attributes/validate",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    void validateTokenProfileAttributes(
            @Parameter(description = "Token instance UUID") @PathVariable String uuid,
            @RequestBody List<RequestAttributeDto> attributes
    ) throws ValidationException, NotFoundException;

    // activation of token

    @Operation(
            summary = "List Token activation Attributes"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Token activation Attributes retrieved"
                    )
            })
    @RequestMapping(
            path = "/{uuid}/activate/attributes",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    List<BaseAttribute> listTokenActivationAttributes(
            @Parameter(description = "Token instance UUID") @PathVariable String uuid
    ) throws NotFoundException;

    @Operation(
            summary = "Validate Token activation Attributes"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Token activation Attributes validated"
                    )
            })
    @RequestMapping(
            path = "/{uuid}/activate/attributes/validate",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    void validateTokenActivationAttributes(
            @Parameter(description = "Token instance UUID") @PathVariable String uuid,
            @RequestBody List<RequestAttributeDto> attributes
    ) throws ValidationException, NotFoundException;

    @Operation(
            summary = "Activate Token"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Token activated"
                    )
            })
    @RequestMapping(
            path = "/{uuid}/activate",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    void activateToken(
            @Parameter(description = "Token instance UUID") @PathVariable String uuid,
            @RequestBody List<RequestAttributeDto> attributes
    ) throws ValidationException, NotFoundException, TokenInstanceException;

    @Operation(
            summary = "Deactivate Token"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Token deactivated"
                    )
            })
    @RequestMapping(
            path = "/{uuid}/deactivate",
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    void deactivateToken(
            @Parameter(description = "Token instance UUID") @PathVariable String uuid
    ) throws NotFoundException, TokenInstanceException;

}
