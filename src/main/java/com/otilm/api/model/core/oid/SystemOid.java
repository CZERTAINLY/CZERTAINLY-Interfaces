package com.otilm.api.model.core.oid;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;

@Getter
public enum SystemOid {

    // RDN Attribute Type
    EMAIL("1.2.840.113549.1.9.1", "Email", OidCategory.RDN_ATTRIBUTE_TYPE, "EMAIL", List.of("E", "EMAILADDRESS")),
    COMMON_NAME("2.5.4.3", "Common Name", OidCategory.RDN_ATTRIBUTE_TYPE, "CN", List.of()),
    ORGANIZATION_UNIT("2.5.4.11", "Organization Unit", OidCategory.RDN_ATTRIBUTE_TYPE, "OU", List.of()),
    ORGANIZATION("2.5.4.10", "Organization", OidCategory.RDN_ATTRIBUTE_TYPE, "O", List.of()),
    LOCALITY("2.5.4.7", "Locality", OidCategory.RDN_ATTRIBUTE_TYPE, "L", List.of()),
    STATE("2.5.4.8", "State", OidCategory.RDN_ATTRIBUTE_TYPE, "ST", List.of("S")),
    DOMAIN_COMPONENT("0.9.2342.19200300.100.1.25", "Domain Component", OidCategory.RDN_ATTRIBUTE_TYPE, "DC", List.of()),
    COUNTRY("2.5.4.6", "Country", OidCategory.RDN_ATTRIBUTE_TYPE, "C", List.of()),
    DISTINGUISHED_NAME_QUALIFIER("2.5.4.46", "Distinguished Name Qualifier", OidCategory.RDN_ATTRIBUTE_TYPE, "DNQ",
            List.of("DN", "dnQualifier")),
    TITLE("2.5.4.12", "Title", OidCategory.RDN_ATTRIBUTE_TYPE, "T", List.of("TITLE")),
    SURNAME("2.5.4.4", "Surname", OidCategory.RDN_ATTRIBUTE_TYPE, "SN", List.of("SURNAME")),
    GIVEN_NAME("2.5.4.42", "Given Name", OidCategory.RDN_ATTRIBUTE_TYPE, "GIVENNAME", List.of()),
    INITIALS("2.5.4.43", "Initials", OidCategory.RDN_ATTRIBUTE_TYPE, "INITIALS", List.of()),
    PSEUDONYM("2.5.4.65", "Pseudonym", OidCategory.RDN_ATTRIBUTE_TYPE, "PSEUDONYM", List.of()),
    GENERATION_QUALIFIER("2.5.4.44", "Generation Qualifier", OidCategory.RDN_ATTRIBUTE_TYPE, "GENERATION", List.of()),
    SERIAL_NUMBER("2.5.4.5", "Serial Number", OidCategory.RDN_ATTRIBUTE_TYPE, "SERIALNUMBER", List.of()),
    STREET_ADDRESS("2.5.4.9", "Street Address", OidCategory.RDN_ATTRIBUTE_TYPE, "STREET", List.of("streetAddress")),
    POSTAL_CODE("2.5.4.17", "Postal Code", OidCategory.RDN_ATTRIBUTE_TYPE, "PostalCode", List.of()),
    BUSINESS_CATEGORY("2.5.4.15", "Business Category", OidCategory.RDN_ATTRIBUTE_TYPE, "BusinessCategory", List.of()),
    USER_ID("0.9.2342.19200300.100.1.1", "User ID", OidCategory.RDN_ATTRIBUTE_TYPE, "UID", List.of()),

    /**
     * Certificate extensions a requester plausibly places in a CSR. SAN (2.5.29.17) is absent because the parser
     * diverts it into {@code subjectAltNames}, so an extension mapping on it never matches.
     */
    EXTENDED_KEY_USAGE_EXTENSION("2.5.29.37", "Extended Key Usage", OidCategory.CERTIFICATE_EXTENSION, false,
            ExtensionValueEncoding.DER),
    KEY_USAGE("2.5.29.15", "Key Usage", OidCategory.CERTIFICATE_EXTENSION, true, ExtensionValueEncoding.DER),
    BASIC_CONSTRAINTS("2.5.29.19", "Basic Constraints", OidCategory.CERTIFICATE_EXTENSION, true,
            ExtensionValueEncoding.DER),
    SUBJECT_KEY_IDENTIFIER("2.5.29.14", "Subject Key Identifier", OidCategory.CERTIFICATE_EXTENSION, false,
            ExtensionValueEncoding.DER),
    SUBJECT_DIRECTORY_ATTRIBUTES("2.5.29.9", "Subject Directory Attributes", OidCategory.CERTIFICATE_EXTENSION, false,
            ExtensionValueEncoding.DER),
    NAME_CONSTRAINTS("2.5.29.30", "Name Constraints", OidCategory.CERTIFICATE_EXTENSION, true,
            ExtensionValueEncoding.DER),
    PRIVATE_KEY_USAGE_PERIOD("2.5.29.16", "Private Key Usage Period", OidCategory.CERTIFICATE_EXTENSION, false,
            ExtensionValueEncoding.DER),
    TLS_FEATURE("1.3.6.1.5.5.7.1.24", "TLS Feature", OidCategory.CERTIFICATE_EXTENSION, false,
            ExtensionValueEncoding.DER),

