package com.czertainly.api.interfaces.core.acme;

import com.czertainly.api.exception.AcmeProblemDocumentException;
import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.model.core.acme.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Map;

/**
 * List of end points supported for the ACME implementation in CZERTAINLY
 */
@RestController
@RequestMapping("/acme/{acmeProfileName}")
@Tag(name = "ACME API", description = "Interfaces used by ACME clients to request ACME related operations. " +
        "ACME Profile defines the behaviour for the specific ACME configuration. When the ACME Profile contains " +
        "default RA Profile, it can be used by the ACME clients to request operations on their specific URL.")
@ApiResponses(
        value = {
                @ApiResponse(
                        responseCode = "400",
                        description = "Bad Request",
                        content = @Content(mediaType = "application/problem+json",
                                schema = @Schema(implementation = ProblemDocument.class))
                ),
                @ApiResponse(
                        responseCode = "401",
                        description = "Unauthorized",
                        content = @Content(mediaType = "application/problem+json",
                                schema = @Schema(implementation = ProblemDocument.class))
                ),
                @ApiResponse(
                        responseCode = "403",
                        description = "Forbidden",
                        content = @Content(mediaType = "application/problem+json",
                                schema = @Schema(implementation = ProblemDocument.class))
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal Server Error",
                        content = @Content
                )
        })
public interface AcmeController {

    @Operation(summary = "Get Directory information")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Directory information retrieved")})
    @RequestMapping(path = "/directory", method = RequestMethod.GET, produces = {"application/json"})
    ResponseEntity<Directory> getDirectory(@Parameter(description = "ACME Profile name") @PathVariable String acmeProfileName) throws NotFoundException, AcmeProblemDocumentException;

