package com.czertainly.api.model.core.auth;

import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(enumAsRef = true)
public enum AttributeResource implements IPlatformEnum {

    CERTIFICATE(Resource.Codes.CERTIFICATE, "Certificate", true),
    CREDENTIAL(Resource.Codes.CREDENTIAL, "Credential", true),
    AUTHORITY(Resource.Codes.AUTHORITY, "Authority", false),
    ENTITY(Resource.Codes.ENTITY, "Entity Instance", false),
    LOCATION(Resource.Codes.LOCATION, "Location", false)
    ;

    private final String code;
    private final String label;
    @Getter
    private final boolean withContent;

    AttributeResource(String code, String label, boolean withContent) {
        this.code = code;
        this.label = label;
        this.withContent = withContent;
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
