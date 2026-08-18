package com.otilm.api.model.connector.signatures.contentsigning.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.otilm.api.exception.ValidationError;
import com.otilm.api.exception.ValidationException;
import com.otilm.api.model.common.enums.IPlatformEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;

/**
 * What kind of artifact the connector fetched while extending a signature.
 */
@Schema(enumAsRef = true)
public enum FetchedArtifactKind implements IPlatformEnum {

    CRL("crl", "CRL", "Certificate revocation list"),
    OCSP("ocsp", "OCSP", "OCSP response"),
    AIA_CERTIFICATE("aiaCertificate", "AIA Certificate",
            "Certificate fetched to complete a chain, from an Authority Information Access location");

    private static final FetchedArtifactKind[] VALUES;

    static {
        VALUES = values();
    }

    private final String code;
    private final String label;
    private final String description;

    FetchedArtifactKind(String code, String label, String description) {
        this.code = code;
        this.label = label;
        this.description = description;
    }

    @Override
    @JsonValue
    public String getCode() {
        return this.code;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @JsonCreator
    public static FetchedArtifactKind findByCode(String code) {
        return Arrays
                .stream(VALUES)
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new ValidationException(
                        ValidationError.create("Unknown fetched artifact kind {}", code)));
    }
}
