package com.czertainly.api.model.core.certificate.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum SearchableFields {
    COMMON_NAME("commonName"),
    SERIAL_NUMBER("serialNumber"),
    RA_PROFILE_NAME("raProfile"),
    ENTITY_NAME("entity"),
    STATUS("status"),
    GROUP_NAME("group"),
    OWNER("owner"),
    ISSUER_COMMON_NAME("issuerCommonName"),
    SIGNATURE_ALGORITHM("signatureAlgorithm"),
    FINGERPRINT("fingerprint"),
    NOT_AFTER("notAfter"),
    NOT_BEFORE("notBefore"),
    PUBLIC_KEY_ALGORITHM("publicKeyAlgorithm"),
    KEY_SIZE("keySize"),
    KEY_USAGE("keyUsage"),
    BASIC_CONSTRAINTS("basicConstraints"),
    META("meta"),
    SUBJECT_ALTERNATIVE_NAMES("subjectAlternativeNames"),
    SUBJECTDN("subjectDn"),
    ISSUERDN("issuerDn"),
    ISSUER_SERIAL_NUMBER("issuerSerialNumber"),
    OCSP_VALIDATION("ocspValidation"),
    CRL_VALIDATION("crlValidation"),
    SIGNATURE_VALIDATION("signatureValidation")
    ;

    private final String field;

    SearchableFields(String string) {
        this.field = string;
    }

    @JsonValue
    public String getCode() {
        return field;
    }

    @JsonCreator
    public static SearchableFields fromCode(String field) {
        return Arrays.stream(values())
                .filter(e -> e.field.equals(field))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Unsupported type %s.", field)));
    }
}
