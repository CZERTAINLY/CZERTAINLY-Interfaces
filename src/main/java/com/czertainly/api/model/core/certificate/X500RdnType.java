package com.czertainly.api.model.core.certificate;

import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;


public enum X500RdnType implements IPlatformEnum {

    Email("EMAIL", "Email", "1.2.840.113549.1.9.1", new String[]{"E", "EMAILADDRESS"}),
    CommonName("CN", "CommonName", "2.5.4.3", new String[]{}),
    OrganizationUnit("OU", "OrganizationUnit", "2.5.4.11", new String[]{}),
    Organization("O", "Organization", "2.5.4.10", new String[]{}),
    Locality("L", "Locality", "2.5.4.7", new String[]{}),
    State("ST", "State", "2.5.4.8", new String[]{}),
    DomainComponent("DC", "DomainComponent", "0.9.2342.19200300.100.1.25", new String[]{}),
    Country("C", "Country", "2.5.4.6", new String[]{});

    private final String code;
    private final String label;
    private final String OID;
    private final String[] aliases;

    private static final X500RdnType[] VALUES;

    static {
        VALUES = values();
    }

    X500RdnType(String code, String label, String OID, String[] aliases) {
        this.code = code;
        this.label = label;
        this.OID = OID;
        this.aliases = aliases;
    }

    @JsonCreator
    public static X500RdnType fromCode(String code) {
        return Arrays.stream(VALUES)
                .filter(e -> e.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Unsupported attribute content type %s.", code)));
    }

    public static X500RdnType fromOID(String OID) {
        return Arrays.stream(VALUES)
                .filter(e -> e.OID.equals(OID))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Unsupported attribute content type %s.", OID)));
    }


    @Override
    public String getCode() {
        return this.code;
    }

    public String[] getAliases() {
        return aliases;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public String getDescription() {
        return "";
    }

    public String getOID() {
        return this.OID;
    }



}
