package com.czertainly.api.model.core.search;

import com.czertainly.api.model.connector.cryptography.enums.IAbstractSearchableEnum;
import com.czertainly.api.model.connector.cryptography.enums.CryptographicAlgorithm;
import com.czertainly.api.model.connector.cryptography.enums.KeyFormat;
import com.czertainly.api.model.connector.cryptography.enums.KeyType;
import com.czertainly.api.model.core.cryptography.key.KeyState;
import com.czertainly.api.model.core.cryptography.key.KeyUsage;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Schema(enumAsRef = true)
public enum SearchableFields {
    COMMON_NAME("commonName", null),
    SERIAL_NUMBER("serialNumber", null),
    RA_PROFILE_NAME("raProfile", null),
    ENTITY_NAME("entity", null),
    STATUS("status", null),
    COMPLIANCE_STATUS("complianceStatus", null),
    GROUP_NAME("group", null),
    OWNER("owner", null),
    ISSUER_COMMON_NAME("issuerCommonName", null),
    SIGNATURE_ALGORITHM("signatureAlgorithm", null),
    FINGERPRINT("fingerprint", null),
    NOT_AFTER("notAfter", null),
    NOT_BEFORE("notBefore", null),
    PUBLIC_KEY_ALGORITHM("publicKeyAlgorithm", null),
    KEY_SIZE("keySize", null),
    KEY_USAGE("keyUsage", null),
    BASIC_CONSTRAINTS("basicConstraints", null),
    META("meta", null),
    SUBJECT_ALTERNATIVE_NAMES("subjectAlternativeNames", null),
    SUBJECTDN("subjectDn", null),
    ISSUERDN("issuerDn", null),
    ISSUER_SERIAL_NUMBER("issuerSerialNumber", null),
    OCSP_VALIDATION("ocspValidation", null),
    CRL_VALIDATION("crlValidation", null),
    SIGNATURE_VALIDATION("signatureValidation", null),

    CK_NAME("name", null),
    CK_GROUP("cryptographicKey.group.name", null),
    CK_OWNER("cryptographicKey.owner", null),
    CK_TOKEN_PROFILE("cryptographicKey.tokenProfile.name", null),
    CK_TOKEN_INSTANCE("cryptographicKey.tokenInstanceReference.name", null),
    CKI_TYPE("type", KeyType.class),
    CKI_FORMAT("format", KeyFormat.class),
    CKI_STATE("state", KeyState.class),
    CKI_LENGTH("length", null),
    CKI_USAGE("usage", KeyUsage.class),
    CKI_CRYPTOGRAPHIC_ALGORITHM("cryptographicAlgorithm", CryptographicAlgorithm.class)
    ;

    private final String field;

    private final Class<? extends IAbstractSearchableEnum> enumClass;


    private final Map<String, String> nativeCodeMap = Stream.of(new String[][]{
            {"commonName", "common_name"},
            {"serialNumber", "serial_number"},
            {"raProfile", "ra_profile_id"},
            {"entity", "entity_id"},
            {"status", "status"},
            {"group", "group_id"},
            {"owner", "owner"},
            {"issuerCommonName", "issuer_common_name"},
            {"signatureAlgorithm", "signature_algorithm"},
            {"fingerprint", "fingerprint"},
            {"notAfter", "not_after"},
            {"notBefore", "not_before"},
            {"publicKeyAlgorithm", "public_key_algorithm"},
            {"keySize", "key_size"},
            {"keyUsage", "key_usage"},
            {"basicConstraints", "basic_constraints"},
            {"meta", "meta"},
            {"subjectAlternativeNames", "subject_alternative_names"},
            {"subjectDn", "subject_dn"},
            {"issuerDn", "issuer_dn"},
            {"issuerSerialNumber", "issuer_serial_number"},
            {"ocspValidation", "ocspValidation"},
            {"crlValidation", "ocspValidation"},
            {"signatureValidation", "ocspValidation"},
            {"name", "name"},

    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

    SearchableFields(String string, Class<? extends IAbstractSearchableEnum> enumClass) {
        this.field = string;
        this.enumClass = enumClass;
    }

    @JsonValue
    public String getCode() {
        return field;
    }

    public String getNativeCode() {
        return nativeCodeMap.get(field);
    }

    public Class<? extends IAbstractSearchableEnum> getEnumClass() {
        return enumClass;
    }

    @JsonCreator
    public static SearchableFields fromCode(String field) {
        return Arrays.stream(values())
                .filter(e -> e.field.equals(field))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Unsupported type %s.", field)));
    }
    }
