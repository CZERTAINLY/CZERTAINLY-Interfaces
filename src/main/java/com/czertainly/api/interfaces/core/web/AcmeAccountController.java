package com.czertainly.api.interfaces.core.web;

import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.model.client.acme.AcmeAccountListResponseDto;
import com.czertainly.api.model.client.acme.AcmeAccountResponseDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/acmeAccounts")
@Tag(name = "ACME Account Management API", description = "ACME Account Management API")
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
public interface AcmeAccountController {

    @Operation(summary = "List ACME Account")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "ACME Account list retrieved")})
    @RequestMapping(produces = {"application/json"})
    public List<AcmeAccountListResponseDto> listAcmeAccount();

    @Operation(summary = "Details of ACME Account")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "ACME Account detail retrieved")})
    @RequestMapping(path = "/{uuid}", produces = {"application/json"})
    public AcmeAccountResponseDto getAcmeAccount(@PathVariable String uuid) throws NotFoundException;

    @Operation(summary = "Enable ACME Account")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "ACME Account enabled")})
    @RequestMapping(path = "/{uuid}/enable", produces = {"application/json"}, method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enableAcmeAccount(@PathVariable String uuid) throws NotFoundException;

    @Operation(summary = "Disable ACME Account")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "ACME Account disabled")})
    @RequestMapping(path = "/{uuid}/disable", produces = {"application/json"}, method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  disableAcmeAccount(@PathVariable String uuid) throws NotFoundException;

    @Operation(summary = "Delete ACME Account")
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "ACME Account deleted")})
    @RequestMapping(path = "/{uuid}", produces = {"application/json"}, method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  deleteAcmeAccount(@PathVariable String uuid) throws NotFoundException;

    @Operation(summary = "Enable multiple ACME Accounts")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "ACME Accounts enabled")})
    @RequestMapping(path = "/enable", produces = {"application/json"}, consumes = {"application/json"}, method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void bulkEnableAcmeAccount(@RequestBody List<String> uuids) throws NotFoundException;

    @Operation(summary = "Disable multiple ACME Accounts")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "ACME Accounts disabled")})
    @RequestMapping(path = "/disable", produces = {"application/json"}, consumes = {"application/json"}, method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  bulkDisableAcmeAccount(@RequestBody List<String> uuids) throws NotFoundException;

    @Operation(summary = "Delete multiple ACME Accounts")
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "ACME Accounts deleted")})
    @RequestMapping(path = "/delete", produces = {"application/json"}, consumes = {"application/json"}, method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  bulkDeleteAcmeAccount(@RequestBody List<String> uuids) throws NotFoundException;
}
