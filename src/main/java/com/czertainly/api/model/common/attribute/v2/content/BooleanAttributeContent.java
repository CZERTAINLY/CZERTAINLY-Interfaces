package com.czertainly.api.model.common.attribute.v2.content;

import io.swagger.v3.oas.annotations.media.Schema;

public class BooleanAttributeContent extends BaseAttributeContent<Boolean> {

    @Schema(description = "Boolean attribute value", required = true)
    private Boolean data;

    public BooleanAttributeContent() {
    }

    public BooleanAttributeContent(Boolean data) {
        this.data = data;
    }

    public BooleanAttributeContent(String reference, Boolean data) {
        super(reference);
        this.data = data;
    }

    public Boolean getData() {
        return data;
    }

    public void setData(Boolean data) {
        this.data = data;
    }
}
