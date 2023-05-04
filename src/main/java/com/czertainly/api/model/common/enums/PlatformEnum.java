package com.czertainly.api.model.common.enums;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.client.cryptography.key.KeyCompromiseReason;
import com.czertainly.api.model.client.cryptography.key.KeyRequestType;
import com.czertainly.api.model.common.HealthStatus;
import com.czertainly.api.model.common.attribute.v2.AttributeType;
import com.czertainly.api.model.common.attribute.v2.callback.AttributeValueTarget;
import com.czertainly.api.model.common.attribute.v2.constraint.AttributeConstraintType;
import com.czertainly.api.model.common.attribute.v2.content.AttributeContentType;
import com.czertainly.api.model.common.attribute.v2.content.data.ProgrammingLanguageEnum;
import com.czertainly.api.model.common.enums.cryptography.KeyAlgorithm;
import com.czertainly.api.model.common.enums.cryptography.KeyFormat;
import com.czertainly.api.model.common.enums.cryptography.KeyType;
import com.czertainly.api.model.connector.cryptography.enums.TokenInstanceStatus;
import com.czertainly.api.model.core.acme.AccountStatus;
import com.czertainly.api.model.core.auth.Resource;
import com.czertainly.api.model.core.certificate.CertificateStatus;
import com.czertainly.api.model.core.certificate.CertificateType;
import com.czertainly.api.model.core.certificate.CertificateValidationStatus;
import com.czertainly.api.model.core.compliance.ComplianceRuleStatus;
import com.czertainly.api.model.core.compliance.ComplianceStatus;
import com.czertainly.api.model.core.connector.AuthType;
import com.czertainly.api.model.core.connector.ConnectorStatus;
import com.czertainly.api.model.core.connector.FunctionGroupCode;
import com.czertainly.api.model.core.cryptography.key.KeyState;
import com.czertainly.api.model.core.cryptography.key.KeyUsage;
import com.czertainly.api.model.core.discovery.DiscoveryStatus;
import com.czertainly.api.model.core.search.SearchCondition;
import com.czertainly.api.model.core.search.SearchGroup;
import com.czertainly.api.model.core.search.SearchableFieldType;
import com.czertainly.api.model.core.settings.SettingsSection;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

@Schema(enumAsRef = true)
public enum PlatformEnum implements IPlatformEnum {
    // general
    RESOURCE(Resource.class, "Platform resource"),
    SEARCH_CONDITION(SearchCondition.class, "Search condition"),
    SEARCH_FIELD_TYPE(SearchableFieldType.class, "Search field type"),
    SEARCH_GROUP(SearchGroup.class, "Search group"),
    SETTINGS_SECTION(SettingsSection.class, "Settings section"),

    // connectors
    AUTH_TYPE(AuthType.class, "Authentication type"),
    HEALTH_STATUS(HealthStatus.class, "Health status"),
    CONNECTOR_STATUS(ConnectorStatus.class, "Connector status"),
    CONNECTOR_FUNCTION_GROUP(FunctionGroupCode.class, "Connector function group"),  // TODO: rename to ConnectorFunctionGroup

    // certificates
    CERTIFICATE_TYPE(CertificateType.class, "Certificate type"),
    CERTIFICATE_STATUS(CertificateStatus.class, "Certificate status"),
    CERTIFICATE_VALIDATION_STATUS(CertificateValidationStatus.class, "Certificate validation status"),
    DISCOVERY_STATUS(DiscoveryStatus.class, "Discovery status"),

    // keys & tokens
    CRYPTOGRAPHIC_ALGORITHM(KeyAlgorithm.class, "Cryptographic algorithm"),
    KEY_FORMAT(KeyFormat.class, "Key format type"),
    KEY_STATE(KeyState.class, "Key state"),
    KEY_TYPE(KeyType.class, "Key type"),
    KEY_USAGE(KeyUsage.class, "Key usage"),
    KEY_REQUEST_TYPE(KeyRequestType.class, "Key request type"),
    KEY_COMPROMISE_REASON(KeyCompromiseReason.class, "Key compromise reason"),
    TOKEN_STATUS(TokenInstanceStatus.class, "Token instance status"),

    // compliance
    COMPLIANCE_STATUS(ComplianceStatus.class, "Compliance status"),
    COMPLIANCE_RULE_STATUS(ComplianceRuleStatus.class, "Compliance rule status"),

    // ACME
    ACME_ACCOUNT_STATUS(AccountStatus.class, "ACME Account status"), // TODO: rename to AcmeAccountStatus

    // Attributes
    ATTRIBUTE_TYPE(AttributeType.class, "Attribute Type"),
    ATTRIBUTE_CONTENT_TYPE(AttributeContentType.class, "Attribute content type"),
    ATTRIBUTE_CONSTRAINT_TYPE(AttributeConstraintType.class, "Attribute constraint type"),
    ATTRIBUTE_CALLBACK_VALUE_TARGET(AttributeValueTarget.class, "Attribute callback mapping value target"), // TODO: rename to AttributeCallbackValueTarget
    PROGRAMMING_LANGUAGE(ProgrammingLanguageEnum.class, "Programming language for code block attribute"),
    ;

    private static final PlatformEnum[] VALUES;

    static {
        VALUES = values();
    }

    private final String code;
    private final String label;
    private final String description;

    private final Class<? extends IPlatformEnum> enumClass;

    PlatformEnum(Class<? extends IPlatformEnum> enumClass, String label) {
        this(enumClass, label, null);
    }

    PlatformEnum(Class<? extends IPlatformEnum> enumClass, String label, String description) {
        this.enumClass = enumClass;
        this.code = enumClass.getSimpleName();
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

    public Class<? extends IPlatformEnum> getEnumClass() {
        return enumClass;
    }

    @JsonCreator
    public static PlatformEnum findByCode(String code) {
        return Arrays.stream(VALUES)
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown platform enum {}", code)));
    }

    public static PlatformEnum findByClass(Class clazz) {
        return Arrays.stream(VALUES)
                .filter(e -> e.enumClass.equals(clazz))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Unknown platform enum for class %s.", clazz)));
    }
}
