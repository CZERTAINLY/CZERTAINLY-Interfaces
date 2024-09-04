package com.czertainly.api.interfaces.core.cmp.error;

/**
 * List of implementation error states - it helps to find quickly a purpose of problem
 * <p>
 * rules:
 *  1. DO NOT REUSE THEM - each error has own enum item
 *  2. MUST BE USED ONLY FOR ONE TIME - uniquest is clear, bug is quickly identifiable
 *  3. DO NOT BE LAZY - create own error codes every(time|where) you need it!
 *  4. FORMAT errorCode:
 *      0-2 chars CMP,
 *      3-5 shortcut for location (e.g. Controller->CNT),
 *      6-8 number (from 001-999)
 */
public enum ImplFailureInfo {

    // -- general
    INCONS(       -1, "Inconsistency state"),

    // -- controller.level (CNT)
    CMPCNTR001(    1,"http get method is not supported"),

    // -- service.level (SRV)
    CMPSRV100(   100,"pki message request cannot be parsed"),
    CMPSRV101(   101,"pki message response cannot be parsed"),
    CMPSRV102(   102,"pki message response cannot be processed"),

    // -- validation: message (MSG)
    CMPVALMSG200(200,"validation: pki message type is not supported"),
    CMPVALMSG201(201,"validation: pki message type is unknown"),

    // -- validation: proof of possesion (POP)
    CMPVALPOP501(501, "validation pop: cannot extract public key"),
    CMPVALPOP502(502, "validation pop: cannot initialize signature"),
    CMPVALPOP503(503, "validation pop: cannot verification signature"),
    CMPVALPOP504(504, "validation pop: ProofOfPossession field is null"),
    CMPVALPOP505(505, "validation pop: ProofOfPossession type RAVerified is not allowed"),
    CMPVALPOP506(506, "validation pop: wrong signature"),
    CMPVALPOP507(507, "validation pop: public key in template is missing"),
    CMPVALPOP508(508, "validation pop: PoposkInput field must be absent"),
    CMPVALPOP509(509, "validation pop: subject in template is missing"),

    // -- validation: protection (PRO)
    CMPVALPRO530(530, "validation (request): protection element is missing"),
    CMPVALPRO531(531, "validation (response): protection element is missing"),
    CMPVALPRO532(532, "validation (request): protectionAlg element is missing"),
    CMPVALPRO533(533, "validation (response): protectionAlg element is missing"),

    CRYPTOSIG541(541, "signature-based protection: extraCerts is empty"),
    CRYPTOSIG542(542, "signature-based protection: signature verification is broken"),
    CRYPTOSIG543(543, "signature-based protection: certificate (used for protecting) has key not suitable for signing"),

    // -- handling: message (HAN)
    CMPHANCERTCONF001(1001,"certConf failed - certificate for given fingerprint is not found"),
    CMPHANCERTCONF002(1002,"certConf failed - given transactionId and related certificate are not found"),

    // -- developer
    TODO(       -999, "Only for developer purpose - inform czertainly admin")
    ;
    private final int errorCode;
    private final String errorDescription;

    ImplFailureInfo(int errorCode, String errorDescrition) {
        this.errorCode = errorCode;
        this.errorDescription =errorDescrition;
    }

    public int getCode() {
        return errorCode;
    }

    public String getDescription() {
        return errorDescription;
    }
}
