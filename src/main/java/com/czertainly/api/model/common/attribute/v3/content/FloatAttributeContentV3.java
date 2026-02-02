package com.czertainly.api.model.common.attribute.v3.content;

import com.czertainly.api.model.common.attribute.common.content.AttributeContentType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

@Schema(
        description = "Float attribute content for decimal numbers",
        type = "object")
public class FloatAttributeContentV3 extends BaseAttributeContentV3<Float> {

    @Schema(description = "Float attribute value", requiredMode = Schema.RequiredMode.REQUIRED)
    private Float data;

    public FloatAttributeContentV3() {
        setContentType(AttributeContentType.FLOAT);
    }

    public FloatAttributeContentV3(Float data) {
        super(data.toString());
        this.data = data;
        setContentType(AttributeContentType.FLOAT);
    }

    public FloatAttributeContentV3(String reference, Float data) {
        super(reference);
        this.data = data;
        setContentType(AttributeContentType.FLOAT);
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
        if (!(o instanceof FloatAttributeContentV3)) return false;
        if (!super.equals(o)) return false;
        FloatAttributeContentV3 that = (FloatAttributeContentV3) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), data);
    }

}
