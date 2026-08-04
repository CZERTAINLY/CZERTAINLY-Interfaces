package com.otilm.api.model.core.scep;

import com.otilm.api.exception.ValidationError;
import com.otilm.api.exception.ValidationException;
import com.otilm.api.model.common.enums.IPlatformEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

/**
 * Source of the challenge a SCEP profile authenticates enrolments against: the profile's shared
 * challenge password, or the per-certificate challenge of a certificate registration.
 */
@Schema(enumAsRef = true)
public enum ScepChallengeSource implements IPlatformEnum {
    PROFILE_CHALLENGE_PASSWORD("profileChallengePassword", "Profile Challenge Password"),
    CERTIFICATE_REGISTRATION("certificateRegistration", "Certificate Registration");

    private static final ScepChallengeSource[] VALUES;

    static {
        VALUES = values();
    }

    private final String code;
    private final String label;

    ScepChallengeSource(String code, String label) {
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
    public static ScepChallengeSource findByCode(String code) {
        return Arrays.stream(VALUES)
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown SCEP Challenge Source code {}", code)));
    }
}
