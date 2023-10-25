package com.czertainly.api.model.core.authority;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

public enum CertificateRevocationReason implements IPlatformEnum {

    UNSPECIFIED("unspecified", "Unspecified", 0),
    KEY_COMPROMISE("keyCompromise", "Key compromise", 1),
    CA_COMPROMISE("cACompromise", "CA compromise", 2),
    AFFILIATION_CHANGED("affiliationChanged", "Affiliation changed", 3),
    SUPERSEDED("superseded", "Superseded", 4),
    CESSATION_OF_OPERATION("cessationOfOperation", "Cessation of operation", 5),
    CERTIFICATE_HOLD("certificateHold", "Certificate hold", 6),
    PRIVILEGES_WITHDRAWN("privilegeWithdrawn", "Privilege withdrawn", 9),
    AA_COMPROMISE("aACompromise", "AA compromise", 10);

    private static final CertificateRevocationReason[] VALUES;

    static {
        VALUES = values();
    }

    @Schema(description = "Revocation Reason code",
            example = "keyCompromise", requiredMode = Schema.RequiredMode.REQUIRED)
    private final String code;
    private final String label;
    private final int reasonCode;

    CertificateRevocationReason(String code, String label, int reasonCode) {
        this.code = code;
        this.label = label;
        this.reasonCode = reasonCode;
    }

    @JsonCreator
    public static CertificateRevocationReason fromCode(String code) {
        return Arrays.stream(VALUES)
                .filter(r -> r.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown Certificate Revocation reason {}", code)));
    }

    public static CertificateRevocationReason fromReasonCode(int reasonCode) {
        return Arrays.stream(VALUES)
                .filter(r -> r.reasonCode == reasonCode)
                .findFirst()
                .orElse(null);
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
        return null;
    }

    public int getReasonCode() {
        return reasonCode;
    }
}
