package com.czertainly.api.model.common.attribute.v2.content;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

public class BooleanAttributeContent extends BaseAttributeContent<Boolean> {

    @Schema(description = "Boolean attribute value", required = true)
    private Boolean data;

    public BooleanAttributeContent() {
    }

    public BooleanAttributeContent(Boolean data) {
        this.data = data;
    }

    public BooleanAttributeContent(String reference, Boolean data) {
        super(reference);
        this.data = data;
    }

    public Boolean getData() {
        return data;
    }

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
