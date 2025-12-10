package com.czertainly.api.model.common.attribute.v3.content;

import com.czertainly.api.model.common.attribute.common.content.AttributeContentType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

@Schema(
        description = "Object attribute content for data with custom structure",
        type = "object")
public class ObjectAttributeContentV3 extends BaseAttributeContentV3<Object> {

    @Schema(description = "Object attribute content data", requiredMode = Schema.RequiredMode.REQUIRED)
    private Object data;

    public ObjectAttributeContentV3() {
        setContentType(AttributeContentType.OBJECT);
    }

    public ObjectAttributeContentV3(Object data) {
        this.data = data;
        setContentType(AttributeContentType.OBJECT);
    }

    public ObjectAttributeContentV3(String reference, Object data) {
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
        if (!(o instanceof ObjectAttributeContentV3)) return false;
        if (!super.equals(o)) return false;
        ObjectAttributeContentV3 that = (ObjectAttributeContentV3) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), data);
    }
}
