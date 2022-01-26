package com.czertainly.api.model.common;

import io.swagger.v3.oas.annotations.media.Schema;

public class JwsBody {

    @Schema(
            description = "Protected field of JWS",
            required = true
    )
    private String protectedField;
    @Schema(
            description = "JWS payload",
            required = true
    )
    private String payload;
    @Schema(
            description = "JWS signature",
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
