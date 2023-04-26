package com.czertainly.api.model.core.cryptography.key;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.common.attribute.v2.content.BaseAttributeContent;
import com.czertainly.api.model.common.attribute.v2.content.StringAttributeContent;
import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Schema(enumAsRef = true)
public enum RsaPadding implements IPlatformEnum {
    PKCS1_v1_5("PKCS1-v1_5"),
    OAEP("OAEP");

    @Schema(description = "Type of the RSA Padding",
            example = "OAEP", requiredMode = Schema.RequiredMode.REQUIRED)
    private final String code;

    RsaPadding(String code) {
        this.code = code;
    }

    @JsonCreator
    public static RsaPadding findByCode(String code) {
        return Arrays.stream(RsaPadding.values())
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown code {}", code)));
    }

    @JsonValue
    public String getCode() {
        return this.code;
    }

    @Override
    public String getLabel() {
        return code;
    }

    @Override
    public String getDescription() {
        return null;
    }

    public static List<BaseAttributeContent> asStringAttributeContentList() {
        return List.of(values()).stream()
                .map(item -> new StringAttributeContent(item.name(), item.getCode()))
                .collect(Collectors.toList());
    }
}
