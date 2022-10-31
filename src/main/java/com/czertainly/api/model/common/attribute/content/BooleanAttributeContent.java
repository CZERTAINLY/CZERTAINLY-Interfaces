package com.czertainly.api.model.common.attribute.content;

import io.swagger.v3.oas.annotations.media.Schema;

public class BooleanAttributeContent extends BaseAttributeContent<Boolean> {

    @Schema(description = "Boolean attribute value")
    private Boolean data;

    public BooleanAttributeContent() {
    }

    public BooleanAttributeContent(Boolean data) {
        this.data = data;
    }

    public Boolean getData() {
        return data;
    }

    public void setData(Boolean data) {
        this.data = data;
    }
}
