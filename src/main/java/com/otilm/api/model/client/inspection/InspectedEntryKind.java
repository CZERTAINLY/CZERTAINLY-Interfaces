package com.otilm.api.model.client.inspection;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.otilm.api.model.common.enums.IPlatformEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;

/**
 * What one entry of an uploaded file turned out to be.
 */
@Schema(enumAsRef = true,
        description = "What one entry of an uploaded file turned out to be. The kind decides what can be done with "
                + "the entry: only a key needs somewhere to be stored, and only a certificate can go straight into "
                + "the inventory.")
public enum InspectedEntryKind implements IPlatformEnum {

    KEY_PAIR_WITH_CHAIN("keyPairWithChain", "Key pair with chain",
            "A private key together with its certificate and any issuers found alongside it"),
    CERTIFICATE("certificate", "Certificate", "A certificate with no private key"),
    PRIVATE_KEY("privateKey", "Private key", "A private key with no certificate"),
    SECRET_KEY("secretKey", "Secret key", "A secret key"),
    SIGNING_REQUEST("signingRequest", "Signing request",
            "A certificate signing request. It is reported so a caller can see what a file holds, and is not "
                    + "something the certificate import operation takes in.");

    private static final InspectedEntryKind[] VALUES;

    static {
        VALUES = values();
    }

    private final String code;
    private final String label;
    private final String description;

    InspectedEntryKind(String code, String label, String description) {
        this.code = code;
        this.label = label;
        this.description = description;
    }

    @JsonCreator
    public static InspectedEntryKind findByCode(String code) {
        return Arrays
                .stream(VALUES)
                .filter(kind -> kind.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown inspected entry kind " + code));
    }

    @Override
    @JsonValue
    public String getCode() {
        return code;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
