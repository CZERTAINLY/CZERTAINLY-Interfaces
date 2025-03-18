package com.czertainly.api.model.common.attribute.v2.content;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

@Schema(
        description = "Integer attribute content for integer numbers",
        type = "object")
public class IntegerAttributeContent extends BaseAttributeContent<Integer> {

    @Schema(description = "Integer attribute value", requiredMode = Schema.RequiredMode.REQUIRED)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntegerAttributeContent)) return false;
        if (!super.equals(o)) return false;
        IntegerAttributeContent that = (IntegerAttributeContent) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), data);
    }
}
