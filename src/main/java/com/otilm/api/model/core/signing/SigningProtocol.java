package com.otilm.api.model.core.signing;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.otilm.api.exception.ValidationError;
import com.otilm.api.exception.ValidationException;
import com.otilm.api.model.common.enums.IPlatformEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;
import java.util.List;

/**
 * How a signing operation was initiated. The {@code gateEligible} flag marks which values a Signing Profile may offer.
 */
@Schema(enumAsRef = true,
        description = "How a signing operation was initiated. Some values name a client protocol that a Signing "
                + "Profile can enable; others record an invocation the platform makes on its own and can never be "
                + "enabled.")
public enum SigningProtocol implements IPlatformEnum {

    CSC_API(Codes.CSC_API, "CSC API Protocol", "Cloud Signature Consortium API v2", true),
    TSP(Codes.TSP, "Timestamping Protocol", "Timestamping Protocol based on RFC 3161", true),
    INTERNAL_TSA(Codes.INTERNAL_TSA, "Internal TSA",
            "In-process timestamp issuance by the signing engine, recorded but never reachable by a client", false);

    private static final SigningProtocol[] VALUES;

    static {
        VALUES = values();
    }

    private final String code;
    private final String label;
    private final String description;
    private final boolean gateEligible;

    SigningProtocol(String code, String label, String description, boolean gateEligible) {
        this.code = code;
        this.label = label;
        this.description = description;
        this.gateEligible = gateEligible;
    }

    @JsonCreator
    public static SigningProtocol findByCode(String code) {
        return Arrays
                .stream(VALUES)
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(
                        () -> new ValidationException(ValidationError.create("Unknown signing protocol {}", code)));
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

    public boolean isGateEligible() {
        return this.gateEligible;
    }

    public static List<SigningProtocol> gateEligibleValues() {
        return Arrays.stream(VALUES).filter(SigningProtocol::isGateEligible).toList();
    }

    public static class Codes {
        public static final String CSC_API = "csc_api";
        public static final String TSP = "tsp";
        public static final String INTERNAL_TSA = "internal_tsa";

        private Codes() {
        }
    }
}
