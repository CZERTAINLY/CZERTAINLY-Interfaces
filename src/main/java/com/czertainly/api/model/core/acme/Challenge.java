package com.czertainly.api.model.core.acme;

/**
 * This class represents the list of the parameters for the challenge object in the
 * ACME protocol
 */
public class Challenge {
    /**
     * Type of the challenge encoded in the object
     */
    private String type;

    /**
     * URL to which the response can be posted after the client completes the challange
     */
    private String url;

    /**
     * Status of the challenge. The possible values are "pending", "processing", "valid", "invalid"
     * This is a mandatory field.
     */
    private ChallengeStatus status;

    /**
     * Time at which the server validated this challenge. This data is encoded in the format
     * specified in RFC3339.
     * This is a Non-Mandatory parameter bus is required if the status of the status field is set to valid
     */
    private String validated;

    /**
     * Error that occurred while the server was validating the challenge, if any, structured as a problem document
     * [RFC7807].  Multiple errors can be indicated by using subproblems.
     * A challenge object with an error MUST have status equal to "invalid".
     */
    private ProblemDocument error;

    /**
     * Random string generated using the SecureRandom class of JAVA to provide a cryptography random key
     */
    private String token;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ChallengeStatus getStatus() {
        return status;
    }

    public void setStatus(ChallengeStatus status) {
        this.status = status;
    }

    public String getValidated() {
        return validated;
    }

    public void setValidated(String validated) {
        this.validated = validated;
    }

    public ProblemDocument getError() {
        return error;
    }

    public void setError(ProblemDocument error) {
        this.error = error;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
