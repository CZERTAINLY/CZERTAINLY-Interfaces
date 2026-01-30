package com.czertainly.api.model.client.connector.v2;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;


@Schema(enumAsRef = true)
public enum ConnectorInterface implements IPlatformEnum {

    INFO("info", "Info"),
    HEALTH("health", "Health"),
    AUTHORITY_PROVIDER("authorityProvider", "Authority Provider"),
    DISCOVERY_PROVIDER("discoveryProvider", "Discovery Provider"),
    ENTITY_PROVIDER("entityProvider", "Entity Provider"),
    COMPLIANCE_PROVIDER("complianceProvider", "Compliance Provider"),
    CRYPTOGRAPHY_PROVIDER("cryptographyProvider", "Cryptography Provider"),
    NOTIFICATION_PROVIDER("notificationProvider", "Notification Provider"),
    SECRET_PROVIDER("secretProvider", "Secret Provider");

    private static final ConnectorInterface[] VALUES;

    static {
        VALUES = values();
    }

    @Schema(description = "Function Group code of the Connector",
            examples = {"credentialProvider"}, requiredMode = Schema.RequiredMode.REQUIRED)
    private final String code;
    private final String label;
    private final String description;

    ConnectorInterface(String code, String label) {
        this(code, label,null);
    }

    ConnectorInterface(String code, String label, String description) {
        this.code = code;
        this.label = label;
        this.description = description;
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

    @JsonCreator
    public static ConnectorInterface findByCode(String code) {
        return Arrays.stream(VALUES)
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown connector interface code {}", code)));
    }
}
