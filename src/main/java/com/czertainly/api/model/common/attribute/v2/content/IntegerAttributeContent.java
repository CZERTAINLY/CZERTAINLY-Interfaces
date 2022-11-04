package com.czertainly.api.model.common.attribute.v2.content;

import io.swagger.v3.oas.annotations.media.Schema;

public class IntegerAttributeContent extends BaseAttributeContent<Integer> {

    @Schema(description = "Integer attribute value")
    private Integer data;

    public IntegerAttributeContent() {
    }

    public IntegerAttributeContent(Integer data) {
        this.data = data;
    }

    public IntegerAttributeContent(String reference, Integer data) {
        super(reference);
        this.data = data;
    }

    @Override
    public Integer getData() {
        return data;
    }

    @Override
    public void setData(Integer data) {
        this.data = data;
    }
}
