package com.czertainly.api.model.core.search;

import com.czertainly.api.model.common.attribute.v2.AttributeType;
import com.czertainly.api.model.connector.cryptography.enums.IAbstractSearchableEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true)
public enum SearchGroup implements IAbstractSearchableEnum {

    META(AttributeType.META, "Metadata"),
    CUSTOM(AttributeType.CUSTOM, "Custom attribute"),
    PROPERTY(null, "Property");


    private AttributeType attributeType;

    private String label;

    SearchGroup(AttributeType attributeType, String label) {
        this.attributeType = attributeType;
        this.label = label;
    }

    public AttributeType getAttributeType() {
        return attributeType;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }

    @Override
    public String getEnumLabel() {
        return getLabel();
    }
}
