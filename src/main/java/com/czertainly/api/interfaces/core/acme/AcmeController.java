package com.czertainly.api.interfaces.core.acme;

import com.czertainly.api.exception.AcmeProblemDocumentException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.model.core.acme.Authorization;
import com.czertainly.api.model.core.acme.Challenge;
import com.czertainly.api.model.core.acme.Directory;
import com.czertainly.api.model.core.acme.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This class contains the list of end points supported for the ACME implementation
 * in CZERTAINLY
 */
@RestController
@RequestMapping("/acme/{acmeProfileName}")
public interface AcmeController {

    @RequestMapping(path = "/directory", method = RequestMethod.GET)
    ResponseEntity<Directory> getDirectory(@PathVariable String acmeProfileName);

    @RequestMapping(path="/new-nonce")
    ResponseEntity<?> getNonce(@PathVariable String acmeProfileName);

    @RequestMapping(path = "/new-nonce", method = RequestMethod.HEAD)
    ResponseEntity<?> headNonce(@PathVariable String acmeProfileName);

    @RequestMapping(path="/new-account", method = RequestMethod.POST)
    ResponseEntity<?> newAccount(@PathVariable String acmeProfileName, @RequestBody String jwsBody) throws AcmeProblemDocumentException, NotFoundException;

    @RequestMapping(path="/acct/{accountId}", method = RequestMethod.POST)
    void updateAccount(@PathVariable String acmeProfileName, @RequestBody String jwsBody) throws AcmeProblemDocumentException;

    @RequestMapping(path = "/key-change", method = RequestMethod.POST)
    void keyRollover(@PathVariable String acmeProfileName);

    @RequestMapping(path="/new-order", method=RequestMethod.POST)
    ResponseEntity<Order> newOrder(@PathVariable String acmeProfileName, @RequestBody String jwsBody) throws AcmeProblemDocumentException;

    @RequestMapping("/orders/{accountId}")
    void listOrders(@PathVariable String acmeProfileName, @PathVariable String accountId);

    @RequestMapping(path="/authz/{authorizationId}", method = RequestMethod.POST)
    ResponseEntity<Authorization> getAuthorizations(@PathVariable String acmeProfileName, @PathVariable String authorizationId) throws NotFoundException;

    @RequestMapping(path="/chall/{challengeId}", method = RequestMethod.POST)
    ResponseEntity<Challenge> validateChallenge(@PathVariable String acmeProfileName, @PathVariable String challengeId) throws NotFoundException;

    @RequestMapping(path="/order/{orderId}/finalize", method = RequestMethod.POST)
    ResponseEntity<Order> finalize(@PathVariable String acmeProfileName, @PathVariable String orderId, @RequestBody String jwsBody) throws AcmeProblemDocumentException, NotFoundException, JsonProcessingException;

    @RequestMapping(path="/cert/{certificateId}", method = RequestMethod.POST)
    void downloadCertificate(@PathVariable String acmeProfileName, @PathVariable String certificateId);
}
