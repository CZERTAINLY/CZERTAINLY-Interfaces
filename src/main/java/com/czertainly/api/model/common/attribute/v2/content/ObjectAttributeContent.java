package com.czertainly.api.model.common.attribute.v2.content;

import io.swagger.v3.oas.annotations.media.Schema;

public class ObjectAttributeContent extends BaseAttributeContent<Object> {

    @Schema(description = "Object attribute value")
    private Object data;

    public ObjectAttributeContent() {
    }

    public ObjectAttributeContent(Object data) {
        this.data = data;
    }

    public ObjectAttributeContent(String reference, Object data) {
        super(reference);
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
