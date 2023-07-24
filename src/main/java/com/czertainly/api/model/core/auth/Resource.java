package com.czertainly.api.model.core.auth;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

@Schema(enumAsRef = true)
public enum Resource implements IPlatformEnum {
    NONE("NONE", "None", false),

    // GENERAL
    DASHBOARD("dashboard", "Dashboard", false),
    SETTINGS("settings", "Settings", false),
    AUDIT_LOG("auditLogs", "Audit logs", false),
    CREDENTIAL("credentials", "Credentials", true),
    CONNECTOR("connectors", "Connectors", true),
    ATTRIBUTE("attributes", "Attributes", false),
    SCHEDULED_JOB("jobs", "Scheduled jobs", false),

    // AUTH
    USER("users", "Users", false),
    ROLE("roles", "Roles", false),

    // ACME
    ACME_ACCOUNT("acmeAccounts", "ACME Accounts", false),
    ACME_PROFILE("acmeProfiles", "ACME Profiles", true),

    //SCEP
    SCEP_PROFILE("scepProfiles", "SCEP Profiles", true),

    // CERTIFICATES
    AUTHORITY("authorities", "Authorities", true),
    NOTIFICATION("notifications", "Notifications", false),
    RA_PROFILE("raProfiles", "RA Profiles", true),
    CERTIFICATE("certificates", "Certificates", false),
    GROUP("groups", "Groups", true),
    COMPLIANCE_PROFILE("complianceProfiles", "Compliance Profiles", true),
    DISCOVERY("discoveries", "Discoveries", false),

    // ENTITIES
    ENTITY("entities", "Entities", true),
    LOCATION("locations", "Locations", true),

    //CRYPTOGRAPHY
    TOKEN_PROFILE("tokenProfiles", "Token Profiles", true),
    TOKEN("tokens", "Tokens", true),
    CRYPTOGRAPHIC_KEY("keys", "Keys", false),
    ;

    private static final Resource[] VALUES;

    static {
        VALUES = values();
    }

    @Schema(description = "Resource Name",
            example = "certificates",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private final String code;
    private final String label;
    private final String description;
    private final boolean objectAccess;

    Resource(String code, String label, boolean objectAccess) {
        this(code, label,null, objectAccess);
    }

    Resource(String code, String label, String description, boolean objectAccess) {
        this.code = code;
        this.label = label;
        this.description = description;
        this.objectAccess = objectAccess;
    }

    @Override
    @JsonValue
    public String getCode() {
        return this.code;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    public boolean hasObjectAccess() {
        return objectAccess;
    }

    @JsonCreator
    public static Resource findByCode(String code) {
        return Arrays.stream(VALUES)
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown Resource Name {}", code)));
    }
}
