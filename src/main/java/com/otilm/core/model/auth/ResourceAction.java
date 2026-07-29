package com.otilm.core.model.auth;

import com.otilm.api.exception.ValidationError;
import com.otilm.api.exception.ValidationException;
import com.otilm.api.model.common.enums.IPlatformEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

@Schema(enumAsRef = true)
public enum ResourceAction implements IPlatformEnum {
    NONE("NONE", "None", AccessType.NOT_GRANTABLE),
    ANY("ANY", "Any", AccessType.NOT_GRANTABLE), // Action that is evaluated as any action
    MEMBERS("members", "Members", AccessType.READ), // action that is evaluated to allow action for resource lower in hierarchy, e.g. access to certificates through RA profile members action

    // Default (CRUD) Actions
    LIST("list", "List", AccessType.READ),
    DETAIL("detail", "Detail", AccessType.READ),
    CREATE("create", "Create", AccessType.WRITE),
    UPDATE("update", "Update", AccessType.WRITE),
    DELETE("delete", "Delete", AccessType.WRITE),

    // Default change state actions that allows also reverse action (disable/deactivate)
    ENABLE("enable", "Enable", AccessType.WRITE),
    ACTIVATE("activate", "Activate", AccessType.WRITE),

    // Connector actions
    APPROVE("approve", "Approve", AccessType.WRITE),
    CONNECT("connect", "Connect", AccessType.WRITE), // allows also reconnect action

    // Certificate actions
    REGISTER("register", "Register", AccessType.WRITE),
    ISSUE("issue", "Issue", AccessType.WRITE),
    RENEW("renew", "Renew", AccessType.WRITE),
    REKEY("rekey", "Rekey", AccessType.WRITE),
    REVOKE("revoke", "Revoke", AccessType.WRITE),
    ARCHIVE("archive", "Archive", AccessType.WRITE),

    // Audit Log export
    EXPORT("export", "Export", AccessType.READ),

    // Certificate, RA Profile and Compliance Profile
    CHECK_COMPLIANCE("checkCompliance", "Check compliance", AccessType.WRITE),

    // Cryptography operation
    ENCRYPT("encrypt", "Encrypt", AccessType.WRITE),
    DECRYPT("decrypt", "Decrypt", AccessType.WRITE),
    VERIFY("verify", "Verify", AccessType.WRITE),
    SIGN("sign", "Sign", AccessType.WRITE),

    // PROXY
    GET_PROXY_INSTALLATION("getProxyInstallation", "Get proxy installation", AccessType.READ),

    // Secret
    GET_SECRET_CONTENT("getSecretContent", "Get secret content", AccessType.SENSITIVE_READ),
    UPDATE_SOURCE_VAULT_PROFILE("updateSourceVaultProfile", "Update source vault profile", AccessType.WRITE),

    // Digital Signing
    TIMESTAMP("timestamp", "Timestamp", AccessType.WRITE), // RFC 3161 Timestamping
    ;

    /**
     * Declares whether invoking an action reads or changes platform state, so that a permission set can be
     * derived from the action catalogue instead of being maintained by hand. {@code SENSITIVE_READ} is a read
     * that exposes stored secret material, and is therefore withheld from roles granted plain read access.
     * {@code NOT_GRANTABLE} marks the sentinels that never appear in a stored permission row.
     */
    public enum AccessType {
        READ,
        WRITE,
        SENSITIVE_READ,
        NOT_GRANTABLE
    }

    @Schema(description = "Resource Action Name",
            example = "create",
            requiredMode = Schema.RequiredMode.REQUIRED)

    private final String code;

    @Schema(description = "Resource Action label",
            example = "Create",
            requiredMode = Schema.RequiredMode.REQUIRED)

    private final String label;

    private final AccessType accessType;

    ResourceAction(String code, String label, AccessType accessType) {
        this.code = code;
        this.label = label;
        this.accessType = accessType;
    }

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
        return null;
    }

    public AccessType getAccessType() {
        return this.accessType;
    }

    @JsonCreator
    public static ResourceAction findByCode(String code) {
        return Arrays.stream(ResourceAction.values())
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown Resource Action Name {}", code)));
    }

}
