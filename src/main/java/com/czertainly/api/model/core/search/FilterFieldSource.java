package com.czertainly.api.model.core.search;

import com.czertainly.api.model.common.attribute.v2.AttributeType;
import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

@Schema(enumAsRef = true)
public enum FilterFieldSource implements IPlatformEnum {

    META("meta", "Metadata", AttributeType.META),
    CUSTOM("custom", "Custom attribute", AttributeType.CUSTOM),
    PROPERTY("property", "Property", null);

    private static final FilterFieldSource[] VALUES;

    static {
        VALUES = values();
    }

    private final String code;
    private final String label;
    private final String description;
    private AttributeType attributeType;

    FilterFieldSource(String code, String label, AttributeType attributeType) {
        this(code, label ,null, attributeType);
    }

    FilterFieldSource(String code, String label, String description, AttributeType attributeType) {
        this.code = code;
        this.label = label;
        this.description = description;
        this.attributeType = attributeType;
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

    public AttributeType getAttributeType() {
        return attributeType;
    }

    @JsonCreator
    public static FilterFieldSource fromCode(String code) {
        return Arrays.stream(VALUES)
                .filter(e -> e.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Unsupported search group %s.", code)));
    }
}
