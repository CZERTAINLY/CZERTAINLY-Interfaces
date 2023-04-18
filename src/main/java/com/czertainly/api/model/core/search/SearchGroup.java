package com.czertainly.api.model.core.search;

import com.czertainly.api.model.common.attribute.v2.AttributeType;
import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.czertainly.api.model.connector.cryptography.enums.IAbstractSearchableEnum;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true)
public enum SearchGroup implements IPlatformEnum, IAbstractSearchableEnum {

    META("meta", "Metadata", AttributeType.META),
    CUSTOM("custom", "Custom attribute", AttributeType.CUSTOM),
    PROPERTY("property", "Property", null);

    private final String code;
    private final String label;
    private final String description;
    private AttributeType attributeType;

    SearchGroup(String code, String label, AttributeType attributeType) {
        this(code, label ,null, attributeType);
    }

    SearchGroup(String code, String label, String description, AttributeType attributeType) {
        this.code = code;
        this.label = label;
        this.description = description;
        this.attributeType = attributeType;
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

    public AttributeType getAttributeType() {
        return attributeType;
    }

    @Override
    public String getEnumLabel() {
        return getLabel();
    }
}
