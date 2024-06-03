package com.czertainly.api.model.common.attribute.v2.content;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

public class TextAttributeContent extends BaseAttributeContent<String> {

    @Schema(description = "Text attribute value", requiredMode = Schema.RequiredMode.REQUIRED)
    private String data;

    public TextAttributeContent() {
    }

    public TextAttributeContent(String data) {
        this.data = data;
    }

    public TextAttributeContent(String reference, String data) {
        super(reference);
        this.data = data;
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
        if (!(o instanceof TextAttributeContent)) return false;
        if (!super.equals(o)) return false;
        TextAttributeContent that = (TextAttributeContent) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), data);
    }
}
