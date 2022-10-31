package com.czertainly.api.model.common.attribute.content;

import io.swagger.v3.oas.annotations.media.Schema;

public class FloatAttributeContent extends BaseAttributeContent<Float> {

    @Schema(description = "Float attribute value")
    private Float data;

    public FloatAttributeContent() {
    }

    public FloatAttributeContent(Float data) {
        this.data = data;
    }

    public Float getData() {
        return data;
    }

    public void setData(Float data) {
        this.data = data;
    }
}
