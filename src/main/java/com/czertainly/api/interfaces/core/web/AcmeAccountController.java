package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.model.client.acme.AcmeAccountListResponseDto;
import com.czertainly.api.model.client.acme.AcmeAccountResponseDto;
import com.czertainly.api.model.common.AuthenticationServiceExceptionDto;
import com.czertainly.api.model.common.ErrorMessageDto;
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
@RequestMapping("/v1")
@Tag(name = "ACME Account Management", description = "ACME Account Management API")
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
public interface AcmeAccountController {

    @Operation(summary = "List ACME Accounts")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "ACME Accounts list retrieved")})
    @RequestMapping(path = "/acmeAccounts", produces = {"application/json"}, method = RequestMethod.GET)
    public List<AcmeAccountListResponseDto> listAcmeAccounts();

    @Operation(summary = "Details of ACME Account")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "ACME Account details retrieved")})
    @RequestMapping(path = "/acmeProfiles/{acmeProfileUuid}/acmeAccounts/{acmeAccountUuid}", produces = {"application/json"}, method = RequestMethod.GET)
    public AcmeAccountResponseDto getAcmeAccount(@Parameter(description = "ACME Profile UUID") @PathVariable String acmeProfileUuid, @Parameter(description = "ACME Account UUID") @PathVariable String acmeAccountUuid) throws NotFoundException;

    @Operation(summary = "Enable ACME Account")
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "ACME Account enabled")})
    @RequestMapping(path = "/acmeProfiles/{acmeProfileUuid}/acmeAccounts/{acmeAccountUuid}/enable", produces = {"application/json"}, method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enableAcmeAccount(@Parameter(description = "ACME Profile UUID") @PathVariable String acmeProfileUuid, @Parameter(description = "ACME Account UUID") @PathVariable String acmeAccountUuid) throws NotFoundException;

    @Operation(summary = "Disable ACME Account")
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "ACME Account disabled")})
    @RequestMapping(path = "/acmeProfiles/{acmeProfileUuid}/acmeAccounts/{acmeAccountUuid}/disable", produces = {"application/json"}, method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  disableAcmeAccount(@Parameter(description = "ACME Profile UUID") @PathVariable String acmeProfileUuid, @Parameter(description = "ACME Account UUID") @PathVariable String acmeAccountUuid) throws NotFoundException;

    @Operation(summary = "Revoke ACME Account")
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "ACME Account revoked")})
    @RequestMapping(path = "/acmeProfiles/{acmeProfileUuid}/acmeAccounts/{acmeAccountUuid}", produces = {"application/json"}, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void revokeAcmeAccount(@Parameter(description = "ACME Profile UUID") @PathVariable String acmeProfileUuid, @Parameter(description = "ACME Account UUID") @PathVariable String acmeAccountUuid) throws NotFoundException;

    @Operation(summary = "Enable multiple ACME Accounts")
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "ACME Accounts enabled")})
    @RequestMapping(path = "/acmeAccounts/enable", produces = {"application/json"}, consumes = {"application/json"}, method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void bulkEnableAcmeAccount(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "ACME Account UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples={@ExampleObject(value="[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")})) @RequestBody List<String> uuids) throws NotFoundException;

    @Operation(summary = "Disable multiple ACME Accounts")
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "ACME Accounts disabled")})
    @RequestMapping(path = "/acmeAccounts/disable", produces = {"application/json"}, consumes = {"application/json"}, method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  bulkDisableAcmeAccount(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "ACME Account UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples={@ExampleObject(value="[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")})) @RequestBody List<String> uuids) throws NotFoundException;

    @Operation(summary = "Revoke multiple ACME Accounts")
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "ACME Accounts revoked")})
    @RequestMapping(path = "/acmeAccounts/revoke", produces = {"application/json"}, consumes = {"application/json"}, method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void bulkRevokeAcmeAccount(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "ACME Account UUIDs", content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
            examples={@ExampleObject(value="[\"c2f685d4-6a3e-11ec-90d6-0242ac120003\",\"b9b09548-a97c-4c6a-a06a-e4ee6fc2da98\"]")})) @RequestBody List<String> uuids) throws NotFoundException;
}
