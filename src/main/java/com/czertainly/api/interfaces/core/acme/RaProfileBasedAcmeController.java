package com.czertainly.api.interfaces.core.acme;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class contains the list of end points supported for the ACME implementation
 * in CZERTAINLY. This controller is different from the ACME controller as this implements the
 * list of end points based on the RA Profile.
 */
@RestController
@RequestMapping("acme/raprofile/{raProfileName}")
interface RaProfileBasedAcmeController {

    @RequestMapping(path = "/directory", method = RequestMethod.GET)
    void getDirectory(@PathVariable String raProfileName);

    @RequestMapping(path="/new-nonce")
    void getNonce(@PathVariable String raProfileName);

    @RequestMapping(path = "/new-nonce", method = RequestMethod.HEAD)
    void headNonce(@PathVariable String raProfileName);

    @RequestMapping(path="/new-account", method = RequestMethod.POST)
    void newAccount(@PathVariable String raProfileName);

    @RequestMapping(path="/acct/{accountId}", method = RequestMethod.POST)
    void updateAccount(@PathVariable String raProfileName, @PathVariable String accountId);

    @RequestMapping(path = "/key-change", method = RequestMethod.POST)
    void keyRollover(@PathVariable String raProfileName);

    @RequestMapping(path="/new-order", method=RequestMethod.POST)
    void newOrder(@PathVariable String raProfileName);

    @RequestMapping("/orders/{accountId}")
    void listOrders(@PathVariable String raProfileName, @PathVariable String accountId);

    @RequestMapping(path="/order/{orderId}/finalize", method = RequestMethod.POST)
    void finalize(@PathVariable String raProfileName, @PathVariable String orderId);

    @RequestMapping(path="/cert/{certificateId}", method = RequestMethod.POST)
    void downloadCertificate(@PathVariable String raProfileName, @PathVariable String certificateId);
}
