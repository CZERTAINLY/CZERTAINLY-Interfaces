package com.czertainly.api.model.core.auth;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;
import java.util.List;

@Schema(enumAsRef = true)
public enum Resource implements IPlatformEnum {
    NONE("NONE", "None"),

    // GENERAL
    DASHBOARD("dashboard", "Dashboard"),
    SETTINGS("settings", "Settings"),
    AUDIT_LOG("auditLogs", "Audit logs"),
    CREDENTIAL("credentials", "Credential", true, true),
    CONNECTOR("connectors", "Connector", true, true),
    ATTRIBUTE("attributes", "Attribute", true),
    SCHEDULED_JOB("jobs", "Scheduled job"),
    NOTIFICATION_INSTANCE("notificationInstances", "Notification instance"),

    // AUTH
    USER("users", "User", false, true, true, false),
    ROLE("roles", "Role", false, true),

    // ACME
    ACME_ACCOUNT("acmeAccounts", "ACME Account"),
    ACME_PROFILE("acmeProfiles", "ACME Profile", true, true),

    //SCEP
    SCEP_PROFILE("scepProfiles", "SCEP Profile", true, true),

    // CERTIFICATES
    AUTHORITY("authorities", "Authority", true, true),
    RA_PROFILE("raProfiles", "RA Profile", true, true),
    CERTIFICATE("certificates", "Certificate", false, true, true, true),
    CERTIFICATE_REQUEST("certificateRequests", "Certificate Request", false, false),
    GROUP("groups", "Group", true, true),
    COMPLIANCE_PROFILE("complianceProfiles", "Compliance Profile", true, true),
    DISCOVERY("discoveries", "Discovery", false, true),

    // ENTITIES
    ENTITY("entities", "Entity", true, true),
    LOCATION("locations", "Location", true, true),

    //CRYPTOGRAPHY
    TOKEN_PROFILE("tokenProfiles", "Token Profile", true, true),
    TOKEN("tokens", "Token", true, true),
    CRYPTOGRAPHIC_KEY("keys", "Key", false, true, true, true),

    // APPROVALS
    APPROVAL_PROFILE("approvalProfiles", "Approval profile", true),
    APPROVAL("approvals", "Approval"),

    // WORKFLOWS
    RULE("rules", "Rule"),
    ACTION("actions", "Action"),
    TRIGGER("triggers", "Trigger"),
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

    private final boolean hasObjectAccess;
    private final boolean hasCustomAttributes;
    private final boolean hasGroups;
    private final boolean hasOwner;

    Resource(String code, String label) {
        this(code, label, null, false, false, false, false);
    }

    Resource(String code, String label, boolean objectAccess) {
        this(code, label, null, objectAccess, false, false, false);
    }

    Resource(String code, String label, boolean objectAccess, boolean hasCustomAttributes) {
        this(code, label, null, objectAccess, hasCustomAttributes, false, false);

    }

    Resource(String code, String label, boolean objectAccess, boolean hasCustomAttributes, boolean hasGroups, boolean hasOwner) {
        this(code, label, null, objectAccess, hasCustomAttributes, hasGroups, hasOwner);
    }

    Resource(String code, String label, String description, boolean objectAccess, boolean hasCustomAttributes, boolean hasGroups, boolean hasOwner) {
        this.code = code;
        this.label = label;
        this.description = description;
        this.hasObjectAccess = objectAccess;
        this.hasCustomAttributes = hasCustomAttributes;
        this.hasGroups = hasGroups;
        this.hasOwner = hasOwner;
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
        return hasObjectAccess;
    }

    public boolean hasCustomAttributes() {
        return hasCustomAttributes;
    }

    public boolean hasGroups() {
        return hasGroups;
    }

    public boolean hasOwner() {
        return hasOwner;
    }

    @JsonCreator
    public static Resource findByCode(String code) {
        return Arrays.stream(VALUES)
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown Resource Name {}", code)));
    }

    public static List<Resource> getCustomAttributesResources() {
        return Arrays.stream(VALUES).filter(k -> k.hasCustomAttributes).toList();
    }
}
