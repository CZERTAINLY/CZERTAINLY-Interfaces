package com.czertainly.api.model.common.attribute.v2.content;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

@Schema(
        description = "Object attribute content for data with custom structure",
        type = "object")
public class ObjectAttributeContent extends BaseAttributeContent<Object> {

    @Schema(description = "Object attribute content data", requiredMode = Schema.RequiredMode.REQUIRED)
    private Object data;

    public ObjectAttributeContent() {
        setContentType(AttributeContentType.OBJECT);
    }

    public ObjectAttributeContent(Object data) {
        this.data = data;
        setContentType(AttributeContentType.OBJECT);
    }

    public ObjectAttributeContent(String reference, Object data) {
        super(reference);
        this.data = data;
        setContentType(AttributeContentType.OBJECT);
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
        if (!(o instanceof ObjectAttributeContent)) return false;
        if (!super.equals(o)) return false;
        ObjectAttributeContent that = (ObjectAttributeContent) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), data);
    }
}
