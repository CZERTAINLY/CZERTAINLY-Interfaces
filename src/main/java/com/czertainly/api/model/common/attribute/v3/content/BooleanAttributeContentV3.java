package com.czertainly.api.model.common.attribute.v3.content;

import com.czertainly.api.model.common.attribute.common.content.AttributeContentType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Schema(
        description = "Boolean attribute content to store true/false values",
        type = "object")
public class BooleanAttributeContentV3 extends BaseAttributeContentV3<Boolean> {

    @Schema(description = "Boolean attribute value", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean data;

    @Schema(description = "Boolean attribute value", requiredMode = Schema.RequiredMode.REQUIRED)
    @Getter
    @Setter
    private AttributeContentType contentType = AttributeContentType.BOOLEAN;

    public BooleanAttributeContentV3() {
    }

    public BooleanAttributeContentV3(Boolean data) {
        this.data = data;
        setContentType(AttributeContentType.BOOLEAN);
    }

    public BooleanAttributeContentV3(String reference, Boolean data) {
        super(reference);
        this.data = data;
        setContentType(AttributeContentType.BOOLEAN);
    }

    @Override
    public Boolean getData() {
        return data;
    }

    @Override
    public void setData(Boolean data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BooleanAttributeContentV3)) return false;
        if (!super.equals(o)) return false;
        BooleanAttributeContentV3 that = (BooleanAttributeContentV3) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), data);
    }
}
