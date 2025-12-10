package com.czertainly.api.model.common.attribute.v3.content;

import com.czertainly.api.model.common.attribute.common.content.AttributeContentType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

@Schema(
        description = "Text attribute content used to store longer formatted strings",
        type = "object")
public class TextAttributeContentV3 extends BaseAttributeContentV3<String> {

    @Schema(description = "Text attribute value", requiredMode = Schema.RequiredMode.REQUIRED)
    private String data;

    public TextAttributeContentV3() {
        setContentType(AttributeContentType.TEXT);
    }

    public TextAttributeContentV3(String data) {
        this.data = data;
        setContentType(AttributeContentType.TEXT);
    }

    public TextAttributeContentV3(String reference, String data) {
        super(reference);
        this.data = data;
        setContentType(AttributeContentType.TEXT);
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TextAttributeContentV3)) return false;
        if (!super.equals(o)) return false;
        TextAttributeContentV3 that = (TextAttributeContentV3) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), data);
    }
}
