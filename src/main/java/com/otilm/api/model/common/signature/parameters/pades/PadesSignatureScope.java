package com.otilm.api.model.common.signature.parameters.pades;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.otilm.api.exception.ValidationError;
import com.otilm.api.exception.ValidationException;
import com.otilm.api.model.common.enums.IPlatformEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;

/**
 * What a PAdES signature claims over the document: it certifies the whole document, or it approves one revision.
 *
 * <p>
 * Only the first signature in a PDF can certify. A request to certify a document that already carries a signature is
 * rejected, not downgraded.
 * </p>
 */
@Schema(name = "PadesSignatureScope", enumAsRef = true,
        description = "Scope a PAdES signature claims over the document")
public enum PadesSignatureScope implements IPlatformEnum {

    CERTIFICATION(Codes.CERTIFICATION, "Certification",
            "A certification signature. Changes to the document afterwards are limited to filling in forms, "
                    + "instantiating page templates, and adding further signatures; the permission level is "
                    + "fixed and not configurable. Only the first signature in a document may certify."),
    REVISION(Codes.REVISION, "Revision", "An approval signature over the current revision of the document");

    public static class Codes {

        private Codes() {
        }

        public static final String CERTIFICATION = "certification";
        public static final String REVISION = "revision";
    }

    private static final PadesSignatureScope[] VALUES;

    static {
        VALUES = values();
    }

    private final String code;
    private final String label;
    private final String description;

    PadesSignatureScope(String code, String label, String description) {
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
    public static PadesSignatureScope findByCode(String code) {
        return Arrays
                .stream(VALUES)
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new ValidationException(
                        ValidationError.create("Unknown PAdES signature scope {}", code)));
    }
}
