package com.czertainly.api.model.core.cryptography.key;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.common.attribute.v2.content.BaseAttributeContent;
import com.czertainly.api.model.common.attribute.v2.content.StringAttributeContent;
import com.czertainly.api.model.connector.cryptography.enums.IAbstractSearchableEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Schema(enumAsRef = true)
public enum OaepHash implements IAbstractSearchableEnum {
    SHA1("SHA1"),
    SHA224("SHA224"),
    SHA256("SHA256"),
    SHA384("SHA384"),
    SHA512("SHA384"),
    SHA3_224("SHA3-224"),
    SHA3_256("SHA3-256"),
    SHA3_384("SHA3-384"),
    SHA3_512("SHA3-384"),
    ;

    @Schema(description = "OAEP Hash Function",
            example = "SHA1", requiredMode = Schema.RequiredMode.REQUIRED)
    private final String code;

    OaepHash(String code) {
        this.code = code;
    }

    @JsonCreator
    public static OaepHash findByCode(String code) {
        return Arrays.stream(OaepHash.values())
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
    public String getEnumLabel() {
        return code;
    }

    public static List<BaseAttributeContent> asStringAttributeContentList() {
        return List.of(values()).stream()
                .map(item -> new StringAttributeContent(item.name(), item.getCode()))
                .collect(Collectors.toList());
    }
}
