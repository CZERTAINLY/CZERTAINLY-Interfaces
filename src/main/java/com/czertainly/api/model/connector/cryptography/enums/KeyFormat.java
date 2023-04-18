package com.czertainly.api.model.connector.cryptography.enums;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.lang.Nullable;

import java.util.Arrays;

@Schema(enumAsRef = true)
public enum KeyFormat implements IPlatformEnum, IAbstractSearchableEnum{
    RAW("Raw", "Raw", "Encoded key in raw format"),
    SPKI("SubjectPublicKeyInfo", "SubjectPublicKeyInfo", "DER-encoded ASN.1 SubjectPublicKeyInfo of the public key"),
    PRKI("PrivateKeyInfo", "PrivateKeyInfo", "DER-encoded ASN.1 PrivateKeyInfo of the private key"),
    EPRKI("EncryptedPrivateKeyInfo", "EncryptedPrivateKeyInfo", "DER-encoded ASN.1 EncryptedPrivateKeyInfo of the private key"),
    CUSTOM("Custom", "Custom", "Custom, external, specific data");

    private static final KeyFormat[] VALUES;

    static {
        VALUES = values();
    }

    private final String code;
    private final String label;
    private final String description;

    KeyFormat(String code, String label) {
        this(code, label,null);
    }

    KeyFormat(String code, String label, String description) {
        this.code = code;
        this.label = label;
        this.description = description;
    }

    @JsonCreator
    public static KeyFormat findByCode(String code) {
        return Arrays.stream(VALUES)
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown Key Format {}", code)));
    }

    @Override
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

    @Override
    public String getEnumLabel() {
        return code;
    }

    /**
     * Return a string representation of this status code.
     */
    @Override
    public String toString() {
        return name();
    }

}
