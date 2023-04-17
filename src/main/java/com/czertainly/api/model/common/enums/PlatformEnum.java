package com.czertainly.api.model.common.enums;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.common.HealthStatus;
import com.czertainly.api.model.common.attribute.v2.AttributeType;
import com.czertainly.api.model.common.attribute.v2.callback.AttributeValueTarget;
import com.czertainly.api.model.common.attribute.v2.constraint.AttributeConstraintType;
import com.czertainly.api.model.common.attribute.v2.content.AttributeContentType;
import com.czertainly.api.model.core.acme.AccountStatus;
import com.czertainly.api.model.core.certificate.CertificateStatus;
import com.czertainly.api.model.core.certificate.CertificateType;
import com.czertainly.api.model.core.certificate.CertificateValidationStatus;
import com.czertainly.api.model.core.compliance.ComplianceRuleStatus;
import com.czertainly.api.model.core.compliance.ComplianceStatus;
import com.czertainly.api.model.core.connector.AuthType;
import com.czertainly.api.model.core.connector.ConnectorStatus;
import com.czertainly.api.model.core.connector.FunctionGroupCode;
import com.czertainly.api.model.core.discovery.DiscoveryStatus;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

@Schema(enumAsRef = true)
public enum PlatformEnum implements IPlatformEnum {
    // connectors
    AUTH_TYPE(AuthType.class, "Authentication type"),
    HEALTH_STATUS(HealthStatus.class, "Health status"),
    CONNECTOR_STATUS(ConnectorStatus.class, "Connector status"),
    CONNECTOR_FUNCTION_GROUP(FunctionGroupCode.class, "Connector function group"),  // TODO: rename to ConnectorFunctionGroup

    // certificates
    CERTIFICATE_TYPE(CertificateType.class, "Certificate type"),
    CERTIFICATE_STATUS(CertificateStatus.class, "Certificate status"),
    CERTIFICATE_VALIDATION_STATUS(CertificateValidationStatus.class, "Certificate validation status"),

    // compliance
    COMPLIANCE_STATUS(ComplianceStatus.class, "Compliance status"),
    COMPLIANCE_RULE_STATUS(ComplianceRuleStatus.class, "Compliance rule status"),

    DISCOVERY_STATUS(DiscoveryStatus.class, "Discovery status"),

    // ACME
    ACME_ACCOUNT_STATUS(AccountStatus.class, "ACME Account status"), // TODO: rename to AcmeAccountStatus

    // Attributes
    ATTRIBUTE_TYPE(AttributeType.class, "Attribute Type"),
    ATTRIBUTE_CONTENT_TYPE(AttributeContentType.class, "Attribute content type"),
    ATTRIBUTE_CONSTRAINT_TYPE(AttributeConstraintType.class, "Attribute constraint type"),
    ATTRIBUTE_CALLBACK_VALUE_TARGET(AttributeValueTarget.class, "Attribute callback mapping value target"), // TODO: rename to AttributeCallbackValueTarget
    ;

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
        return Arrays.stream(PlatformEnum.values())
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown platform enum {}", code)));
    }
}
