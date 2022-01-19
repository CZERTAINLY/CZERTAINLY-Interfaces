package com.czertainly.api.interfaces.core.acme;

import com.czertainly.api.exception.AcmeProblemDocumentException;
import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.model.core.acme.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

/**
 * This class contains the list of end points supported for the ACME implementation
 * in CZERTAINLY
 */
@RestController
@RequestMapping("/acme/{acmeProfileName}")
public interface AcmeController {

    @RequestMapping(path = "/directory", method = RequestMethod.GET)
    ResponseEntity<Directory> getDirectory(@PathVariable String acmeProfileName) throws NotFoundException;

    @RequestMapping(path="/new-nonce")
    ResponseEntity<?> getNonce(@PathVariable String acmeProfileName);

    @RequestMapping(path = "/new-nonce", method = RequestMethod.HEAD)
    ResponseEntity<?> headNonce(@PathVariable String acmeProfileName);

    @RequestMapping(path="/new-account", method = RequestMethod.POST)
    ResponseEntity<?> newAccount(@PathVariable String acmeProfileName, @RequestBody String jwsBody) throws AcmeProblemDocumentException, NotFoundException;

    @RequestMapping(path="/acct/{accountId}", method = RequestMethod.POST)
    ResponseEntity<Account> updateAccount(@PathVariable String acmeProfileName, @PathVariable String accountId, @RequestBody String jwsBody) throws AcmeProblemDocumentException, NotFoundException;

    @RequestMapping(path = "/key-change", method = RequestMethod.POST)
    ResponseEntity<?> keyRollover(@PathVariable String acmeProfileName, @RequestBody String jwsBody) throws NotFoundException, AcmeProblemDocumentException;

    @RequestMapping(path="/new-order", method=RequestMethod.POST)
    ResponseEntity<Order> newOrder(@PathVariable String acmeProfileName, @RequestBody String jwsBody) throws AcmeProblemDocumentException, NotFoundException;

    @RequestMapping("/orders/{accountId}")
    ResponseEntity<List<Order>> listOrders(@PathVariable String acmeProfileName, @PathVariable String accountId) throws NotFoundException;

    @RequestMapping(path="/authz/{authorizationId}", method = RequestMethod.POST)
    ResponseEntity<Authorization> getAuthorizations(@PathVariable String acmeProfileName, @PathVariable String authorizationId, @RequestBody String jwsBody) throws NotFoundException, AcmeProblemDocumentException;

    @RequestMapping(path="/chall/{challengeId}", method = RequestMethod.POST)
    ResponseEntity<Challenge> validateChallenge(@PathVariable String acmeProfileName, @PathVariable String challengeId) throws NotFoundException, NoSuchAlgorithmException, InvalidKeySpecException;

    @RequestMapping(path="/order/{orderId}", method = RequestMethod.POST)
    ResponseEntity<Order> getOrder(@PathVariable String acmeProfileName, @PathVariable String orderId) throws NotFoundException;

    @RequestMapping(path="/order/{orderId}/finalize", method = RequestMethod.POST)
    ResponseEntity<Order> finalize(@PathVariable String acmeProfileName, @PathVariable String orderId, @RequestBody String jwsBody) throws AcmeProblemDocumentException, ConnectorException, JsonProcessingException, CertificateException, AlreadyExistException;

    @RequestMapping(path="/cert/{certificateId}", method = RequestMethod.POST)
    ResponseEntity<Resource> downloadCertificate(@PathVariable String acmeProfileName, @PathVariable String certificateId) throws NotFoundException, CertificateException;

    @RequestMapping(path = "/revoke-cert", method = RequestMethod.POST)
    ResponseEntity<?> revokeCertificate(@PathVariable String acmeProfileName, @RequestBody String jwsBody) throws AcmeProblemDocumentException, ConnectorException, CertificateException;
}
