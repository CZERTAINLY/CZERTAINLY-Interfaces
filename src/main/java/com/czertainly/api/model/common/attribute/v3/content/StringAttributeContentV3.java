package com.czertainly.api.model.common.attribute.v3.content;

import com.czertainly.api.model.common.attribute.v2.content.AttributeContentType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

@Schema(
        description = "String attribute content",
        type = "object")
public class StringAttributeContentV3 extends BaseAttributeContentV3<String> {

    @Schema(description = "String attribute value", requiredMode = Schema.RequiredMode.REQUIRED)
    private String data;

    public StringAttributeContentV3() {
        setContentType(AttributeContentType.STRING);
    }

    public StringAttributeContentV3(String data) {
        super(data);
        this.data = data;
        setContentType(AttributeContentType.STRING);
    }

    public StringAttributeContentV3(String reference, String data) {
        super(reference);
        this.data = data;
        setContentType(AttributeContentType.STRING);
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
        if (!(o instanceof StringAttributeContentV3)) return false;
        StringAttributeContentV3 that = (StringAttributeContentV3) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }
}
