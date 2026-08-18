package com.otilm.api.model.connector.signatures.contentsigning.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.otilm.api.exception.ValidationError;
import com.otilm.api.exception.ValidationException;
import com.otilm.api.model.common.enums.IPlatformEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;

/**
 * The seven content-signing formatting operations. The code is the operation's path segment under
 * {@code /v1/signatureProvider/contentSigning}.
 */
@Schema(enumAsRef = true)
public enum ContentSigningFormattingOperation implements IPlatformEnum {

    COMPUTE_DTBS("computeDtbs", "Compute DTBS", "Builds the signature structure and returns the bytes to be signed"),
    EMBED_SIGNATURE_VALUE("embedSignatureValue", "Embed Signature Value",
            "Embeds the signature value, completing a SIGNED-level signature"),
    COMPUTE_SIGNATURE_TIMESTAMP_IMPRINT("computeSignatureTimestampImprint", "Compute Signature Timestamp Imprint",
            "Returns the imprint the platform is to obtain a signature timestamp over"),
    EMBED_SIGNATURE_TIMESTAMP("embedSignatureTimestamp", "Embed Signature Timestamp",
            "Embeds the signature timestamp token, raising the signature to TIMESTAMPED level"),
    COMPUTE_ARCHIVE_TIMESTAMP_IMPRINT("computeArchiveTimestampImprint", "Compute Archive Timestamp Imprint",
            "Returns the imprint the platform is to obtain an archive timestamp over"),
    EMBED_ARCHIVE_TIMESTAMP("embedArchiveTimestamp", "Embed Archive Timestamp",
            "Embeds the archive timestamp token, raising the signature to ARCHIVAL level"),
    EXTEND_TO_LEVEL("extendToLevel", "Extend To Level",
            "Fetches the validation material the signature needs and embeds it, raising the signature to LONG_TERM level");

    private static final ContentSigningFormattingOperation[] VALUES;

    static {
        VALUES = values();
    }

    private final String code;
    private final String label;
    private final String description;

    ContentSigningFormattingOperation(String code, String label, String description) {
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
    public static ContentSigningFormattingOperation findByCode(String code) {
        return Arrays
                .stream(VALUES)
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new ValidationException(
                        ValidationError.create("Unknown content signing formatting operation {}", code)));
    }
}
