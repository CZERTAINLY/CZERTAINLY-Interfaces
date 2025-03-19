package com.czertainly.api.interfaces.core.acme;

import com.czertainly.api.exception.AcmeProblemDocumentException;
import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.interfaces.NoAuthController;
import com.czertainly.api.model.core.acme.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
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
@RequestMapping("/v1/protocols/acme/{acmeProfileName}")
@Tag(name = "ACME operations", description = "Interfaces used by ACME clients to request ACME related operations. " +
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
public interface AcmeController extends NoAuthController {

    @Operation(summary = "Get Directory information", externalDocs = @ExternalDocumentation(description = "RFC 8555, section 7.1.1", url = "https://datatracker.ietf.org/doc/html/rfc8555#section-7.1.1"))
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Directory information retrieved")})
    @GetMapping(path = "/directory", produces = {"application/json"})
    ResponseEntity<Directory> getDirectory(@Parameter(description = "ACME Profile name") @PathVariable String acmeProfileName) throws NotFoundException, AcmeProblemDocumentException;

    @Operation(summary = "New Nonce", externalDocs = @ExternalDocumentation(description = "RFC 8555, section 7.2", url = "https://datatracker.ietf.org/doc/html/rfc8555#section-7.2"))
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "New Nonce retrieved", content = @Content(schema = @Schema(hidden = true)))})
    @GetMapping(path = "/new-nonce")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<?> getNonce(@Parameter(description = "ACME Profile name") @PathVariable String acmeProfileName);

    @Operation(summary = "New Nonce", externalDocs = @ExternalDocumentation(description = "RFC 8555, section 7.2", url = "https://datatracker.ietf.org/doc/html/rfc8555#section-7.2"))
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "New Nonce retrieved", content = @Content(schema = @Schema(hidden = true)))})
    @RequestMapping(path = "/new-nonce", method = RequestMethod.HEAD)
    ResponseEntity<?> headNonce(@Parameter(description = "ACME Profile name") @PathVariable String acmeProfileName);

    @Operation(summary = "Create Account", externalDocs = @ExternalDocumentation(description = "RFC 8555, section 7.3", url = "https://datatracker.ietf.org/doc/html/rfc8555#section-7.3"))
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Existing Account retrieved"),
            @ApiResponse(responseCode = "201", description = "New Account created")})
    @PostMapping(path = "/new-account", consumes = {"application/jose+json"}, produces = {"application/json"})
    ResponseEntity<Account> newAccount(@Parameter(description = "ACME Profile name") @PathVariable String acmeProfileName, @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "New Account JWS payload", content = @Content(schema = @Schema(implementation = NewAccountRequest.class))) @RequestBody String jwsBody) throws AcmeProblemDocumentException, NotFoundException;

    @Operation(summary = "Update Account", externalDocs = @ExternalDocumentation(description = "RFC 8555, section 7.3.2", url = "https://datatracker.ietf.org/doc/html/rfc8555#section-7.3.2"))
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "ACME Account updated")})
    @PostMapping(path = "/acct/{accountId}", consumes = {"application/jose+json"}, produces = {"application/json"})
    ResponseEntity<Account> updateAccount(@Parameter(description = "ACME Profile name") @PathVariable String acmeProfileName, @Parameter(description = "Account ID") @PathVariable String accountId, @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Account JWS payload", content = @Content(schema = @Schema(implementation = NewAccountRequest.class))) @RequestBody String jwsBody) throws AcmeProblemDocumentException, NotFoundException;

    @Operation(summary = "Key Rollover", externalDocs = @ExternalDocumentation(description = "RFC 8555, section 7.3.5", url = "https://datatracker.ietf.org/doc/html/rfc8555#section-7.3.5"))
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Account key updated"),
            @ApiResponse(responseCode = "409", description = "Conflict. Key already exists", content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDocument.class)))})
    @PostMapping(path = "/key-change", consumes = {"application/jose+json"}, produces = {"application/json"})
    ResponseEntity<?> keyRollover(@Parameter(description = "ACME Profile name") @PathVariable String acmeProfileName, @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Key Rollover JWS payload", content = @Content(schema = @Schema(implementation = KeyRollover.class))) @RequestBody String jwsBody) throws NotFoundException, AcmeProblemDocumentException;

    @Operation(summary = "Request new Order", externalDocs = @ExternalDocumentation(description = "RFC 8555, section 7.4", url = "https://datatracker.ietf.org/doc/html/rfc8555#section-7.4"))
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "New Order request created")})
    @PostMapping(path = "/new-order", consumes = {"application/jose+json"}, produces = {"application/json"})
    ResponseEntity<Order> newOrder(@Parameter(description = "ACME Profile name") @PathVariable String acmeProfileName, @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "New Order JWS payload", content = @Content(schema = @Schema(implementation = CertificateIssuanceRequest.class))) @RequestBody String jwsBody) throws AcmeProblemDocumentException, NotFoundException;

    @Operation(summary = "List Orders", externalDocs = @ExternalDocumentation(description = "RFC 8555, section 7.1.2.1", url = "https://datatracker.ietf.org/doc/html/rfc8555#section-7.1.2.1"))
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Orders list retrieved")})
    @PostMapping(path = "/orders/{accountId}", consumes = {"application/jose+json"}, produces = {"application/json"})
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "List Orders JWS Payload", required = true, content = @Content(mediaType = "application/jose+json", examples = {@ExampleObject(value = "")}))
    ResponseEntity<List<Order>> listOrders(@Parameter(description = "ACME Profile name") @PathVariable String acmeProfileName, @Parameter(description = "Account Id") @PathVariable String accountId) throws NotFoundException, AcmeProblemDocumentException;

    @Operation(summary = "Get Authorizations for an Order", externalDocs = @ExternalDocumentation(description = "RFC 8555, section 7.5", url = "https://datatracker.ietf.org/doc/html/rfc8555#section-7.5"))
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Authorizations retrieved")})
    @PostMapping(path = "/authz/{authorizationId}", consumes = {"application/jose+json"}, produces = {"application/json"})
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Get Authorization of Order JWS Payload", required = true, content = @Content(mediaType = "application/jose+json", examples = {@ExampleObject(value = "")}))
    ResponseEntity<Authorization> getAuthorizations(@Parameter(description = "ACME Profile name") @PathVariable String acmeProfileName, @Parameter(description = "Authorization Id") @PathVariable String authorizationId, @RequestBody String jwsBody) throws NotFoundException, AcmeProblemDocumentException;

    @Operation(summary = "Validate Challenge", externalDocs = @ExternalDocumentation(description = "RFC 8555, section 7.5.1", url = "https://datatracker.ietf.org/doc/html/rfc8555#section-7.5.1"))
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Challenge validation initiated")})
    @PostMapping(path = "/chall/{challengeId}", consumes = {"application/jose+json"}, produces = {"application/json"})
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Initiate Challenge validation JWS Payload", required = true, content = @Content(schema = @Schema(implementation = Map.class), mediaType = "application/jose+json"))
    ResponseEntity<Challenge> validateChallenge(@Parameter(description = "ACME Profile name") @PathVariable String acmeProfileName, @Parameter(description = "Challenge Id") @PathVariable String challengeId) throws NotFoundException, NoSuchAlgorithmException, InvalidKeySpecException, AcmeProblemDocumentException;

    @Operation(summary = "Get Order details", externalDocs = @ExternalDocumentation(description = "RFC 8555, section 7.1.3", url = "https://datatracker.ietf.org/doc/html/rfc8555#section-7.1.3"))
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Order details retrieved")})
    @PostMapping(path = "/order/{orderId}", consumes = {"application/jose+json"}, produces = {"application/json"})
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Get Order details payload", required = true, content = @Content(mediaType = "application/jose+json", examples = {@ExampleObject(value = "")}))
    ResponseEntity<Order> getOrder(@Parameter(description = "ACME Profile name") @PathVariable String acmeProfileName, @Parameter(description = "Order Id") @PathVariable String orderId) throws NotFoundException, AcmeProblemDocumentException;

    @Operation(summary = "Finalize Order", externalDocs = @ExternalDocumentation(description = "RFC 8555, section 7.4", url = "https://datatracker.ietf.org/doc/html/rfc8555#:~:text=the%20order%20resource%27s-,finalize,-URL.%0A%20%20%20The%20POST"))
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Order finalized")})
    @PostMapping(path = "/order/{orderId}/finalize", consumes = {"application/jose+json"}, produces = {"application/json"})
    ResponseEntity<Order> finalizeOrder(@Parameter(description = "ACME Profile name") @PathVariable String acmeProfileName, @Parameter(description = "Order Id") @PathVariable String orderId, @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Finalize Order JWS payload", content = @Content(schema = @Schema(implementation = CertificateFinalizeRequest.class))) @RequestBody String jwsBody) throws AcmeProblemDocumentException, ConnectorException, JsonProcessingException, CertificateException, AlreadyExistException;

    @Operation(summary = "Download Certificate", externalDocs = @ExternalDocumentation(description = "RFC 8555, section 7.4.2", url = "https://datatracker.ietf.org/doc/html/rfc8555#section-7.4.2"))
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Certificate content retrieved", content = @Content(schema = @Schema(hidden = true)))})
    @PostMapping(path = "/cert/{certificateId}", consumes = {"application/jose+json"}, produces = {"application/pem-certificate-chain"})
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Download Certificate Payload", required = true, content = @Content(mediaType = "application/jose+json", examples = {@ExampleObject(value = "")}))
    ResponseEntity<Resource> downloadCertificate(@Parameter(description = "ACME Profile name") @PathVariable String acmeProfileName, @Parameter(description = "Certificate Id") @PathVariable String certificateId) throws NotFoundException, CertificateException;

    @Operation(summary = "Revoke Certificate", externalDocs = @ExternalDocumentation(description = "RFC 8555, section 7.6", url = "https://datatracker.ietf.org/doc/html/rfc8555#section-7.6"))
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Certificate revoked", content = @Content(schema = @Schema(hidden = true)))})
    @PostMapping(path = "/revoke-cert", consumes = {"application/jose+json"}, produces = {"application/json"})
    ResponseEntity<?> revokeCertificate(@Parameter(description = "ACME Profile name") @PathVariable String acmeProfileName, @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Certificate Revocation JWS payload", content = @Content(schema = @Schema(implementation = CertificateRevocationRequest.class))) @RequestBody String jwsBody) throws AcmeProblemDocumentException, ConnectorException, CertificateException;
}
