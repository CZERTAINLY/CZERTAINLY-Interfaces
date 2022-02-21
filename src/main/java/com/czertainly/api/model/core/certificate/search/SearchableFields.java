package com.czertainly.api.model.core.certificate.search;

import java.util.Arrays;
import java.util.Map;

public enum SearchableFields {
    COMMON_NAME("commonName"),
    SERIAL_NUMBER("serialNumber"),
    RA_PROFILE_NAME("raProfileName"),
    ENTITY_NAME("entityName"),
    STATUS("status"),
    GROUP_NAME("groupName"),
    OWNER("owner"),
    ISSUER_COMMON_NAME("issuerCommonName"),
    SIGNATURE_ALGORITHM("signatureAlgorithm"),
    FINGERPRINT("fingerPrint"),
    EXPIRES("expires")
    ;

    private final String field;

    SearchableFields(String string) {
        this.field = string;
    }

    public String getCode() {
        return field;
    }

    public String getCodeFormatted() {
        Map<String, String> propertyMap = Map.ofEntries(
                Map.entry("commonName","common_name"),
                Map.entry("serialNumber","serial_number"),
                Map.entry("raProfileName","ra_profile_id"),
                Map.entry("entityName","entity_id"),
                Map.entry("status","status"),
                Map.entry("groupName","group_id"),
                Map.entry("owner","owner"),
                Map.entry("issuerCommonName","issuer_common_name"),
                Map.entry("signatureAlgorithm","signature_algorithm"),
                Map.entry("fingerPrint","fingerprint")
                );
        return propertyMap.get(field);
    }

    public static SearchableFields fromCode(String field) {
        return Arrays.stream(values())
                .filter(e -> e.field.equals(field))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Unsupported type %s.", field)));
    }
}
