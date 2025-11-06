package com.czertainly.api.model.common.attribute.v2.content;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

@Schema(
        description = "Boolean attribute content to store true/false values",
        type = "object")
public class BooleanAttributeContent extends BaseAttributeContent<Boolean> {

    @Schema(description = "Boolean attribute value", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean data;

    public BooleanAttributeContent() {
    }

    public BooleanAttributeContent(Boolean data) {
        this.data = data;
        setContentType(AttributeContentType.BOOLEAN);
    }

    public BooleanAttributeContent(String reference, Boolean data) {
        super(reference);
        this.data = data;
        setContentType(AttributeContentType.BOOLEAN);
    }

    @Override
    public Boolean getData() {
        return data;
    }

    @Override
    public void setData(Boolean data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BooleanAttributeContent)) return false;
        if (!super.equals(o)) return false;
        BooleanAttributeContent that = (BooleanAttributeContent) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), data);
    }
}