    // Extended Key Usage purposes
    SERVER_AUTH("1.3.6.1.5.5.7.3.1", "TLS Web Server Authentication", OidCategory.EXTENDED_KEY_USAGE),
    CLIENT_AUTH("1.3.6.1.5.5.7.3.2", "TLS Web Client Authentication", OidCategory.EXTENDED_KEY_USAGE),
    CODE_SIGNING("1.3.6.1.5.5.7.3.3", "Code Signing", OidCategory.EXTENDED_KEY_USAGE),
    EMAIL_PROTECTION("1.3.6.1.5.5.7.3.4", "Email Protection", OidCategory.EXTENDED_KEY_USAGE),
    TIME_STAMPING("1.3.6.1.5.5.7.3.8", "Time Stamping", OidCategory.EXTENDED_KEY_USAGE),
    OCSP_SIGNING("1.3.6.1.5.5.7.3.9", "OCSP Signing", OidCategory.EXTENDED_KEY_USAGE),
    DOCUMENT_SIGNING("1.3.6.1.4.1.311.10.3.12", "Document Signing", OidCategory.EXTENDED_KEY_USAGE),
    IPSEC_USER("1.3.6.1.5.5.8.2.2", "IPSec User", OidCategory.EXTENDED_KEY_USAGE),
    IPSEC_END_SYSTEM("1.3.6.1.5.5.7.3.5", "IPSec End System", OidCategory.EXTENDED_KEY_USAGE),
    IPSEC_TUNNEL("1.3.6.1.5.5.7.3.6", "IPSec Tunnel", OidCategory.EXTENDED_KEY_USAGE),
    AUTHENTIC_DOCUMENTS_TRUST("1.2.840.113583.1.1.5", "Authentic Documents Trust", OidCategory.EXTENDED_KEY_USAGE),

    QTST_STATEMENT_1("0.4.0.19422.1.1", "Qualified Electronic Time-Stamp", OidCategory.GENERIC);

    private static final SystemOid[] VALUES;

    static {
        VALUES = values();
    }

    /** Properties only a certificate extension carries; {@code null} for every other category. */
    private record ExtensionProperties(boolean defaultCritical, ExtensionValueEncoding valueEncoding) {
    }

    private final String oid;
    private final String displayName;
    private final OidCategory category;
    private final String code;
    private final List<String> altCodes;
    @Getter(AccessLevel.NONE)
    private final ExtensionProperties extensionProperties;

    /** RDN attribute type: carries the short code, plus any alternative spellings that must also parse. */
    SystemOid(String oid, String displayName, OidCategory category, String code, List<String> altCodes) {
        this(oid, displayName, category, code, altCodes, null);
    }

    /** Entry with no additional properties — an extended-key-usage purpose or a generic identifier. */
    SystemOid(String oid, String displayName, OidCategory category) {
        this(oid, displayName, category, null, List.of(), null);
    }

    /** Certificate extension: the criticality and value-encoding defaults applied when projecting. */
    SystemOid(String oid, String displayName, OidCategory category, boolean defaultCritical,
            ExtensionValueEncoding valueEncoding) {
        this(oid, displayName, category, null, List.of(), new ExtensionProperties(defaultCritical, valueEncoding));
    }

    SystemOid(String oid, String displayName, OidCategory category, String code, List<String> altCodes,
            ExtensionProperties extensionProperties) {
        this.oid = oid;
        this.displayName = displayName;
        this.category = category;
        this.code = code;
        // Never null, so a consumer iterating all values can flatten altCodes without a guard. Named
        // because a null fails class initialisation, which would otherwise surface as a bare NPE.
        this.altCodes = List.copyOf(Objects.requireNonNull(altCodes, () -> "altCodes must not be null for " + name()));
        this.extensionProperties = extensionProperties;
    }

    /** {@code null} unless this is a certificate extension. */
    public Boolean getDefaultCritical() {
        return extensionProperties == null ? null : extensionProperties.defaultCritical();
    }

    /** {@code null} unless this is a certificate extension. */
    public ExtensionValueEncoding getValueEncoding() {
        return extensionProperties == null ? null : extensionProperties.valueEncoding();
    }

    public static SystemOid fromOID(String oid) {
        return Arrays.stream(VALUES).filter(e -> e.oid.equals(oid)).findFirst().orElse(null);
    }

}
