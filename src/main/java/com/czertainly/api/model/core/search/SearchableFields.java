package com.czertainly.api.model.core.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum SearchableFields {
    COMMON_NAME("commonName"),
    SERIAL_NUMBER("serialNumber"),
    RA_PROFILE_NAME("raProfile"),
    ENTITY_NAME("entity"),
    STATUS("status"),
    COMPLIANCE_STATUS("complianceStatus"),
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

    private final Map<String, String> nativeCodeMap = Stream.of(new String[][] {
            { "commonName", "common_name" },
            { "serialNumber", "serial_number" },
            {"raProfile","ra_profile_id"},
            {"entity","entity_id"},
            {"status","status"},
            {"group","group_id"},
            {"owner","owner"},
            {"issuerCommonName","issuer_common_name"},
            {"signatureAlgorithm","signature_algorithm"},
            {"fingerprint","fingerprint"},
            {"notAfter","not_after"},
            {"notBefore","not_before"},
            {"publicKeyAlgorithm","public_key_algorithm"},
            {"keySize","key_size"},
            {"keyUsage","key_usage"},
            {"basicConstraints","basic_constraints"},
            {"meta","meta"},
            {"subjectAlternativeNames","subject_alternative_names"},
            {"subjectDn","subject_dn"},
            {"issuerDn","issuer_dn"},
            {"issuerSerialNumber","issuer_serial_number"},
            {"ocspValidation","ocspValidation"},
            {"crlValidation","ocspValidation"},
            {"signatureValidation","ocspValidation"},

    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

    SearchableFields(String string) {
        this.field = string;
    }

    @JsonValue
    public String getCode() {
        return field;
    }

    public String getNativeCode(){
        return nativeCodeMap.get(field);
    }

    @JsonCreator
    public static SearchableFields fromCode(String field) {
        return Arrays.stream(values())
                .filter(e -> e.field.equals(field))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Unsupported type %s.", field)));
    }
}
