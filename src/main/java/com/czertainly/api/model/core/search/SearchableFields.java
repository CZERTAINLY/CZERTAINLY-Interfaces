package com.czertainly.api.model.core.search;

import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.czertainly.api.model.common.enums.cryptography.KeyAlgorithm;
import com.czertainly.api.model.common.enums.cryptography.KeyFormat;
import com.czertainly.api.model.common.enums.cryptography.KeyType;
import com.czertainly.api.model.core.certificate.CertificateState;
import com.czertainly.api.model.core.certificate.CertificateValidationStatus;
import com.czertainly.api.model.core.compliance.ComplianceStatus;
import com.czertainly.api.model.core.cryptography.key.KeyState;
import com.czertainly.api.model.core.cryptography.key.KeyUsage;
import com.czertainly.api.model.core.discovery.DiscoveryStatus;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum SearchableFields {
    COMMON_NAME("commonName", null, null, null),
    SERIAL_NUMBER("serialNumber", null, null, null),
    RA_PROFILE_NAME("raProfile.name", null, null, null),
    CERTIFICATE_STATE("state", CertificateState.class, null, null),
    CERTIFICATE_VALIDATION_STATUS("validationStatus", CertificateValidationStatus.class, null, null),
    COMPLIANCE_STATUS("complianceStatus", ComplianceStatus.class, null, null),
    GROUP_NAME("groups.name", null, null, null),
    CERT_LOCATION_NAME("locations.location.name", null, null, null),
    OWNER("owner.ownerUsername", null, null, null),
    ISSUER_COMMON_NAME("issuerCommonName", null, null, null),
    SIGNATURE_ALGORITHM("signatureAlgorithm", null, null, null),
    FINGERPRINT("fingerprint", null, null, null),
    NOT_AFTER("notAfter", null, null, null),
    NOT_BEFORE("notBefore", null, null, null),
    PUBLIC_KEY_ALGORITHM("publicKeyAlgorithm", null, null, null),
    KEY_SIZE("keySize", null, null, null),
    KEY_USAGE("keyUsage", null, null, null),
    BASIC_CONSTRAINTS("basicConstraints", null, null, null),
    META("meta", null, null, null),
    SUBJECT_ALTERNATIVE_NAMES("subjectAlternativeNames", null, null, null),
    SUBJECTDN("subjectDn", null, null, null),
    ISSUERDN("issuerDn", null, null, null),
    ISSUER_SERIAL_NUMBER("issuerSerialNumber", null, null, null),
    OCSP_VALIDATION("ocspValidation", CertificateValidationStatus.class, null, null),
    CRL_VALIDATION("crlValidation", CertificateValidationStatus.class, null, null),
    SIGNATURE_VALIDATION("signatureValidation", CertificateValidationStatus.class, null, null),
    PRIVATE_KEY("type", null, "key.items", KeyType.PRIVATE_KEY),
    TRUSTED_CA("trustedCa",null, null,null),

    // Cryptographic Key
    CKI_NAME("name", null, null, null),
    CK_GROUP("cryptographicKey.groups.name", null, null, null),
    CK_OWNER("cryptographicKey.owner.ownerUsername", null, null, null),
    CK_TOKEN_PROFILE("cryptographicKey.tokenProfile.name", null, null, null),
    CK_TOKEN_INSTANCE("cryptographicKey.tokenInstanceReference.name", null, null, null),
    CKI_TYPE("type", KeyType.class, null, null),
    CKI_FORMAT("format", KeyFormat.class, null, null),
    CKI_STATE("state", KeyState.class, null, null),
    CKI_LENGTH("length", null, null, null),
    CKI_USAGE("usage", KeyUsage.class, null, null),
    CKI_CRYPTOGRAPHIC_ALGORITHM("cryptographicAlgorithm", KeyAlgorithm.class, null, null),

    // Discovery
    DISCOVERY_NAME("name", null, null, null),
    DISCOVERY_START_TIME("startTime", null, null, null),
    DISCOVERY_END_TIME("endTime", null, null, null),
    DISCOVERY_STATUS("status", DiscoveryStatus.class, null, null),
    DISCOVERY_TOTAL_CERT_DISCOVERED("totalCertificatesDiscovered", null, null, null),
    DISCOVERY_CONNECTOR_NAME("connectorName", null, null, null),
    DISCOVERY_KIND("kind", null, null, null),

    // Entity
    ENTITY_NAME("name", null, null, null),
    ENTITY_CONNECTOR_NAME("connectorName", null, null, null),
    ENTITY_KIND("kind", null, null, null),

    // Location
    LOCATION_NAME("name", null, null, null),
    LOCATION_ENTITY_INSTANCE("entityInstanceName", null, null, null),
    LOCATION_ENABLED("enabled", null, null, null),
    LOCATION_SUPPORT_MULTIPLE_ENTRIES("supportMultipleEntries", null, null, null),
    LOCATION_SUPPORT_KEY_MANAGEMENT("supportKeyManagement", null, null, null);

    private final String field;

    private final Class<? extends IPlatformEnum> enumClass;

    private final String pathToBeJoin;

    private final Object expectedValue;

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

    SearchableFields(final String field, final Class<? extends IPlatformEnum> enumClass, final String pathToBeJoin, final Object expectedValue) {
        this.field = field;
        this.enumClass = enumClass;
        this.pathToBeJoin = pathToBeJoin;
        this.expectedValue = expectedValue;
    }

    @JsonValue
    public String getCode() {
        return field;
    }

    public String getNativeCode() {
        return nativeCodeMap.get(field);
    }

    public Class<? extends IPlatformEnum> getEnumClass() {
        return enumClass;
    }

    public String getPathToBeJoin() {
        return pathToBeJoin;
    }

    public Object getExpectedValue() {
        return expectedValue;
    }

    @JsonCreator
    public static SearchableFields fromCode(String field) {
        return Arrays.stream(values())
                .filter(e -> e.field.equals(field))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Unsupported type %s.", field)));
    }
}
