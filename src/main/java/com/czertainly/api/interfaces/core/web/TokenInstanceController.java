package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.*;
import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import com.czertainly.api.model.client.cryptography.token.TokenInstanceRequestDto;
import com.czertainly.api.model.common.AuthenticationServiceExceptionDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import com.czertainly.api.model.common.attribute.v2.BaseAttribute;
import com.czertainly.api.model.core.cryptography.token.TokenInstanceDetailDto;
import com.czertainly.api.model.core.cryptography.token.TokenInstanceDto;
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

@RestController
@RequestMapping("/v1/tokens")
@Tag(name = "Token Instance Controller", description = "Token Instance Controller API")
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
public interface TokenInstanceController {

    // Token Instance Operation APIs
    @Operation(
            summary = "List Token Instances"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Token Instances retrieved")
            })
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<TokenInstanceDto> listTokenInstances();

    @Operation(
            summary = "Get Token Instance Detail"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Token Instance Detail retrieved")
            })
    @RequestMapping(
            path = "/{uuid}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    TokenInstanceDetailDto getTokenInstance(
            @Parameter(description = "UUID of the Token Instance") @PathVariable String uuid
    ) throws ConnectorException;

    @Operation(
            summary = "Create a new Token Instance"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Token Instance Created Successfully"),
                    @ApiResponse(responseCode = "422",
                            description = "Unprocessible Entity",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                                    examples = {
                                            @ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")
                                    })
                    )
            })
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    TokenInstanceDetailDto createTokenInstance(@RequestBody TokenInstanceRequestDto request) throws AlreadyExistException, ValidationException, ConnectorException, AttributeException;

    @Operation(
            summary = "Update Token Instance"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Token Instance Updated"),
                    @ApiResponse(responseCode = "422",
                            description = "Unprocessible Entity",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                                    examples = {
                                            @ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")
                                    })
                    )
            })
    @RequestMapping(path = "/{uuid}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    TokenInstanceDetailDto updateTokenInstance(
            @Parameter(description = "Token Instance UUID") @PathVariable String uuid,
            @RequestBody TokenInstanceRequestDto request)
            throws ConnectorException, ValidationException, AttributeException;

    @Operation(
            description = "Delete Token Instance"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Token Instance deleted")
            }
    )
    @RequestMapping(
            path = "/{uuid}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteTokenInstance(
            @Parameter(description = "Token Instance UUID") @PathVariable String uuid)
            throws NotFoundException;

    @Operation(
            summary = "Activate Token Instance"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Token Instance Activated")
            })
    @RequestMapping(
            path = "/{uuid}/activate",
            method = RequestMethod.PATCH,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void activateTokenInstance(@Parameter(description = "Token Instance UUID") @PathVariable String uuid,
                               @RequestBody List<RequestAttributeDto> attributes)
            throws ConnectorException;

    @Operation(
            summary = "Deactivate Token Instance"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Token Instance Deactivated")
            })
    @RequestMapping(
            path = "/{uuid}/deactivate",
            method = RequestMethod.PATCH,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deactivateTokenInstance(@Parameter(description = "Token Instance UUID") @PathVariable String uuid)
            throws ConnectorException;

    @Operation(
            description = "Delete multiple Token Instance"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Token Instances deleted")
            }
    )
    @RequestMapping(
            path = "/delete",
            method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteTokenInstance(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Token Instance UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {@ExampleObject(value = "[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")}))
            @RequestBody List<String> uuids);

    @Operation(
            summary = "Reload Token Instance status"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Token Instance Status Reloaded from Connector")
            })
    @RequestMapping(
            path = "/{uuid}",
            method = RequestMethod.PATCH,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    TokenInstanceDetailDto reloadStatus(
            @Parameter(description = "UUID of the Token Instance") @PathVariable String uuid
    ) throws ConnectorException;


    // Token Instance related Attribute APIs

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
            path = "/{uuid}/tokenProfiles/attributes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<BaseAttribute> listTokenProfileAttributes(
            @Parameter(description = "Token instance UUID") @PathVariable String uuid
    ) throws ConnectorException;

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
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<BaseAttribute> listTokenInstanceActivationAttributes(
            @Parameter(description = "Token Instance UUID") @PathVariable String uuid
    ) throws ConnectorException;
}
