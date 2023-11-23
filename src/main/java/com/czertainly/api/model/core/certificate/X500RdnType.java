package com.czertainly.api.model.core.certificate;

import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;


public enum X500RdnType implements IPlatformEnum {

    EMAIL("EMAIL", "Email", "1.2.840.113549.1.9.1", new String[]{"EMAIL", "E", "EMAILADDRESS"}),
    COMMON_NAME("CN", "CommonName", "2.5.4.3", new String[]{"CN"}),
    ORGANIZATION_UNIT("OU", "OrganizationUnit", "2.5.4.11", new String[]{"OU"}),
    ORGANIZATION("O", "Organization", "2.5.4.10", new String[]{"O"}),
    LOCALITY("L", "Locality", "2.5.4.7", new String[]{"L"}),
    STATE("ST", "State", "2.5.4.8", new String[]{"S","ST"}),
    DOMAIN_COMPONENT("DC", "DomainComponent", "0.9.2342.19200300.100.1.25", new String[]{"DC"}),
    COUNTRY("C", "Country", "2.5.4.6", new String[]{"C"});

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
        return null;
    }

    public String getOID() {
        return this.OID;
    }



}
