package com.czertainly.api.model.core.acme;

import io.swagger.v3.oas.annotations.media.Schema;

public class AcmeJwsBody {

    @Schema(
            description = "Protected Field of JWS",
            required = true
    )
    private String protectedField;
    @Schema(
            description = "JWS Payload",
            required = true
    )
    private String payload;
    @Schema(
            description = "JWS Signature",
            required = true
    )
    private String signature;

    public String getProtected() {
        return protectedField;
    }

    public void setProtected(String protectedField) {
        this.protectedField = protectedField;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
