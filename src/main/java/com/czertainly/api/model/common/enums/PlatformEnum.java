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
import com.czertainly.api.model.common.enums.cryptography.*;
import com.czertainly.api.model.connector.cryptography.enums.TokenInstanceStatus;
import com.czertainly.api.model.connector.notification.NotificationType;
import com.czertainly.api.model.core.acme.AccountStatus;
import com.czertainly.api.model.core.auth.Resource;
import com.czertainly.api.model.core.authority.CertificateRevocationReason;
import com.czertainly.api.model.core.certificate.*;
import com.czertainly.api.model.core.cmp.CmpProfileVariant;
import com.czertainly.api.model.core.cmp.ProtectionMethod;
import com.czertainly.api.model.core.compliance.ComplianceRuleStatus;
import com.czertainly.api.model.core.compliance.ComplianceStatus;
import com.czertainly.api.model.core.connector.AuthType;
import com.czertainly.api.model.core.connector.ConnectorStatus;
import com.czertainly.api.model.core.connector.FunctionGroupCode;
import com.czertainly.api.model.core.cryptography.key.KeyState;
import com.czertainly.api.model.core.cryptography.key.KeyUsage;
import com.czertainly.api.model.core.discovery.DiscoveryStatus;
import com.czertainly.api.model.core.enums.CertificateRequestFormat;
import com.czertainly.api.model.core.enums.Protocol;
import com.czertainly.api.model.core.other.ResourceEvent;
import com.czertainly.api.model.core.workflows.ConditionType;
import com.czertainly.api.model.core.workflows.ExecutionType;
import com.czertainly.api.model.core.workflows.TriggerType;
import com.czertainly.api.model.core.search.FilterFieldSource;
import com.czertainly.api.model.core.search.FilterConditionOperator;
import com.czertainly.api.model.core.search.FilterFieldType;
import com.czertainly.api.model.core.settings.SettingsSection;
import com.czertainly.api.model.scheduler.SchedulerJobExecutionStatus;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

@Schema(enumAsRef = true)
public enum PlatformEnum implements IPlatformEnum {
    // general
    RESOURCE(Resource.class, "Platform resource"),
    FILTER_CONDITION_OPERATOR(FilterConditionOperator.class, "Filter condition operator"),
    FILTER_FIELD_TYPE(FilterFieldType.class, "Filter field type"),
    FILTER_FIELD_SOURCE(FilterFieldSource.class, "Filter field source"),
    SETTINGS_SECTION(SettingsSection.class, "Settings section"),
    NOTIFICATION_TYPE(NotificationType.class, "Notification type"),

    // connectors
    AUTH_TYPE(AuthType.class, "Authentication type"),
    HEALTH_STATUS(HealthStatus.class, "Health status"),
    CONNECTOR_STATUS(ConnectorStatus.class, "Connector status"),
    CONNECTOR_FUNCTION_GROUP(FunctionGroupCode.class, "Connector function group"),  // TODO: rename to ConnectorFunctionGroup

    // certificates
    CERTIFICATE_TYPE(CertificateType.class, "Certificate type"),
    CERTIFICATE_STATE(CertificateState.class, "Certificate state"),
    CERTIFICATE_VALIDATION_STATUS(CertificateValidationStatus.class, "Certificate validation status"),
    CERTIFICATE_FORMAT(CertificateFormat.class, "Certificate format"),
    CERTIFICATE_FORMAT_ENCODING(CertificateFormatEncoding.class, "Certificate format encoding"),
    CERTIFICATE_VALIDATION_CHECK(CertificateValidationCheck.class, "Certificate validation check type"),
    CERTIFICATE_REVOCATION_REASON(CertificateRevocationReason.class, "Certificate revocation reason"),
    CERTIFICATE_REQUEST_FORMAT(CertificateRequestFormat.class, "Certificate request format"),
    DISCOVERY_STATUS(DiscoveryStatus.class, "Discovery status"),
    PROTOCOL(Protocol.class, "Protocol used to issue certificate"),

    // keys & tokens
    KEY_ALGORITHM(KeyAlgorithm.class, "Cryptographic key algorithm"),
    KEY_FORMAT(KeyFormat.class, "Key format type"),
    KEY_STATE(KeyState.class, "Key state"),
    KEY_TYPE(KeyType.class, "Key type"),
    KEY_USAGE(KeyUsage.class, "Key usage"),
    KEY_REQUEST_TYPE(KeyRequestType.class, "Key request type"),
    KEY_COMPROMISE_REASON(KeyCompromiseReason.class, "Key compromise reason"),
    TOKEN_STATUS(TokenInstanceStatus.class, "Token instance status"),
    DIGEST_ALGORITHM(DigestAlgorithm.class, "Digest algorithm"),
    RSA_SIGNATURE_SCHEME(RsaSignatureScheme.class, "RSA signature scheme"),
    RSA_ENCRYPTION_SCHEME(RsaEncryptionScheme.class, "RSA encryption scheme"),

    // compliance
    COMPLIANCE_STATUS(ComplianceStatus.class, "Compliance status"),
    COMPLIANCE_RULE_STATUS(ComplianceRuleStatus.class, "Compliance rule status"),

    // ACME
    ACME_ACCOUNT_STATUS(AccountStatus.class, "ACME Account status"), // TODO: rename to AcmeAccountStatus

    // CMP
    CMP_PROTECTION_METHOD(ProtectionMethod.class, "CMP protection method"),
    CMP_PROFILE_VARIANT(CmpProfileVariant.class, "CMP protocol variant"),

    // Attributes
    ATTRIBUTE_TYPE(AttributeType.class, "Attribute Type"),
    ATTRIBUTE_CONTENT_TYPE(AttributeContentType.class, "Attribute content type"),
    ATTRIBUTE_CONSTRAINT_TYPE(AttributeConstraintType.class, "Attribute constraint type"),
    ATTRIBUTE_CALLBACK_VALUE_TARGET(AttributeValueTarget.class, "Attribute callback mapping value target"), // TODO: rename to AttributeCallbackValueTarget
    PROGRAMMING_LANGUAGE(ProgrammingLanguageEnum.class, "Programming language for code block attribute"),

    // Scheduler
    SCHEDULER_JOB_EXECUTION_STATUS(SchedulerJobExecutionStatus.class, "Scheduled job execution status"),

    // workflows
    TRIGGER_TYPE(TriggerType.class, "Trigger Type"),
    CONDITION_TYPE(ConditionType.class, "Condition Type"),
    EXECUTION_TYPE(ExecutionType.class, "Execution Type"),
    RESOURCE_EVENT(ResourceEvent.class, "Events available for a resource")
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
