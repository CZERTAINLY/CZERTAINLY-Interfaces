package com.czertainly.api.model.common.attribute.v2.content;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

public class FloatAttributeContent extends BaseAttributeContent<Float> {

    @Schema(description = "Float attribute value", requiredMode = Schema.RequiredMode.REQUIRED)
    private Float data;

    public FloatAttributeContent() {
    }

    public FloatAttributeContent(Float data) {
        super(data.toString());
        this.data = data;
    }

    public FloatAttributeContent(String reference, Float data) {
        super(reference);
        this.data = data;
    }

    public Float getData() {
        return data;
    }

    public void setData(Float data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FloatAttributeContent)) return false;
        if (!super.equals(o)) return false;
        FloatAttributeContent that = (FloatAttributeContent) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), data);
    }
}
