package com.otilm.api.model.core.cbom;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.otilm.api.exception.ValidationError;
import com.otilm.api.exception.ValidationException;
import com.otilm.api.model.common.enums.IPlatformEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;

/**
 * State of the cryptographic asset sync for one stored CBOM record. Header sync and asset sync are separate steps: a
 * CBOM is visible in the document list as soon as its header is synced, while its assets appear in the inventory only
 * once this state reaches {@link #SYNCED}.
 */
@Schema(enumAsRef = true)
public enum CbomAssetSyncState implements IPlatformEnum {

    PENDING(Codes.PENDING, "Pending", "Asset sync has not started for this record"),
    IN_PROGRESS(Codes.IN_PROGRESS, "In progress", "Asset sync is currently running for this record"),
    SYNCED(Codes.SYNCED, "Synced", "The record's assets are fully reflected in the inventory"),
    FAILED(Codes.FAILED, "Failed", "The last asset sync attempt for this record failed");

    public static class Codes {
        public static final String PENDING = "pending";
        public static final String IN_PROGRESS = "inProgress";
        public static final String SYNCED = "synced";
        public static final String FAILED = "failed";

        private Codes() {
        }
    }

    private static final CbomAssetSyncState[] VALUES;

    static {
        VALUES = values();
    }

    private final String code;
    private final String label;
    private final String description;

    CbomAssetSyncState(String code, String label, String description) {
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
    public static CbomAssetSyncState findByCode(String code) {
        return Arrays
                .stream(VALUES)
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new ValidationException(
                        ValidationError.create("Unknown CBOM asset sync state {}", code)));
    }
}