    @Operation(summary = "New Nonce")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "New Nonce retrieved", content = @Content(schema = @Schema(hidden = true)))})
    @RequestMapping(path = "/new-nonce", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<?> getNonce(@Parameter(description = "ACME Profile name") @PathVariable String acmeProfileName);

    @Operation(summary = "New Nonce")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "New Nonce retrieved", content = @Content(schema = @Schema(hidden = true)))})
    @RequestMapping(path = "/new-nonce", method = RequestMethod.HEAD)
    ResponseEntity<?> headNonce(@Parameter(description = "ACME Profile name") @PathVariable String acmeProfileName);

    @Operation(summary = "Create Account")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Existing Account retrieved"),
            @ApiResponse(responseCode = "201", description = "New Account created")})
    @RequestMapping(path = "/new-account", method = RequestMethod.POST, consumes = {"application/jose+json"}, produces = {"application/json"})
    ResponseEntity<Account> newAccount(@Parameter(description = "ACME Profile name") @PathVariable String acmeProfileName, @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "New Account JWS payload", content = @Content(schema = @Schema(implementation = NewAccountRequest.class))) @RequestBody String jwsBody) throws AcmeProblemDocumentException, NotFoundException;

    @Operation(summary = "Update Account")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "ACME Account updated")})
    @RequestMapping(path = "/acct/{accountId}", method = RequestMethod.POST, consumes = {"application/jose+json"}, produces = {"application/json"})
    ResponseEntity<Account> updateAccount(@Parameter(description = "ACME Profile name") @PathVariable String acmeProfileName, @Parameter(description = "Account ID") @PathVariable String accountId, @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Account JWS payload", content = @Content(schema = @Schema(implementation = NewAccountRequest.class))) @RequestBody String jwsBody) throws AcmeProblemDocumentException, NotFoundException;

    @Operation(summary = "Key Rollover")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Account key updated"),
            @ApiResponse(responseCode = "409", description = "Conflict. Key already exists", content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDocument.class)))})
    @RequestMapping(path = "/key-change", method = RequestMethod.POST, consumes = {"application/jose+json"}, produces = {"application/json"})
    ResponseEntity<?> keyRollover(@Parameter(description = "ACME Profile name") @PathVariable String acmeProfileName, @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Key Rollover JWS payload", content = @Content(schema = @Schema(implementation = KeyRollover.class))) @RequestBody String jwsBody) throws NotFoundException, AcmeProblemDocumentException;

    @Operation(summary = "Request new Order")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "New Order request created")})
    @RequestMapping(path = "/new-order", method = RequestMethod.POST, consumes = {"application/jose+json"}, produces = {"application/json"})
    ResponseEntity<Order> newOrder(@Parameter(description = "ACME Profile name") @PathVariable String acmeProfileName, @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "New Order JWS payload", content = @Content(schema = @Schema(implementation = CertificateIssuanceRequest.class))) @RequestBody String jwsBody) throws AcmeProblemDocumentException, NotFoundException;

    @Operation(summary = "List Orders")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Orders list retrieved")})
    @RequestMapping(path = "/orders/{accountId}", method = RequestMethod.POST, consumes = {"application/jose+json"}, produces = {"application/json"})
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "List Orders JWS Payload", required = true, content = @Content(mediaType = "application/jose+json", examples = {@ExampleObject(value = "")}))
    ResponseEntity<List<Order>> listOrders(@Parameter(description = "ACME Profile name") @PathVariable String acmeProfileName, @Parameter(description = "Account Id") @PathVariable String accountId) throws NotFoundException, AcmeProblemDocumentException;

    @Operation(summary = "Get Authorizations for an Order")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Authorizations retrieved")})
    @RequestMapping(path = "/authz/{authorizationId}", method = RequestMethod.POST, consumes = {"application/jose+json"}, produces = {"application/json"})
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Get Authorization of Order JWS Payload", required = true, content = @Content(mediaType = "application/jose+json", examples = {@ExampleObject(value = "")}))
    ResponseEntity<Authorization> getAuthorizations(@Parameter(description = "ACME Profile name") @PathVariable String acmeProfileName, @Parameter(description = "Authorization Id") @PathVariable String authorizationId, @RequestBody String jwsBody) throws NotFoundException, AcmeProblemDocumentException;

    @Operation(summary = "Validate Challenge")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Challenge validation initiated")})
    @RequestMapping(path = "/chall/{challengeId}", method = RequestMethod.POST, consumes = {"application/jose+json"}, produces = {"application/json"})
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Initiate Challenge validation JWS Payload", required = true, content = @Content(schema = @Schema(implementation = Map.class), mediaType = "application/jose+json", examples = {@ExampleObject(value = "{}")}))
    ResponseEntity<Challenge> validateChallenge(@Parameter(description = "ACME Profile name") @PathVariable String acmeProfileName, @Parameter(description = "Challenge Id") @PathVariable String challengeId) throws NotFoundException, NoSuchAlgorithmException, InvalidKeySpecException, AcmeProblemDocumentException;

    @Operation(summary = "Get Order details")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Order details retrieved")})
    @RequestMapping(path = "/order/{orderId}", method = RequestMethod.POST, consumes = {"application/jose+json"}, produces = {"application/json"})
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Get Order details payload", required = true, content = @Content(mediaType = "application/jose+json", examples = {@ExampleObject(value = "")}))
    ResponseEntity<Order> getOrder(@Parameter(description = "ACME Profile name") @PathVariable String acmeProfileName, @Parameter(description = "Order Id") @PathVariable String orderId) throws NotFoundException, AcmeProblemDocumentException;

    @Operation(summary = "Finalize Order")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Order finalized")})
    @RequestMapping(path = "/order/{orderId}/finalize", method = RequestMethod.POST, consumes = {"application/jose+json"}, produces = {"application/json"})
    ResponseEntity<Order> finalize(@Parameter(description = "ACME Profile name") @PathVariable String acmeProfileName, @Parameter(description = "Order Id") @PathVariable String orderId, @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Finalize Order JWS payload", content = @Content(schema = @Schema(implementation = CertificateFinalizeRequest.class))) @RequestBody String jwsBody) throws AcmeProblemDocumentException, ConnectorException, JsonProcessingException, CertificateException, AlreadyExistException;

    @Operation(summary = "Download Certificate")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Certificate content retrieved",content = @Content(schema = @Schema(hidden = true)))})
    @RequestMapping(path = "/cert/{certificateId}", method = RequestMethod.POST, consumes = {"application/jose+json"}, produces = {"application/pem-certificate-chain"})
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Download Certificate Payload", required = true, content = @Content(mediaType = "application/jose+json", examples = {@ExampleObject(value = "")}))
    ResponseEntity<Resource> downloadCertificate(@Parameter(description = "ACME Profile name") @PathVariable String acmeProfileName, @Parameter(description = "Certificate Id") @PathVariable String certificateId) throws NotFoundException, CertificateException;

    @Operation(summary = "Revoke Certificate")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Certificate revoked", content = @Content(schema = @Schema(hidden = true)))})
    @RequestMapping(path = "/revoke-cert", method = RequestMethod.POST, consumes = {"application/jose+json"}, produces = {"application/json"})
    ResponseEntity<?> revokeCertificate(@Parameter(description = "ACME Profile name") @PathVariable String acmeProfileName, @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Certificate Revocation JWS payload", content = @Content(schema = @Schema(implementation = CertificateRevocationRequest.class))) @RequestBody String jwsBody) throws AcmeProblemDocumentException, ConnectorException, CertificateException;
}
