package com.czertainly.api.model.common.attribute.v2.content;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

public class StringAttributeContent extends BaseAttributeContent<String> {

    @Schema(description = "String attribute value", requiredMode = Schema.RequiredMode.REQUIRED)
    private String data;

    public StringAttributeContent() {
    }

    public StringAttributeContent(String data) {
        super(data);
        this.data = data;
    }

    public StringAttributeContent(String reference, String data) {
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
        if (!(o instanceof StringAttributeContent)) return false;
        StringAttributeContent that = (StringAttributeContent) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }
}
