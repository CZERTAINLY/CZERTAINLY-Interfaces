package com.czertainly.api.model.core.acme;

public class AcmeJwsBody {
    private String protectedField;
    private String payload;
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
