package com.czertainly.api.interfaces.core.client;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.model.client.authority.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.security.cert.CertificateException;
import java.util.List;

@RestController
@RequestMapping("/v1/operations")
@Tag(name = "Client Operations API", description = "Client API for managing End Entities and Certificates")
public interface ClientOperationController {

    @Operation(summary = "Issue Certificate")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Certificate issued"),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)})
    @RequestMapping(path = "/{raProfileName}/certificate/issue", method = RequestMethod.POST, consumes = { "application/json" }, produces = { "application/json" })
    ClientCertificateSignResponseDto issueCertificate(
            @PathVariable String raProfileName,
            @RequestBody ClientCertificateSignRequestDto request)
            throws NotFoundException, CertificateException, AlreadyExistException, ConnectorException;

    @Operation(summary = "Revoke Certificate")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Certificate revoked"),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)})
    @RequestMapping(path = "/{raProfileName}/certificate/revoke", method = RequestMethod.POST, consumes = { "application/json" })
    void revokeCertificate(
            @PathVariable String raProfileName,
            @RequestBody ClientCertificateRevocationDto request)
            throws NotFoundException, ConnectorException;

    @Operation(summary = "List all End Entities")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of entities retrieved"),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content)})
    @RequestMapping(path = "/{raProfileName}/endentity", method = RequestMethod.GET, produces = { "application/json" })
    List<ClientEndEntityDto> listEntities(
            @PathVariable String raProfileName)
            throws NotFoundException, ConnectorException;

    @Operation(summary = "Add End Entity")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "End Entity added"),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)})
    @RequestMapping(path = "/{raProfileName}/endentity", method = RequestMethod.POST, consumes = { "application/json" })
    void addEndEntity(
            @PathVariable String raProfileName,
            @RequestBody ClientAddEndEntityRequestDto request)
            throws NotFoundException, AlreadyExistException, ConnectorException;

    @Operation(summary = "Get End Entity information")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "End Entity detail retrieved"),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)})
    @RequestMapping(path = "/{raProfileName}/endentity/{username}", method = RequestMethod.GET, produces = { "application/json" })
    ClientEndEntityDto getEndEntity(
            @PathVariable String raProfileName,
            @PathVariable String username)
            throws NotFoundException, ConnectorException;

    @Operation(summary = "Edit End Entity")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "End Entity edited"),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)})
    @RequestMapping(path = "/{raProfileName}/endentity/{username}", method = RequestMethod.POST, consumes = { "application/json" })
    void editEndEntity(
            @PathVariable String raProfileName,
            @PathVariable String username,
            @RequestBody ClientEditEndEntityRequestDto request)
            throws NotFoundException, ConnectorException;

    @Operation(summary = "Revoke all Certificates and delete End Entity")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "End Entity revoked and deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)})
    @RequestMapping(path = "/{raProfileName}/endentity/{username}", method = RequestMethod.DELETE)
    void revokeAndDeleteEndEntity(
            @PathVariable String raProfileName,
            @PathVariable String username)
            throws NotFoundException, ConnectorException;

    @Operation(summary = "Reset password for End Entity")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "End Entity password reset"),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)})
    @RequestMapping(path = "/{raProfileName}/endentity/{username}/resetPassword", method = RequestMethod.PUT)
    void resetPassword(
            @PathVariable String raProfileName,
            @PathVariable String username)
            throws NotFoundException, ConnectorException;
}
