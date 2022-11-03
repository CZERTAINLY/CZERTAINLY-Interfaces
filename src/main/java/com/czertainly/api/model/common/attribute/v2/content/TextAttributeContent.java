package com.czertainly.api.model.common.attribute.v2.content;

import io.swagger.v3.oas.annotations.media.Schema;

public class TextAttributeContent extends BaseAttributeContent<String> {

    @Schema(description = "Text attribute value")
    private String data;

    public TextAttributeContent() {
    }

    public TextAttributeContent(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
