package com.czertainly.api.interfaces.core.acme;

import com.czertainly.api.exception.AcmeProblemDocumentException;
import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.exception.NotFoundException;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Map;

/**
 * This class contains the list of end points supported for the ACME implementation
 * in CZERTAINLY
 */
@RestController
@RequestMapping("/acme/raProfile/{raProfileName}")
@Tag(name = "ACME RA Profile API", description = "Interfaces used by ACME clients to request ACME related operations " +
        "on top of RA Profile. ACME Profile defines the behaviour for the specific ACME configuration. ACME Profile is " +
        "bound with specific RA Profile and it can be used by the ACME clients to request operations on their specific " +
        "URL. These operations are always specific only for the RA Profile.")
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
public interface AcmeRaProfileController {

    @Operation(summary = "Get Directory information", externalDocs = @ExternalDocumentation(description = "RFC 8555, section 7.1.1", url = "https://datatracker.ietf.org/doc/html/rfc8555#section-7.1.1"))
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Directory information retrieved")})
    @RequestMapping(path = "/directory", method = RequestMethod.GET, produces = {"application/json"})
    ResponseEntity<Directory> getDirectory(@Parameter(description = "RA Profile name") @PathVariable String raProfileName) throws NotFoundException, AcmeProblemDocumentException;

