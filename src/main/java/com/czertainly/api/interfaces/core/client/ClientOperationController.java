package com.czertainly.api.interfaces.core.client;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.model.client.authority.ClientAddEndEntityRequestDto;
import com.czertainly.api.model.client.authority.ClientCertificateRevocationDto;
import com.czertainly.api.model.client.authority.ClientCertificateSignRequestDto;
import com.czertainly.api.model.client.authority.ClientCertificateSignResponseDto;
import com.czertainly.api.model.client.authority.ClientEditEndEntityRequestDto;
import com.czertainly.api.model.client.authority.ClientEndEntityDto;
import com.czertainly.api.model.common.AuthenticationServiceExceptionDto;
import com.czertainly.api.model.common.ErrorMessageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.List;

@RestController
@RequestMapping("/v1/operations")
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
                ),
                @ApiResponse(
                        responseCode = "502",
                        description = "Connector Error",
                        content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))
                ),
                @ApiResponse(
                        responseCode = "503",
                        description = "Connector Communication Error",
                        content = @Content(schema = @Schema(implementation = ErrorMessageDto.class))
                ),
        })
@Tag(name = "Legacy Client Operations", description = "Client API for managing End Entities and Certificates")
public interface ClientOperationController {

    @Operation(summary = "Issue Certificate")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Certificate issued")})
    @RequestMapping(path = "/{raProfileName}/certificate/issue", method = RequestMethod.POST, consumes = { "application/json" }, produces = { "application/json" })
    ClientCertificateSignResponseDto issueCertificate(
            @Parameter(description = "RA Profile name") @PathVariable String raProfileName,
            @RequestBody ClientCertificateSignRequestDto request)
            throws NotFoundException, CertificateException, AlreadyExistException, ConnectorException, NoSuchAlgorithmException;

    @Operation(summary = "Revoke Certificate")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Certificate revoked")})
    @RequestMapping(path = "/{raProfileName}/certificate/revoke", method = RequestMethod.POST, consumes = { "application/json" }, produces = { "application/json" })
    void revokeCertificate(
            @Parameter(description = "RA Profile name") @PathVariable String raProfileName,
            @RequestBody ClientCertificateRevocationDto request)
            throws NotFoundException, ConnectorException;

    @Operation(summary = "List all End Entities")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of entities retrieved")})
    @RequestMapping(path = "/{raProfileName}/endentity", method = RequestMethod.GET, produces = {"application/json"})
    List<ClientEndEntityDto> listEntities(
            @Parameter(description = "RA Profile name") @PathVariable String raProfileName)
            throws NotFoundException, ConnectorException;

    @Operation(summary = "Add End Entity")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "End Entity added")})
    @RequestMapping(path = "/{raProfileName}/endentity", method = RequestMethod.POST, consumes = { "application/json" }, produces = { "application/json" })
    void addEndEntity(
            @Parameter(description = "RA Profile name") @PathVariable String raProfileName,
            @RequestBody ClientAddEndEntityRequestDto request)
            throws NotFoundException, AlreadyExistException, ConnectorException;

    @Operation(summary = "Get End Entity information")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "End Entity detail retrieved")})
    @RequestMapping(path = "/{raProfileName}/endentity/{username}", method = RequestMethod.GET, produces = {"application/json"})
    ClientEndEntityDto getEndEntity(
            @Parameter(description = "RA Profile name") @PathVariable String raProfileName,
            @Parameter(description = "Username") @PathVariable String username)
            throws NotFoundException, ConnectorException;

    @Operation(summary = "Edit End Entity")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "End Entity edited")})
    @RequestMapping(path = "/{raProfileName}/endentity/{username}", method = RequestMethod.POST, consumes = { "application/json" }, produces = { "application/json" })
    void editEndEntity(
            @Parameter(description = "RA Profile name") @PathVariable String raProfileName,
            @Parameter(description = "Username") @PathVariable String username,
            @RequestBody ClientEditEndEntityRequestDto request)
            throws NotFoundException, ConnectorException;

    @Operation(summary = "Revoke all Certificates and delete End Entity")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "End Entity revoked and deleted")})
    @RequestMapping(path = "/{raProfileName}/endentity/{username}", method = RequestMethod.DELETE, produces = {"application/json"})
    void revokeAndDeleteEndEntity(
            @Parameter(description = "RA Profile name") @PathVariable String raProfileName,
            @Parameter(description = "Username") @PathVariable String username)
            throws NotFoundException, ConnectorException;

    @Operation(summary = "Reset password for End Entity")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "End Entity password reset")})
    @RequestMapping(path = "/{raProfileName}/endentity/{username}/resetPassword", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
    void resetPassword(
            @Parameter(description = "RA Profile name") @PathVariable String raProfileName,
            @Parameter(description = "Username") @PathVariable String username)
            throws NotFoundException, ConnectorException;

}
