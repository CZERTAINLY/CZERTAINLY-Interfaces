package com.czertainly.api.model.core.acme;

import java.util.List;

/**
 * This class represents the parameters and fields for the Authorization Object
 * in the ACME protocol
 */
public class Authorization {
    /**
     * Identifier that the account is authorized to represent
     */
    private Identifier identifier;

    /**
     * Status of the Authorization Object. Possible values are "pending", "valid", "invalid", "deactivated",
     * "expired" and "revoked"
     */
    private AuthorizationStatus status;

    /**
     * Timestamp after which the server will consider the authorization as Invalid.
     * The value is encoded in the format defined by RFC3339
     *
     * This field is required if the object is "valid" by status
     */
    private String expires;

    /**
     *List of the authorization objects that the client is entitles to complete to prove the ownership
     * of the domain.
     */
    private List<Challenge> challenges;

    /**
     * True if the certificate is wildcard
     */
    private Boolean wildcard;

    public Identifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    public AuthorizationStatus getStatus() {
        return status;
    }

    public void setStatus(AuthorizationStatus status) {
        this.status = status;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public List<Challenge> getChallenges() {
        return challenges;
    }

    public void setChallenges(List<Challenge> challenges) {
        this.challenges = challenges;
    }

    public Boolean getWildcard() {
        return wildcard;
    }

    public void setWildcard(Boolean wildcard) {
        this.wildcard = wildcard;
    }
}
