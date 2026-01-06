package com.czertainly.api.model.core.auth;

import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true)
public enum AttributeResource implements IPlatformEnum {

    CERTIFICATE(Resource.Codes.CERTIFICATE, "Certificate"),
    CERTIFICATE_REQUEST(Resource.Codes.CERTIFICATE_REQUEST, "Certificate Request"),
    CREDENTIAL(Resource.Codes.CREDENTIAL, "Credential"),
    AUTHORITY(Resource.Codes.AUTHORITY, "Authority")
    ;

    private final String code;
    private final String label;

    AttributeResource(String code, String label) {
        this.code = code;
        this.label = label;
    }

    @Override
    @JsonValue
    public String getCode() {
        return code;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getDescription() {
        return null;
    }
}
