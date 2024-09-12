package com.czertainly.api.model.core.certificate;

import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true)
public enum CertificateSubjectType implements IPlatformEnum {

    END_ENTITY("endEntity", "End Entity", "Certificate issued by a Certificate Authority."),
    SELF_SIGNED_END_ENTITY("selfSignedEndEntity", "Self-signed End Entity", "Certificate signed by itself, without outside authority."),
    INTERMEDIATE_CA("intermediateCa", "Intermediate CA", "Certificate issued by a trusted source that can create certificates for others but isn’t the top-level authority."),
    ROOT_CA("rootCa", "Root CA", "The main trusted certificate that signs and approves other certificates in a security system.");

    private final String code;
    private final String label;
    private final String description;

    CertificateSubjectType(String code, String label, String description) {
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

    public boolean isCa() {
        return this.equals(ROOT_CA) || this.equals(INTERMEDIATE_CA);
    }
}
