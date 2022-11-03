package com.czertainly.api.model.common.attribute.v2.content;

import io.swagger.v3.oas.annotations.media.Schema;

public class StringAttributeContent extends BaseAttributeContent<String> {

    @Schema(description = "String attribute value")
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
}
