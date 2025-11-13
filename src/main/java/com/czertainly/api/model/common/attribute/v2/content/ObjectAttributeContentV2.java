package com.czertainly.api.model.common.attribute.v2.content;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

@Schema(
        description = "Object attribute content for data with custom structure",
        type = "object")
public class ObjectAttributeContentV2 extends BaseAttributeContentV2<Object> {

    @Schema(description = "Object attribute content data", requiredMode = Schema.RequiredMode.REQUIRED)
    private Object data;

    public ObjectAttributeContentV2() {

    }

    public ObjectAttributeContentV2(Object data) {
        this.data = data;
    }

    public ObjectAttributeContentV2(String reference, Object data) {
        super(reference);
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ObjectAttributeContentV2)) return false;
        if (!super.equals(o)) return false;
        ObjectAttributeContentV2 that = (ObjectAttributeContentV2) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), data);
    }
}
