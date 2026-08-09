package com.otilm.api.model.connector.discovery.v2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.otilm.api.exception.ValidationError;
import com.otilm.api.exception.ValidationException;
import com.otilm.api.model.common.enums.IPlatformEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;

@Schema(enumAsRef = true)
public enum DiscoveryEventType implements IPlatformEnum {

    PROGRESS(Codes.PROGRESS, "Progress"), RESULT_BATCH(Codes.RESULT_BATCH, "Result Batch"), STATE_CHANGED(
            Codes.STATE_CHANGED, "State Changed"), HEARTBEAT(Codes.HEARTBEAT, "Heartbeat"), ERROR(Codes.ERROR, "Error");

    private static final DiscoveryEventType[] VALUES;

    static {
        VALUES = values();
    }

    private final String code;
    private final String label;
    private final String description;

    DiscoveryEventType(String code, String label) {
        this(code, label, null);
    }

    DiscoveryEventType(String code, String label, String description) {
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
    public static DiscoveryEventType findByCode(String code) {
        return Arrays
                .stream(VALUES)
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(
                        () -> new ValidationException(ValidationError.create("Unknown Discovery event type {}", code)));
    }

    /**
     * Compile-time constants for {@code @JsonSubTypes.Type(name = ...)} on {@link DiscoveryEvent}, mirroring
     * {@code Resource.Codes} and {@code AttributeVersion.Codes} — an annotation attribute must be a constant
     * expression, so the enum's own {@link #code} field cannot be referenced there directly.
     */
    public static class Codes {
        private Codes() {
        }

        public static final String PROGRESS = "progress";
        public static final String RESULT_BATCH = "resultBatch";
        public static final String STATE_CHANGED = "stateChanged";
        public static final String HEARTBEAT = "heartbeat";
        public static final String ERROR = "error";
    }
}