    @Operation(summary = "New Nonce", externalDocs = @ExternalDocumentation(description = "RFC 8555, section 7.2", url = "https://datatracker.ietf.org/doc/html/rfc8555#section-7.2"))
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "New Nonce retrieved", content = @Content(schema = @Schema(hidden = true)))})
    @RequestMapping(path="/new-nonce", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<?> getNonce(@Parameter(description = "RA Profile name") @PathVariable String raProfileName);

    @Operation(summary = "New Nonce", externalDocs = @ExternalDocumentation(description = "RFC 8555, section 7.2", url = "https://datatracker.ietf.org/doc/html/rfc8555#section-7.2"))
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "New Nonce retrieved",content = @Content(schema = @Schema(hidden = true)))})
    @RequestMapping(path = "/new-nonce", method = RequestMethod.HEAD)
    ResponseEntity<?> headNonce(@Parameter(description = "RA Profile name") @PathVariable String raProfileName);

    @Operation(summary = "Create Account", externalDocs = @ExternalDocumentation(description = "RFC 8555, section 7.3", url = "https://datatracker.ietf.org/doc/html/rfc8555#section-7.3"))
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Existing Account retrieved"),
            @ApiResponse(responseCode = "201", description = "New Account created")})
    @RequestMapping(path="/new-account", method = RequestMethod.POST, consumes = {"application/jose+json"}, produces = {"application/json"})
    ResponseEntity<Account> newAccount(@Parameter(description = "RA Profile name") @PathVariable String raProfileName, @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "New Account JWS payload", content = @Content(schema = @Schema(implementation = NewAccountRequest.class))) @RequestBody String jwsBody) throws AcmeProblemDocumentException, NotFoundException;

    @Operation(summary = "Update Account", externalDocs = @ExternalDocumentation(description = "RFC 8555, section 7.3.2", url = "https://datatracker.ietf.org/doc/html/rfc8555#section-7.3.2"))
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "ACME Account updated")})
    @RequestMapping(path="/acct/{accountId}", method = RequestMethod.POST, consumes = {"application/jose+json"}, produces = {"application/json"})
    ResponseEntity<Account> updateAccount(@Parameter(description = "RA Profile name") @PathVariable String raProfileName, @Parameter(description = "Account ID") @PathVariable String accountId, @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Account JWS payload", content = @Content(schema = @Schema(implementation = NewAccountRequest.class))) @RequestBody String jwsBody) throws AcmeProblemDocumentException, NotFoundException;

    @Operation(summary = "Key Rollover", externalDocs = @ExternalDocumentation(description = "RFC 8555, section 7.3.5", url = "https://datatracker.ietf.org/doc/html/rfc8555#section-7.3.5"))
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Account key updated", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "409", description = "Conflict. Key already exists", content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDocument.class)))})
    @RequestMapping(path = "/key-change", method = RequestMethod.POST, consumes = {"application/jose+json"}, produces = {"application/json"})
    ResponseEntity<?> keyRollover(@Parameter(description = "RA Profile name") @PathVariable String raProfileName, @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Key Rollover JWS Payload", content = @Content(schema = @Schema(implementation = KeyRollover.class))) @RequestBody String jwsBody) throws NotFoundException, AcmeProblemDocumentException;

    @Operation(summary = "Request new Order", externalDocs = @ExternalDocumentation(description = "RFC 8555, section 7.4", url = "https://datatracker.ietf.org/doc/html/rfc8555#section-7.4"))
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "New Order request created")})
    @RequestMapping(path="/new-order", method=RequestMethod.POST, consumes = {"application/jose+json"}, produces = {"application/json"})
    ResponseEntity<Order> newOrder(@Parameter(description = "RA Profile name") @PathVariable String raProfileName, @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "New Order JWS payload", content = @Content(schema = @Schema(implementation = CertificateIssuanceRequest.class))) @RequestBody String jwsBody) throws AcmeProblemDocumentException, NotFoundException;

    @Operation(summary = "List Orders", externalDocs = @ExternalDocumentation(description = "RFC 8555, section 7.1.2.1", url = "https://datatracker.ietf.org/doc/html/rfc8555#section-7.1.2.1"))
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Orders list retrieved")})
    @RequestMapping(path= "/orders/{accountId}", method = RequestMethod.POST, consumes = {"application/jose+json"}, produces = {"application/json"})
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "List Orders JWS Payload", required = true, content = @Content(mediaType = "application/jose+json", examples = {@ExampleObject(value = "")}))
    ResponseEntity<List<Order>> listOrders(@Parameter(description = "RA Profile name") @PathVariable String raProfileName, @Parameter(description = "Account Id") @PathVariable String accountId) throws NotFoundException, AcmeProblemDocumentException;

    @Operation(summary = "Get Authorizations for an Order", externalDocs = @ExternalDocumentation(description = "RFC 8555, section 7.5", url = "https://datatracker.ietf.org/doc/html/rfc8555#section-7.5"))
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Authorizations retrieved")})
    @RequestMapping(path="/authz/{authorizationId}", method = RequestMethod.POST, consumes = {"application/jose+json"}, produces = {"application/json"})
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Get Authorization of Order JWS Payload", required = true, content = @Content(mediaType = "application/jose+json", examples = {@ExampleObject(value = "")}))
    ResponseEntity<Authorization> getAuthorizations(@Parameter(description = "RA Profile name") @PathVariable String raProfileName, @Parameter(description = "Authorization Id") @PathVariable String authorizationId, @RequestBody String jwsBody) throws NotFoundException, AcmeProblemDocumentException;

    @Operation(summary = "Validate Challenge", externalDocs = @ExternalDocumentation(description = "RFC 8555, section 7.5.1", url = "https://datatracker.ietf.org/doc/html/rfc8555#section-7.5.1"))
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Challenge validation initiated")})
    @RequestMapping(path="/chall/{challengeId}", method = RequestMethod.POST, consumes = {"application/jose+json"}, produces = {"application/json"})
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Initiate Challenge validation JWS Payload", required = true, content = @Content(schema = @Schema(implementation = Map.class), mediaType = "application/jose+json", examples = {@ExampleObject(value = "{}")}))
    ResponseEntity<Challenge> validateChallenge(@Parameter(description = "RA Profile name") @PathVariable String raProfileName, @Parameter(description = "Challenge Id") @PathVariable String challengeId) throws NotFoundException, NoSuchAlgorithmException, InvalidKeySpecException, AcmeProblemDocumentException;

    @Operation(summary = "Get Order details", externalDocs = @ExternalDocumentation(description = "RFC 8555, section 7.1.3", url = "https://datatracker.ietf.org/doc/html/rfc8555#section-7.1.3"))
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Order details retrieved")})
    @RequestMapping(path="/order/{orderId}", method = RequestMethod.POST, consumes = {"application/jose+json"}, produces = {"application/json"})
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Get Order details payload", required = true, content = @Content(mediaType = "application/jose+json", examples = {@ExampleObject(value = "")}))
    ResponseEntity<Order> getOrder(@Parameter(description = "RA Profile name") @PathVariable String raProfileName, @Parameter(description = "Order Id") @PathVariable String orderId) throws NotFoundException, AcmeProblemDocumentException;

    @Operation(summary = "Finalize Order", externalDocs = @ExternalDocumentation(description = "RFC 8555, section 7.4", url = "https://datatracker.ietf.org/doc/html/rfc8555#:~:text=the%20order%20resource%27s-,finalize,-URL.%0A%20%20%20The%20POST"))
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Order finalized")})
    @RequestMapping(path="/order/{orderId}/finalize", method = RequestMethod.POST, consumes = {"application/jose+json"}, produces = {"application/json"})
    ResponseEntity<Order> finalizeOrder(@Parameter(description = "RA Profile name") @PathVariable String raProfileName, @Parameter(description = "Order Id") @PathVariable String orderId, @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Finalize Order JWS payload", content = @Content(schema = @Schema(implementation = CertificateFinalizeRequest.class))) @RequestBody String jwsBody) throws AcmeProblemDocumentException, ConnectorException, JsonProcessingException, CertificateException, AlreadyExistException;

    @Operation(summary = "Download Certificate", externalDocs = @ExternalDocumentation(description = "RFC 8555, section 7.4.2", url = "https://datatracker.ietf.org/doc/html/rfc8555#section-7.4.2"))
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Certificate content retrieved as file", content = @Content(schema = @Schema(hidden = true)))})
    @RequestMapping(path="/cert/{certificateId}", method = RequestMethod.POST, consumes = {"application/jose+json"}, produces = {"application/pem-certificate-chain"})
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Download Certificate Payload", required = true, content = @Content(mediaType = "application/jose+json", examples = {@ExampleObject(value = "")}))
    ResponseEntity<Resource> downloadCertificate(@Parameter(description = "RA Profile name") @PathVariable String raProfileName, @Parameter(description = "Certificate Id") @PathVariable String certificateId) throws NotFoundException, CertificateException;

    @Operation(summary = "Revoke Certificate", externalDocs = @ExternalDocumentation(description = "RFC 8555, section 7.6", url = "https://datatracker.ietf.org/doc/html/rfc8555#section-7.6"))
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Certificate revoked",content = @Content(schema = @Schema(hidden = true)))})
    @RequestMapping(path = "/revoke-cert", method = RequestMethod.POST, consumes = {"application/jose+json"}, produces = {"application/json"})
    ResponseEntity<?> revokeCertificate(@Parameter(description = "RA Profile name") @PathVariable String raProfileName, @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Certificate Revocation JWS payload", content = @Content(schema = @Schema(implementation = CertificateRevocationRequest.class))) @RequestBody String jwsBody) throws AcmeProblemDocumentException, ConnectorException, CertificateException;
}
