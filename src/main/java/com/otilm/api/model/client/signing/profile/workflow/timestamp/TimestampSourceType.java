package com.otilm.api.model.client.signing.profile.workflow.timestamp;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.otilm.api.exception.ValidationError;
import com.otilm.api.exception.ValidationException;
import com.otilm.api.model.common.enums.IPlatformEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;

/**
 * Which kind of timestamp source a content-signing profile references, and the discriminator selecting its shape.
 */
@Schema(enumAsRef = true,
        description = "Kind of timestamp source, and the discriminator selecting the fields that accompany it")
public enum TimestampSourceType implements IPlatformEnum {

    INTERNAL(Codes.INTERNAL, "Internal",
            "A Timestamping Signing Profile on this platform, invoked in process rather than over TSP");

    public static class Codes {

        private Codes() {
        }

        public static final String INTERNAL = "internal";
    }

    private static final TimestampSourceType[] VALUES;

    static {
        VALUES = values();
    }

    private final String code;
    private final String label;
    private final String description;

    TimestampSourceType(String code, String label, String description) {
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
    public static TimestampSourceType findByCode(String code) {
        return Arrays
                .stream(VALUES)
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new ValidationException(
                        ValidationError.create("Unknown timestamp source type {}", code)));
    }
}
