package com.otilm.api.model.core.protocol;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.otilm.api.exception.ValidationError;
import com.otilm.api.exception.ValidationException;
import com.otilm.api.model.common.enums.IPlatformEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;

/**
 * Source of the credential a protocol profile authenticates enrolments against: the protocol's default mechanism (SCEP
 * shared challenge password, CMP shared secret, ACME domain validation), or the per-certificate challenge of a
 * certificate registration.
 */
@Schema(enumAsRef = true)
public enum ProtocolChallengeSource implements IPlatformEnum {
    PROTOCOL_DEFAULT("protocolDefault", "Protocol Default"),
    CERTIFICATE_REGISTRATION("certificateRegistration", "Certificate Registration");

    private static final ProtocolChallengeSource[] VALUES;

    static {
        VALUES = values();
    }

    private final String code;
    private final String label;

    ProtocolChallengeSource(String code, String label) {
        this.code = code;
        this.label = label;
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
        return null;
    }

    @JsonCreator
    public static ProtocolChallengeSource findByCode(String code) {
        return Arrays
                .stream(VALUES)
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new ValidationException(
                        ValidationError.create("Unknown Protocol Challenge Source code {}", code)));
    }
}
