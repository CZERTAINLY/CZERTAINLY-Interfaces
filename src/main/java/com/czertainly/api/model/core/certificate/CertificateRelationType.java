package com.czertainly.api.model.core.certificate;

import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true)
public enum CertificateRelationType implements IPlatformEnum {

    RENEWAL("renewal", "Renewal", "Renewal of source certificate"),
    REKEY("rekey", "Rekey", "Rekey of source certificate"),
    REPLACEMENT("replacement", "Replacement", "Replacement of source certificate")
    ;

    private final String code;
    private final String label;
    private final String description;

    CertificateRelationType(String code, String label, String description) {
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
}
