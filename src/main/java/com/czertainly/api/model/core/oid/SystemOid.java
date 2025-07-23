package com.czertainly.api.model.core.oid;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum SystemOid {

    // RDN Attribute Type
    EMAIL("1.2.840.113549.1.9.1", "Email", OidCategory.RDN_ATTRIBUTE_TYPE, "IA5String", "EMAIL", List.of("E", "EMAILADDRESS")),
    COMMON_NAME("2.5.4.3", "Common Name", OidCategory.RDN_ATTRIBUTE_TYPE, Constants.UTF_8_STRING, "CN", List.of()),
    ORGANIZATION_UNIT("2.5.4.11", "Organization Unit", OidCategory.RDN_ATTRIBUTE_TYPE, Constants.UTF_8_STRING, "OU", List.of()),
    ORGANIZATION("2.5.4.10", "Organization", OidCategory.RDN_ATTRIBUTE_TYPE, Constants.UTF_8_STRING, "O", List.of()),
    LOCALITY("2.5.4.7", "Locality", OidCategory.RDN_ATTRIBUTE_TYPE, Constants.UTF_8_STRING, "L", List.of()),
    STATE("2.5.4.8", "State", OidCategory.RDN_ATTRIBUTE_TYPE, Constants.UTF_8_STRING, "ST", List.of("S")),
    DOMAIN_COMPONENT("0.9.2342.19200300.100.1.25", "Domain Component", OidCategory.RDN_ATTRIBUTE_TYPE, "IA5String", "DC", List.of()),
    COUNTRY("2.5.4.6", "Country", OidCategory.RDN_ATTRIBUTE_TYPE, "PrintableString", "C", List.of()),
    DISTINGUISHED_NAME_QUALIFIER("2.5.4.46", "Distinguished Name Qualifier", OidCategory.RDN_ATTRIBUTE_TYPE, "PrintableString", "DNQ", List.of()),
    TITLE("2.5.4.12", "Title", OidCategory.RDN_ATTRIBUTE_TYPE, Constants.UTF_8_STRING, "T", List.of("TITLE")),
    SURNAME("2.5.4.4", "Surname", OidCategory.RDN_ATTRIBUTE_TYPE, Constants.UTF_8_STRING, "SN", List.of("SURNAME")),
    GIVEN_NAME("2.5.4.42", "Given Name", OidCategory.RDN_ATTRIBUTE_TYPE, Constants.UTF_8_STRING, "GIVENNAME", List.of()),
    INITIALS("2.5.4.43", "Initials", OidCategory.RDN_ATTRIBUTE_TYPE, Constants.UTF_8_STRING, "INITIALS", List.of()),
    PSEUDONYM("2.5.4.65", "Pseudonym", OidCategory.RDN_ATTRIBUTE_TYPE, Constants.UTF_8_STRING, "PSEUDONYM", List.of()),
    GENERATION_QUALIFIER("2.5.4.44", "Generation Qualifier", OidCategory.RDN_ATTRIBUTE_TYPE, Constants.UTF_8_STRING, "GENERATION", List.of()),

    // Extended Key Usage
    SERVER_AUTH("1.3.6.1.5.5.7.3.1", "TLS Web Server Authentication", OidCategory.EXTENDED_KEY_USAGE),
    CLIENT_AUTH("1.3.6.1.5.5.7.3.2", "TLS Web Client Authentication", OidCategory.EXTENDED_KEY_USAGE),
    CODE_SIGNING("1.3.6.1.5.5.7.3.3", "Code Signing", OidCategory.EXTENDED_KEY_USAGE),
    EMAIL_PROTECTION("1.3.6.1.5.5.7.3.4", "Email Protection", OidCategory.EXTENDED_KEY_USAGE),
    TIME_STAMPING("1.3.6.1.5.5.7.3.8", "Time Stamping", OidCategory.EXTENDED_KEY_USAGE),
    OCSP_SIGNING("1.3.6.1.5.5.7.3.9", "OCSP Signing", OidCategory.EXTENDED_KEY_USAGE)
    ;

    private static final SystemOid[] VALUES;

    static {
        VALUES = values();
    }

    private final String oid;
    private final String displayName;
    private final OidCategory category;
    private final String valueType;
    private final String code;
    private final List<String> altCodes;

    SystemOid(String oid, String displayName, OidCategory category, String valueType, String code, List<String> altCodes) {
        this.oid = oid;
        this.displayName = displayName;
        this.category = category;
        this.valueType = valueType;
        this.code = code;
        this.altCodes = altCodes;
    }

    SystemOid(String oid, String displayName, OidCategory category) {
        this(oid, displayName, category, null, null, null);
    }


    private static class Constants {
        public static final String UTF_8_STRING = "UTF8String";
    }

    public static SystemOid fromOID(String oid) {
        return Arrays.stream(VALUES)
                .filter(e -> e.oid.equals(oid))
                .findFirst()
                .orElse(null);
    }
}
