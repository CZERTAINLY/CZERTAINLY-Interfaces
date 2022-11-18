package com.czertainly.api.model.common.attribute.v2.content;

import io.swagger.v3.oas.annotations.media.Schema;

public class FloatAttributeContent extends BaseAttributeContent<Float> {

    @Schema(description = "Float attribute value", required = true)
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
}
