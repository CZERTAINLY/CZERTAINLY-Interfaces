package com.czertainly.api.model.core.certificate;

import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;


public enum RdnType implements IPlatformEnum {

    Email("EMAIL", "Email", "", new String[]{"E", "EMAILADDRESS"}),
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

    private static final RdnType[] VALUES;

    static {
        VALUES = values();
    }

    RdnType(String code, String label, String OID, String[] aliases) {
        this.code = code;
        this.label = label;
        this.OID = OID;
        this.aliases = aliases;
    }

    @JsonCreator
    public static RdnType fromCode(String code) {
        return Arrays.stream(VALUES)
                .filter(e -> e.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Unsupported attribute content type %s.", code)));
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
        return null;
    }

    public String getOID() {
        return this.OID;
    }



}
