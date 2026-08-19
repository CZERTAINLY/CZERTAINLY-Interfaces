package com.otilm.api.model.common.signature;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.otilm.api.exception.ValidationError;
import com.otilm.api.exception.ValidationException;
import com.otilm.api.model.common.enums.IPlatformEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;

/**
 * The signature family a signature belongs to. v1 ships the four AdES families; future content kinds arrive as
 * additional families rather than as a separate workflow type.
 */
@Schema(enumAsRef = true)
public enum SignatureFamily implements IPlatformEnum {

    PADES(Codes.PADES, "PAdES", "PDF Advanced Electronic Signatures, ETSI EN 319 142", "PAdES"),
    XADES(Codes.XADES, "XAdES", "XML Advanced Electronic Signatures, ETSI EN 319 132", "XAdES"),
    CADES(Codes.CADES, "CAdES", "CMS Advanced Electronic Signatures, ETSI EN 319 122", "CAdES"),
    JADES(Codes.JADES, "JAdES", "JSON Advanced Electronic Signatures, ETSI TS 119 182", "JAdES");

    public static class Codes {

        private Codes() {
        }

        public static final String PADES = "pades";
        public static final String XADES = "xades";
        public static final String CADES = "cades";
        public static final String JADES = "jades";
    }

    private static final SignatureFamily[] VALUES;

    static {
        VALUES = values();
    }

    private final String code;
    private final String label;
    private final String description;
    private final String formatPrefix;

    SignatureFamily(String code, String label, String description, String formatPrefix) {
        this.code = code;
        this.label = label;
        this.description = description;
        this.formatPrefix = formatPrefix;
    }

    /**
     * The family's half of an ETSI baseline identifier, as in the {@code PAdES} of {@code PAdES-B-LT}. Held apart from
     * {@link #getLabel()} so that editing the label for presentation cannot rename a protocol vocabulary.
     */
    public String getFormatPrefix() {
        return this.formatPrefix;
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
    public static SignatureFamily findByCode(String code) {
        return Arrays
                .stream(VALUES)
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(
                        () -> new ValidationException(ValidationError.create("Unknown signature family {}", code)));
    }
}
